/*
 [The "BSD license"]
 Copyright (c) 2011-2020  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.misc;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.ByteWriter;
import org.beetl.core.GroupTemplate;
import org.beetl.core.fun.FileFunctionWrapper;
import org.beetl.core.io.ByteWriter_Byte;
import org.beetl.core.io.ByteWriter_Char;

/**
 * Beetl使用方法
 *
 * @author xiandafu
 */
public class BeetlUtil {
    //一般变量名称12个足够了

    protected static String webroot;

    static byte[] chars = new byte[]
            {
                    // $,%,&,',(,),*,+,,,-, .,/, 0, 1, 2, 3, 4, 5, 6,
                    36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 46, 0, 48, 49, 50, 51, 52, 53, 54,
                    // 7, 8, 9,:,;,<,=,>,?,@, A, B, C, D, E, F, G, H,
                    55, 56, 57, 0, 0, 0, 0, 0, 0, 0, 65, 66, 67, 68, 69, 70, 71, 72,
                    // I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W,
                    73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
                    // X, Y, Z,[,\,],^, _,`, a, b, c,  d,  e,  f,  g,
                    88, 89, 90, 0, 0, 0, 0, 95, 0, 97, 98, 99, 100, 101, 102, 103,
                    //  h,  i,  j,  k,  l,  m,  n,  o,  p,  q,  r,
                    104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114,
                    //  s,  t,  u,  v,  w,  x,  y,  z
                    115, 116, 117, 118, 119, 120, 121, 122};
    //最近一次错误记录
    static int[] checkReult = new int[2];

    /** 部分中文、英文 Token 映射表 */
    static Map<String, String> zhEnTokenMap = new HashMap<>();

    static {
        zhEnTokenMap.put("，", ",");
        zhEnTokenMap.put("；", ";");
        zhEnTokenMap.put("）", ")");
        zhEnTokenMap.put("（", "(");
        zhEnTokenMap.put("‘", "'");
        zhEnTokenMap.put("’", "'");
        zhEnTokenMap.put("“", "\"");
        zhEnTokenMap.put("”", "\"");
        zhEnTokenMap.put("。", ".");
        zhEnTokenMap.put("｝", "}");
        zhEnTokenMap.put("｛", "{");
        zhEnTokenMap.put("＝", "=");
        zhEnTokenMap.put("！", "!");
        zhEnTokenMap.put("％", "%");
        zhEnTokenMap.put("／", "/");
        zhEnTokenMap.put("＼", "\\");
        zhEnTokenMap.put("．", ".");
    }

    /**
     * 判断一个路径是否指到外部了，比如../../test.txt就指到外部
     */
    public static boolean isOutsideOfRoot(String child) {
        if (child == null) {
            return true;
        }
        char[] array = child.toCharArray();
        int root = 0;
        if (array.length == 0) {
            return true;
        }
        int start = 0;
        if (array[0] == '/' || array[0] == '\\') {
            start = 1;
        }
        StringBuilder dir = new StringBuilder();
        for (int i = start; i < array.length; i++) {
            char c = array[i];

            if (c == '/' || c == '\\') {
                if (dir.toString().equals("..")) {
                    root++;
                    if (root == 1) {
                        return true;
                    }
                } else if (dir.length() == 0) {
                    //非法的格式
                    return true;
                } else {
                    root--;
                }
                dir.setLength(0);
            } else {
                dir.append(c);
            }

        }
        return root > 0;
    }

