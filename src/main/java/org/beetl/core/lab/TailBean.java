package org.beetl.core.lab;

import java.util.HashMap;
import java.util.Map;

class TailBean {
    protected Map<String, Object> extMap = new HashMap<>();
    boolean hasLazy = false;

    public Object get(String key) {
        return extMap.get(key);
    }

    public void set(String key, Object value) {
        this.extMap.put(key, value);
    }

    public Map<String, Object> getTails() {
        return extMap;
    }

}
