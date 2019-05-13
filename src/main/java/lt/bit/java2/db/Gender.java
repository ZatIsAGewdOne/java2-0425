package lt.bit.java2.db;

public enum Gender {

    MALE("M"), FEMALE("F");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    static public Gender fromValue(String value) {
        if ("M".equals(value)) return MALE;
        else if ("F".equals(value)) return FEMALE;
        else return null;
    }
}
