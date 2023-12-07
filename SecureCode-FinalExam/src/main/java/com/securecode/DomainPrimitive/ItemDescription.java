package com.securecode.DomainPrimitive;

public class ItemDescription {
    private final String value;

    private final int minlength = 4;
    private final int maxlength = 50;

    public String getValue() {
            return value;
    }
    
    public ItemDescription(String value) throws Exception {
        validate(value);
        this.value = value;
    }

    private void validate(String value) throws Exception {
        if (value.length() < minlength)
            throw new Exception("Invalid ItemDescription Length");
        if (value.length() > maxlength)
            throw new Exception("Invalid ItemDescription Length");
    }

    public String toString() {
        return value;
    }
}
