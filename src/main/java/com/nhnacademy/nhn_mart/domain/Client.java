package com.nhnacademy.nhn_mart.domain;

import lombok.Getter;

@Getter
public class Client {

    private final String id;

    private final String password;

   private final String name;

    public static Client create(String id, String password, String name) {
        return new Client(id, password, name);
    }

    private Client(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }


}