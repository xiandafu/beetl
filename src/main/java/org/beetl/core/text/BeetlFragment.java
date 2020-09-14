package org.beetl.core.text;

/**
 * 定界符或者占位符里的Beetl脚本
 */
public abstract class BeetlFragment extends Fragment {
    /*script 脚本*/
    protected StringBuilder script = new StringBuilder();

    public BeetlFragment(Source source) {
        super(source);
        source.beetlFragment = this;
    }


    public void removeEscape() {
        script.setLength(script.length() - 1);
    }
}
