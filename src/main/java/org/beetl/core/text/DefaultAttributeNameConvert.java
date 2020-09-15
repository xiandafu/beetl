package org.beetl.core.text;

/**
 * html标签的属性名转为为变量名字 ，user-provider 转化userProvider
 *
 * @author xiandafu
 */
public class DefaultAttributeNameConvert implements AttributeNameConvert {

    @Override
    public String convert(String orginal) {
        char[] cs = orginal.toCharArray();
        StringBuilder sb = new StringBuilder(cs.length);
        boolean upper = false;
        for (char c : cs) {
            if (upper) {
                if (c == '-') {
                    continue;
                }
                sb.append(Character.toUpperCase(c));
                upper = false;
            } else {
                if (c == '-') {
                    upper = true;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
