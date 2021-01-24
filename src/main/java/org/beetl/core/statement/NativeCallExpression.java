package org.beetl.core.statement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.beetl.core.Context;
import org.beetl.core.NativeSecurityManager;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.BeetlParserException;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.om.ObjectMethodMatchConf;
import org.beetl.core.fun.ObjectUtil;
import org.beetl.core.statement.nat.ClassNode;
import org.beetl.core.statement.nat.InstanceNode;
import org.beetl.core.statement.nat.NativeArrayNode;
import org.beetl.core.statement.nat.NativeAttributeNode;
import org.beetl.core.statement.nat.NativeMethodNode;
import org.beetl.core.statement.nat.NativeNode;

/**
 * Java方法直接调用
 * xxx.abc().ef.ttt();
 *
 * @author xiandafu
 */
public class NativeCallExpression extends Expression {

    InstanceNode insNode;
    ClassNode clsNode;
    NativeNode[] chain;

    public NativeCallExpression(InstanceNode insNode, NativeNode[] chain, GrammarToken token) {
        super(token);
        this.insNode = insNode;
        this.chain = chain;
    }

    public NativeCallExpression(ClassNode clsNode, NativeNode[] chain, GrammarToken token) {
        super(token);
        this.clsNode = clsNode;
        this.chain = chain;
    }

    public Object evaluate(Context ctx) {
        Class targetCls = null;
        Object targetObj = null;
        NativeNode lastNode = null;
        if (insNode != null) {
            targetObj = insNode.ref.evaluate(ctx);
            if (targetObj != null) {
                targetCls = targetObj.getClass();
            }

            lastNode = insNode;
        } else {
            targetCls = ctx.gt.loadClassBySimpleName(this.clsNode.clazz);
            if (targetCls == null) {
                throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "该类不存在")
                        .pushToken(GrammarToken.createToken(clsNode.clazz, token.line));
            }
            lastNode = clsNode;

        }

        for (NativeNode node : chain) {

            if (node instanceof NativeAttributeNode) {
                String attr = ((NativeAttributeNode) node).attribute;
                try {
                    checkNull(targetCls, lastNode);
                    Field f = targetCls.getField(attr);
                    if (!Modifier.isStatic(f.getModifiers())) {
                        checkNull(targetObj, lastNode);
                    }
                    targetObj = f.get(targetObj);
                    targetCls = f.getType();
                } catch (SecurityException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "不能调用属性", e)
                            .pushToken(GrammarToken.createToken(attr, token.line));
                } catch (NoSuchFieldException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "无此属性", e)
                            .pushToken(GrammarToken.createToken(attr, token.line));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "访问属性出错", e)
                            .pushToken(GrammarToken.createToken(attr, token.line));
                }

            } else if (node instanceof NativeArrayNode) {
                checkNull(targetCls, lastNode);
                if (!targetCls.isArray()) {
                    // 最好是把上一个字符显示出来
                    throw new BeetlException(BeetlException.ARRAY_TYPE_ERROR)
                            .pushToken(GrammarToken.createToken("[]", token.line));
                }
                Expression exp = ((NativeArrayNode) node).exp;
                Object value = exp.evaluate(ctx);
                if (value instanceof Number) {
                    int index = ((Number) value).intValue();
                    targetObj = ((Object[]) targetObj)[index];
                    targetCls = targetObj == null ? null : targetObj.getClass(); // todo or component of array
                } else {
                    throw new BeetlException(BeetlException.ARRAY_INDEX_ERROR, "数组指针必须是Number类型")
                            .pushToken(GrammarToken.createToken("[]", token.line));
                }
            } else if (node instanceof NativeMethodNode) {
                NativeMethodNode methodNode = (NativeMethodNode) node;
                String method = methodNode.method;
                Expression[] expList = methodNode.params;
                this.checkPermit(ctx, targetCls, targetObj, method);

                Object[] args = expList.length == 0 ? ObjectUtil.EMPTY_OBJECT_ARRAY : new Object[expList.length];
                Class[] parameterType = new Class[args.length];
                for (int i = 0; i < expList.length; i++) {
                    args[i] = expList[i].evaluate(ctx);
                    parameterType[i] = args[i] == null ? null : args[i].getClass();
                }
                this.checkNull(targetCls, lastNode);
                ObjectMethodMatchConf mf = ObjectUtil.findMethod(targetCls, method, parameterType);
                if (mf == null) {
                    throw new BeetlException(BeetlParserException.NATIVE_CALL_INVALID,
                            "根据参数未找到匹配的方法" + method + BeetlUtil.getParameterDescription(parameterType))
                            .pushToken(GrammarToken.createToken(token.text, token.line));
                }

                if (targetObj == null && !Modifier.isStatic(mf.method.getModifiers())) {
                    throw new BeetlException(BeetlException.NULL)
                            .pushToken(GrammarToken.createToken(token.text, token.line));
                }

                try {
                    targetObj = ObjectUtil.invoke(targetObj, mf, args);
                    targetCls = targetObj == null ? null : targetObj.getClass();
                } catch (SecurityException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "不能调用方法", e)
                            .pushToken(GrammarToken.createToken(method, token.line));
                } catch (IllegalArgumentException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "错误的参数", e)
                            .pushToken(GrammarToken.createToken(method, token.line));
                } catch (IllegalAccessException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "无法访问方法", e)
                            .pushToken(GrammarToken.createToken(method, token.line));
                } catch (InvocationTargetException e) {
                    throw new BeetlException(BeetlException.NATIVE_CALL_EXCEPTION, "内部调用报错", e.getTargetException())
                            .pushToken(GrammarToken.createToken(method, token.line));
                }

            }

            lastNode = node;

        }
        return targetObj;
    }

    private void checkNull(Object o, NativeNode node) {
        if (o == null) {
            throw new BeetlException(BeetlException.NULL)
                    .pushToken(GrammarToken.createToken(node.getName(), token.line));
        }
    }

    private void checkPermit(Context ctx, Class targetCls, Object targetObj, String method) {
        if (targetCls == null) {
            return;
        }
        NativeSecurityManager securityManager = ctx.gt.getNativeSecurity();
        if (securityManager == null) {
            return;
        }
        if (!securityManager.permit(ctx.template.program.res.getId(), targetCls, targetObj, method)) {
            throw new BeetlException(BeetlException.NATIVE_SECUARITY_EXCEPTION)
                    .pushToken(GrammarToken.createToken(method, token.line));
        }
    }
}
