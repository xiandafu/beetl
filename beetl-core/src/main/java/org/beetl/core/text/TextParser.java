package org.beetl.core.text;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.config.BeetlConfig;
import org.beetl.core.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 文本解析成beetl脚本,按照各种定位符，html标记解析成对应的Fragment，然后再格式化，最后输出成beetl脚本
 *
 * @author xiandafu
 */
public class TextParser {

    /** DEBUG flag */
    private static final boolean DEBUG = BeetlConfig.DEBUG;
    /** Log TAG */
    private static final String TAG = "TextParser";

    Map<Integer, String> textVars = new HashMap<>();
    int textNameSuffix = 0;
    PlaceHolderDelimeter pd;
    ScriptDelimeter sd;
    Source source = null;
    StringBuilder script = new StringBuilder(64);
    //脚本的回车符，antlr解析会忽略掉
    public final static char cr1 = '\n';
    // 操作系统回车换行符号
    public final static String systemCrStr = System.getProperty("line.separator");

    SourceFragement sourceFragement = new SourceFragement();
    HtmlTagConfig htmlTagConfig = null;
    //文本里的回车
    String textCr = null;

    AttributeNameConvert attributeNameConvert;

    public TextParser(GroupTemplate groupTemplate,
                      Configuration.DelimeterHolder pdConfig,
                      Configuration.DelimeterHolder sdConfig) {
        attributeNameConvert = (groupTemplate == null)
                ? new DefaultAttributeNameConvert()
                : groupTemplate.getHtmlTagAttrNameConvert();
        this.pd = pdConfig.createPhd();
        this.sd = sdConfig.createSd();
    }

    public TextParser(GroupTemplate groupTemplate,
                      Configuration.DelimeterHolder pdConfig,
                      Configuration.DelimeterHolder sdConfig,
                      Configuration.HtmlTagHolder tagConfig) {
        this(groupTemplate, pdConfig, sdConfig);
        if (tagConfig.isSupport()) {
            this.htmlTagConfig = tagConfig.create();
        }
    }

    public TextParser(Configuration.DelimeterHolder pdConfig,
                      Configuration.DelimeterHolder sdConfig,
                      Configuration.HtmlTagHolder tagConfig) {
        this(null, pdConfig, sdConfig);
        if (tagConfig.isSupport()) {
            this.htmlTagConfig = tagConfig.create();
        }
    }

    public void doParse(Reader orginal) throws IOException {
        scan1(orginal);
        //合并
        sourceFragement.merge();

        //得到beetl脚本
        for (Fragment f : sourceFragement.list) {
            if (f.getStatus() == FragmentStatus.del) {
                continue;
            }
            script.append(f.getScript());
        }

        String cr = source.findCr();
        this.textCr = cr != null ? cr : systemCrStr;
    }

    protected void scan1(Reader orginal) throws IOException {
        StringBuilder temp = new StringBuilder();
        int bufSize = 1024;
        char[] cs = new char[bufSize];
        for (int len = orginal.read(cs); len != -1; len = orginal.read(cs)) {
            temp.append(cs, 0, len);
        }

        cs = temp.toString().toCharArray();
        source = new Source(cs);
        source.init(this, pd, sd, htmlTagConfig);
        Fragment test = new TextFragment(source);
        Fragment next = null;
        while (true) {
            next = test.consumeAndReturnNext();
            sourceFragement.add(test);
            if (next == null) {
                break;
            } else {
                test = next;
            }
        }
    }

    public StringBuilder getScript() {
        return script;
    }

    public Map<Integer, String> getTextVars() {
        return textVars;
    }

    public Integer getRandomeTextVarName() {
        return textNameSuffix++;
    }

    public void setTextVars(Map<Integer, String> textVars) {
        this.textVars = textVars;
    }

    public String getTextCr() {
        return textCr;
    }

    public static void main(String[] args) throws IOException {
        Configuration.DelimeterHolder pd = new Configuration.DelimeterHolder("${".toCharArray(), "}".toCharArray(), "#{".toCharArray(),
                "}".toCharArray());
        Configuration.DelimeterHolder sd = new Configuration.DelimeterHolder("@".toCharArray(), null, "<%".toCharArray(), "%>".toCharArray());

        Configuration.HtmlTagHolder htmlConfig = new Configuration.HtmlTagHolder();
        String text = "h#{b}";

        StringReader str = new StringReader(text);
        TextParser textParser = new TextParser(null, pd, sd, htmlConfig);
        textParser.doParse(str);
        Log.i(TAG, textParser.getTextVars().toString());
		String script = textParser.getScript().toString();
        BufferedReader reader = new BufferedReader(new StringReader(script));
        Log.i(TAG, "==============================");
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            Log.i(TAG, line);
        }
    }

}
