package org.beetl.core.text;

public class TextFragment extends Fragment {
    StringBuilder text = new StringBuilder();
    boolean hasText = false;
    int crCount = 0;

    public TextFragment(Source source) {
        super(source);
        this.endLine = this.startLine;
        source.lastTextFragment = this;

    }


    public void appendTextFragment(Fragment fr) {
        if (fr instanceof TextFragment) {
            text.append(((TextFragment) fr).text);
        } else if (fr instanceof CRFragment) {
            text.append(((CRFragment) fr).cr);
            crCount++;
        } else {
            throw new IllegalArgumentException(fr.getClass().getName());
        }
    }


    @Override
    public StringBuilder getScript() {
        StringBuilder script = new StringBuilder();
        if (text.length() == 0) {
            return script;
        }
        Integer varName = source.getParser().getRandomeTextVarName();
        script.append("<$").append(varName).append(">>");
        for (int i = 0; i < crCount; i++) {
            script.append(TextParser.cr1);
        }

        // 添加一个静态变量
        source.parser.getTextVars().put(varName, text.toString());
        return script;
    }


    @Override
    public Fragment consumeAndReturnNext() {

        while (!source.isEof()) {
            if (source.isPlaceHolderStart()) {
                this.setEndLine();
                return new PlaceHolderFragment(source);
            } else if (source.isScriptStart()) {
                this.setEndLine();
                return new ScriptBlockFragment(source);
            } else if (source.isHtmlTagStart()) {
                this.setEndLine();
                return new HtmlTagStartFragment(source);
            } else if (source.isHtmlTagEnd()) {
                this.setEndLine();
                return new HtmlTagEndFragment(source);
            } else if (source.isCrStart()) {
                return new CRFragment(source);
            } else {
                char c = source.consumeAndGet();
                text.append(c);
            }
        }
        return null;
    }


    public boolean onlySpace() {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != ' ' && c != '\t') {
                return false;
            }
        }
        return true;
    }


    /*删除一个\*/
    public void removeTextEscape() {
        text.setLength(text.length() - 1);
    }


}
