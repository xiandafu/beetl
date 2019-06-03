package org.beetl.core.text;

public class PlaceHolderFragment extends Fragment {
    StringBuilder script = new StringBuilder("<<");
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
        if (source.pd.isMatchFirstGroup) {
            script = new StringBuilder("<<");
        } else {
            script = new StringBuilder("<#");
        }

        while (!source.isEof()) {
        	if(source.isPlaceHolderEnd(script)){
				script.append(">>");
				break;
			}else{
				script.append(source.consumeAndGet());
			}
        }

        if (source.isEof()) {
            return null;
        }

        return super.findNext();
    }
    
   


}
