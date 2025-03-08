package com.fuel.authservice;

public interface IAuthService {
    boolean login(String username, String password);
    void logout();
}
