package com.purang.camp.uiautotest.logging.webdriver;

public enum LogMapKey {
    METHOD_NAME_KEY("method_name"),

    CLASS_NAME_KEY("class_name"),

    MESSAGE_KEY("message_info"),

    RUN_STATUS_KEY("status_info");

    private String keyName;

    private LogMapKey(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return this.keyName;
    }
}
