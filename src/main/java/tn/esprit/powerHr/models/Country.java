package tn.esprit.powerHr.models;

public class Country {
    private final String name;
    private final String code;
    private final String flag;

    public Country(String name, String code, String flag) {
        this.name = name;
        this.code = code;
        this.flag = flag;
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public String getFlag() { return flag; }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", flag, name, code);
    }
} 