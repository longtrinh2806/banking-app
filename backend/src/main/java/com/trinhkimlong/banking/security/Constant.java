package com.trinhkimlong.banking.security;

public class Constant {
    private final String SECRET_KEY = "89a74eecd80b96757fecc95d7b689157600366d5c114fb8794ae07ea2f7c7fd2";
    private final String HEADER = "Authorization";
    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public String getHEADER() {
        return HEADER;
    }
}
