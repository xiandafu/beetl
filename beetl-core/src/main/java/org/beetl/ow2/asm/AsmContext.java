package org.beetl.ow2.asm;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.ElementType;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Keep track the class and method etc when doing scanning/enhancement
 */
public class AsmContext {

    private static final ThreadLocal<AsmContext> current = new ThreadLocal<>();
    private static final ThreadLocal<Integer> line = new ThreadLocal<>();

    private String name;
    private ElementType type;
    private AsmContext parent;
    private Integer lineNo;

    AsmContext(String name, ElementType type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public ElementType type() {
        return type;
    }

    public Integer lineNo() {
        return lineNo;
    }

    public String className() {
        AsmContext ctx = this;
        while (null != ctx) {
            if (TYPE == ctx.type) {
                return ctx.name;
            }
            ctx = ctx.parent;
        }
        throw new RuntimeException("Cannot reach type context");
    }

    @Override
    public String toString() {
        Deque<AsmContext> deque = new ArrayDeque<>();
        AsmContext ctx = this;
        while (null != ctx) {
            deque.push(ctx);
            ctx = ctx.parent;
        }
        StringBuilder sb = new StringBuilder();
        print(sb, deque);
        return sb.toString();
    }

    private static void print(StringBuilder sb, Deque<AsmContext> stack) {
        // print the first one
        AsmContext ctx = stack.pop();
        sb.append(ctx.name);
        // print the following
        while (!stack.isEmpty()) {
            sb.append("::");
            ctx = stack.pop();
            sb.append(ctx.name);
            if (METHOD == ctx.type) {
                sb.append("()");
            }
        }
    }

    public static void line(int lineNo) {
        line.set(lineNo);
    }

    public static int line() {
        return line.get();
    }

    public static void enterClass(String className) {
        AsmContext parent = current();
        AsmContext context = new AsmContext(Type.getObjectType(className).getClassName(), TYPE);
        current.set(context);
        if (null != parent) {
            context.parent = parent;
        }
    }

    public static void enterField(String fieldName) {
        enterSubContext(fieldName, FIELD);
    }

    public static void enterMethod(String methodName) {
        enterSubContext(methodName, METHOD);
    }

    public static void enterParemter(String paramName) {
        enterSubContext(paramName, PARAMETER);
    }

    public static void enterAnnotation(String annoName) {
        enterSubContext(annoName, ANNOTATION_TYPE);
    }

    public static void exit() {
        AsmContext context = current();
        if (null != context) {
            AsmContext parent = context.parent;
            if (null != parent) {
                context.parent = null;
                current.set(parent);
            } else {
                current.remove();
            }
        }
        line.remove();
    }

    private static AsmContext current() {
        return current.get();
    }

    public static AsmContext reset() {
        AsmContext context = current();
        if (null != context) {
            context.lineNo = line.get();
        }
        current.remove();
        line.remove();
        return context;
    }

    private static void enterSubContext(String name, ElementType type) {
        AsmContext parent = current();
        if (null == parent) {
            throw new IllegalStateException("parent context not found");
        }
        AsmContext context = new AsmContext(name, type);
        context.parent = parent;
        current.set(context);
    }

}
