package com.securecode.DomainPrimitive;

import java.util.Arrays;

public class SecureString {
    private final char[] value;

    public SecureString(final char[] value) {
        this.value = value.clone();
        Arrays.fill(value, '0');
    }

    public String getValueOnce() {
        String getOnce = new String(this.value);
        Arrays.fill(this.value, '0');
        return getOnce;
    }

    public boolean validate(IValidationInterface validationInterface) {
        return validationInterface.validate(value);
    }
}
