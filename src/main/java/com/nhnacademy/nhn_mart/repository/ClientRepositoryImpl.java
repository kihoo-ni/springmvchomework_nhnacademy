package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.Client;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final Map<String, Client> clientMap = new HashMap<>();

    public ClientRepositoryImpl() {
        clientMap.put("1", Client.create("1", "1","kihoon"));
        clientMap.put("2", Client.create("2", "2","jung"));
        clientMap.put("3", Client.create("3", "3","dong"));

    }

    @Override
    public boolean exists(String id) {
        return clientMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getClient(id))
                .map(client -> client.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public List<Client> getClients() {
        return clientMap.values().stream().toList();
    }

    @Override
    public Client getClient(String id) {
        return clientMap.get(id);
    }


}
