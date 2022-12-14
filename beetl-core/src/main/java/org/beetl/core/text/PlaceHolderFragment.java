package org.beetl.core.text;

public class PlaceHolderFragment extends BeetlFragment {


    public PlaceHolderFragment(Source source) {
        super(source);
        this.setEndLine();
    }

    @Override
    public StringBuilder getScript() {

        return script;
    }

    @Override
    public Fragment consumeAndReturnNext() {
        // 不同占位符，返回不同的标识，beetl支持俩种占位符，能表达不同语义
        script = new StringBuilder(source.pd.isMatchFirstGroup ? PS_START_1 : PS_START_2);

        while (!source.isEof()) {
            if (source.isPlaceHolderEnd(script)) {
                script.append(RIGHT_END);
                break;
            } else {
                script.append(source.consumeAndGet());
            }
        }

        return source.isEof() ? null : super.findNext();
    }

}
