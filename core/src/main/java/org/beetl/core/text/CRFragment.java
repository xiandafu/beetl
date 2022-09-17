package org.beetl.core.text;

public class CRFragment extends Fragment {
    char[] cr;

    public CRFragment(Source source) {
        super(source);
        int size = source.lastCrSize;
        cr = source.consumeAndGetCR(size);
    }

    @Override
    public StringBuilder getScript() {
        StringBuilder script = new StringBuilder();
        Integer varName = source.getParser().getRandomeTextVarName();
        script.append(TEXT_START).append(varName).append(RIGHT_END);
        script.append(cr);

        // 添加一个静态变量
        source.parser.getTextVars().put(varName, new String(cr));
        return script;
    }

    @Override
    public Fragment consumeAndReturnNext() {
        //回车算上一行内容
        this.endLine = this.source.curLine - 1;
        while (!source.isEof()) {
            if (source.isPlaceHolderStart()) {
                return new PlaceHolderFragment(source);
            } else if (source.isScriptStart()) {
                return new ScriptBlockFragment(source);
            } else if (source.isHtmlTagStart()) {
                return new HtmlTagStartFragment(source);
            } else if (source.isHtmlTagEnd()) {
                return new HtmlTagEndFragment(source);
            } else if (source.isCrStart()) {
                return new CRFragment(source);
            } else {
                return new TextFragment(source);
            }
        }
        return null;
    }

}