    /**
     * 相对路径计算
     *
     * @param siblings   当前路径
     * @param resourceId 相对路径
     * @return 绝对路径
     */
    public static String getRelPath(String siblings, String resourceId) {

        String relResourceId = null;
        if (resourceId != null && resourceId.length() != 0) {
            char cs = resourceId.charAt(0);
            if (!(cs == '\\' || cs == '/')) {
                //相对路径
                int i = siblings.length() - 1;
                boolean find = false;
                for (; i > 0; i--) {
                    char c = siblings.charAt(i);
                    if (c == '\\' || c == '/') {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    String parent = siblings.substring(0, i + 1);

                    relResourceId = parent.concat(resourceId);
                } else {
                    relResourceId = resourceId;
                }

            } else {
                relResourceId = resourceId;
            }
            if (BeetlUtil.isOutsideOfRoot(relResourceId)) {
                throw new RuntimeException("不能访问外部文件或者模板");
            }
            return relResourceId;
        } else {
            throw new RuntimeException("资源ID为空，参数错");
        }

    }

    public static Writer getWriterByByteWriter(ByteWriter byteWriter) {

        ByteWriter temp = null;
        while ((temp = byteWriter.getParent()) != null) {
            byteWriter = temp;
        }

        Writer w = null;
        if (byteWriter instanceof ByteWriter_Char) {
            ByteWriter_Char bw = (ByteWriter_Char) byteWriter;
            w = bw.getW();
        } else {
            ByteWriter_Byte bw = (ByteWriter_Byte) byteWriter;
            try {
                w = new OutputStreamWriter(bw.getOs(), bw.getCs());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
        return w;
    }

    /**
     * 自定义WebRoot路径
     */
    public static void setWebroot(String webroot) {
        BeetlUtil.webroot = webroot;
    }

    /**
     * 返回Web根路径,如果存在自定义webroot路径,则返回自定义webroot
     */
    public static String getWebRoot() {
        if (webroot != null) {
            return webroot;
        }
        try {
            String path = BeetlUtil.class.getClassLoader().getResource("").toURI().getPath();
            if (path == null) {
                throw new RuntimeException("Beetl未能自动检测到WebRoot，请手工指定WebRoot路径");
            }
            File file = new File(path);
            String root = null;
            if (file.getParentFile() != null && file.getParentFile().getParentFile() != null) {
                root = file.getParentFile().getParentFile().getCanonicalPath();
            } else {
                throw new RuntimeException("Beetl检测到Beetl.jar位于" + path + "但未能自动检测到WebRoot，请手工指定WebRoot路径");
            }

            return root;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * check 命名合法性
     *
     * @param str 需要check的字符串
     */
    public static boolean checkNameing(String str) {
        char[] commonArray = new char[str.length()];
        int len = 0;
        if (str == null || (len = str.length()) == 0) {
            return false;
        }
        str.getChars(0, len, commonArray, 0);
        int index = 0;
        char word = commonArray[index++];
        //首字母判断  不为数字 , .
        if (word >= 46 && word <= 57)
            setLog(1, word);
            //尾字母判断
        else if (commonArray[len - 1] == 46)
            setLog(len, 46);
        else
            while (true) {
                if (word < 36 || word > 122 || chars[word - 36] == 0) {
                    setLog(index + 1, word);
                    return false;
                }
                if (index == len)
                    return true;
                word = commonArray[index++];
            }
        return false;
    }

    private static void setLog(int index, int errorChar) {
        checkReult[0] = index;
        checkReult[1] = errorChar;
    }

    public static int[] getLog() {
        return checkReult;
    }

    /**
     * 出现中文字符时，提示对应的英文字符
     *
     * @param msg 可能是中文的字符串
     * @return 如果不是当前知道的中文字符，返回原来的字符串，否则提示出对应的英文字符
     */
    public static String reportChineseTokenError(String msg) {
        String maybeEnChar = zhEnTokenMap.get(msg);
        return maybeEnChar != null ? msg + " 貌似输入了中文符号,应该是 " + maybeEnChar : msg;
    }

    /*优化引擎会假定传给模板的变量是同一类型（同一个class或者具有同样接口或者父类,如果不是这样，会导致ClassCastException*/
    public static RuntimeException throwCastException(ClassCastException ex, GroupTemplate gt) {
        String clsName = gt.getConf().getEngine();
        if (clsName.equals("org.beetl.core.engine.DefaultTemplateEngine")) {
            return ex;
        } else {
            String detail = ex.getMessage();
            detail = detail + "如果采用优化引擎，会假定传给模板的变量是同一类型,如果不是，请使用directive dynamic 变量；来避免";
            throw new RuntimeException(detail, ex);
        }
    }

    public static void autoFileFunctionRegister(GroupTemplate gt, File funtionRoot, String ns, String path, String functionSuffix) {
        File[] files = funtionRoot.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                autoFileFunctionRegister(gt, f, f.getName().concat("."), path.concat(f.getName()).concat("/"), functionSuffix);
            } else if (f.getName().endsWith(functionSuffix)) {
                String resourceId = path + f.getName();
                String fileName = f.getName();
                fileName = fileName.substring(0, (fileName.length() - functionSuffix.length() - 1));
                String functionName = ns.concat(fileName);
                FileFunctionWrapper fun = new FileFunctionWrapper(resourceId);
                gt.registerFunction(functionName, fun);
            }
        }
    }

    public static String getParameterDescription(Class[] types) {
        if (types == null || types.length == 0) {
            return "()";
        }

        StringBuilder sb = new StringBuilder("(");
        for (Class clzz : types) {
            sb.append(clzz.getSimpleName()).append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

}
