package lt.bit.java2.db;

public enum Gender {

    MALE('M'), FEMALE('F');

    private char value;

    Gender(char value) {
        this.value = value;
    }

    public char getValue() {
        return this.value;
    }

    static public Gender fromValue(Character value) {
        if (value == null) return null;

        if ('M' == value) return MALE;
        else if ('F' == value) return FEMALE;
        else return null;
    }

    static public Gender fromValue(String value) {
        if (value == null || value.isEmpty()) return null;

        return fromValue(value.charAt(0));
    }
}
