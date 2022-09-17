package org.beetl.core.om;

import java.util.Map.Entry;

public class MapEntryAA extends AttributeAccess {
    /** name: key */
    private static final String NAME_KEY = "key";
    /** name: value */
    private static final String NAME_VALUE = "value";

    @Override
    public Object value(Object o, Object name) {
        Entry entry = (Entry) o;
        if (NAME_KEY.equals(name)) {
            return entry.getKey();
        } else if (NAME_VALUE.equals(name)) {
            return entry.getValue();
        } else {
            throw new RuntimeException("无此方法entry." + name);
        }
    }

}
