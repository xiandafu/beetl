package org.beetl.core.text;

public abstract class Fragment {
	public static  final String RIGHT_END ="~>";
	public static  final String PS_START_1 ="<~";
	public static  final String PS_START_2 ="<#";
	public static  final String TEXT_START ="<^";

	
    protected Source source;
    protected int startLine;
    protected int endLine;
    private FragmentStatus status = FragmentStatus.normal;

    public Fragment(Source source) {
        this.source = source;
        this.setStartLine();
    }

    public abstract StringBuilder getScript();

    public abstract Fragment consumeAndReturnNext();

    protected Fragment findNext() {
        if (source.isEof()) {
            return null;
        }
        if (source.isScriptStart()) {
            this.setEndLine();
            return new ScriptBlockFragment(source);
        } else if (source.isPlaceHolderStart()) {
            this.setEndLine();
            return new PlaceHolderFragment(source);
        } else if (source.isHtmlTagStart()) {
            this.setEndLine();
            return new HtmlTagStartFragment(source);
        } else if (source.isHtmlTagEnd()) {
            this.setEndLine();
            return new HtmlTagEndFragment(source);
        } else if (source.isCrStart()) {
            return new CRFragment(source);
        } else {
            this.setEndLine();
            return new TextFragment(source);
        }
    }

    protected void setStartLine() {
        this.startLine = source.curLine;
    }

    protected void setEndLine() {
        this.endLine = source.curLine;
    }

    public FragmentStatus getStatus() {
        return status;
    }

    public void setStatus(FragmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " from " + this.startLine + " to " + this.endLine;
    }

}
