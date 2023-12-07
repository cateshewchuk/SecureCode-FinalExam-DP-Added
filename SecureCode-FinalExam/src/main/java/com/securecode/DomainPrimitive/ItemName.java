package com.securecode.DomainPrimitive;

public class ItemName {
    private final String value;

    private final int minlength = 4;
    private final int maxlength = 25;

    public String getValue() {
            return value;
    }
    
    public ItemName(String value) throws Exception {
        validate(value);
        this.value = value;
    }

    private void validate(String value) throws Exception {
        if (value.length() < minlength)
            throw new Exception ("Invalid ItemName Length");
        if (value.length() > maxlength)
            throw new Exception ("Invalid ItemName Length");
    }

    public String toString() {
        return value;
    }
}
