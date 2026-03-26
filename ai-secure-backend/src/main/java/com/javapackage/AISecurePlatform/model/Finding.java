package com.javapackage.AISecurePlatform.model;

public class Finding {

    private String type;
    private String risk;
    private int line;

    public Finding(String type, String risk, int line) {
        this.type = type;
        this.risk = risk;
        this.line = line;
    }

    public String getType() {
        return type;
    }

    public String getRisk() {
        return risk;
    }

    public int getLine() {
        return line;
    }
}
