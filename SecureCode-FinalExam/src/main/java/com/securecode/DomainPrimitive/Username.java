package com.securecode.DomainPrimitive;

import java.util.regex.Pattern;

public class Username {
    private final String value;

    private final int minlength = 4;
    private final int maxlength = 32;

    public String getValue() {
        return value;
    }

    public Username(String value) throws Exception {
        validate(value);
        this.value = value;
    }

    private void validate(String value) throws Exception {
        if (value.length() < minlength)
            throw new Exception("Invalid Username Length");
        if (value.length() > maxlength)
            throw new Exception("Invalid Username Length");
    
  		String regexUsername = "[a-zA-Z0-9]+";

		if (!Pattern.matches(regexUsername, value)) {
			throw new Exception("Invalid Username characters");
		}
    }

    public String toString() {
        return value;
    }
}
