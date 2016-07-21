package com.easytoolsoft.easyreport.common.pair;

public class KeyValuePair {
    private String key;
    private String name;

    public KeyValuePair() {
    }

    public KeyValuePair(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setValue(String name) {
        this.name = name;
    }
}
