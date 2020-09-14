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
        for (int i = 0; i < cs.length; i++) {
            if (upper) {
                if (cs[i] == '-') {
                    continue;
                }
                sb.append(Character.toUpperCase(cs[i]));
                upper = false;


            } else {
                if (cs[i] == '-') {
                    upper = true;
                } else {
                    sb.append(cs[i]);
                }
            }


        }
        return sb.toString();
    }
}
