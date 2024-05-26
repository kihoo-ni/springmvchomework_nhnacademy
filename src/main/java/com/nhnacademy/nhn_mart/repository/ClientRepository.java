package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.Client;

import java.util.List;

public interface ClientRepository {
    boolean exists(String id);
    boolean matches(String id, String password);
    List<Client> getClients();
    Client getClient(String id);


}

