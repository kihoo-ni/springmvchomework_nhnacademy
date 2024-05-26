package com.nhnacademy.nhn_mart.domain;

import lombok.Getter;

public class CsManager {
    @Getter
    private final String id;

    @Getter
    private final String password;

    @Getter
    private final String name;

    public static CsManager create(String id, String password, String name) {
        return new CsManager(id, password, name);
    }

    private CsManager(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }


}
