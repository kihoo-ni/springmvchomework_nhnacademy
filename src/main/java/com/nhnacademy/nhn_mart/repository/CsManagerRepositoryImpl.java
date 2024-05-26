package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.CsManager;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CsManagerRepositoryImpl implements CsManagerRepository {
    private final Map<String, CsManager> clientMap = new HashMap<>();

    public CsManagerRepositoryImpl() {
        clientMap.put("1", CsManager.create("1", "1","매니저"));

    }

    @Override
    public boolean exists(String id) {
        return clientMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getCsManager(id))
                .map(client -> client.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public List<CsManager> getCsManagers() {
        return clientMap.values().stream().toList();
    }

    @Override
    public CsManager getCsManager(String id) {
        return clientMap.get(id);
    }

}
