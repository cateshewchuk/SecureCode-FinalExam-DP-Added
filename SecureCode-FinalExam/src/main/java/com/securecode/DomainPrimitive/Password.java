package com.securecode.DomainPrimitive;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Password {
    private final char[] value;

    private final int minlength = 6;
    private final int maxlength = 32;


    public Password(final char[] value) throws Exception {
        validate(value);

        this.value = value.clone();
        Arrays.fill(value, '0');
    }

   private void validate(char[] value) throws Exception {
        if (value.length < minlength)
            throw new Exception("Invalid Username Length");
        if (value.length > maxlength)
            throw new Exception("Invalid Username Length");

		String regexPassword = "[a-zA-Z0-9!@#$%^&*()]+";

		if (!Pattern.matches(regexPassword, new String(value))) {
			throw new Exception("Invalid password characters");
		}

    }

    public String getValueOnce() {
        String getOnce = new String(this.value);
        Arrays.fill(this.value, '0');
        return getOnce;
    }

    public String getValue() {
        return "Password: *******";
    }

    public String toString() {
        return "Password: *******";
    }

}
