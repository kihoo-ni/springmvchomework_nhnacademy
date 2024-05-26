package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.CsManager;

import java.util.List;

public interface CsManagerRepository {
    boolean exists(String id);
    boolean matches(String id, String password);
    List<CsManager> getCsManagers();
    CsManager getCsManager(String id);
}
