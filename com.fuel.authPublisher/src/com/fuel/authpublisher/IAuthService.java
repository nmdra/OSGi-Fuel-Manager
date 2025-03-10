package com.fuel.authpublisher;

public interface IAuthService {
    boolean login(String username, String password);
    void logout();
}
