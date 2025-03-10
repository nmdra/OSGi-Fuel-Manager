package com.fuel.authpublisher;

import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements IAuthService {
    private final Map<String, String> userCredentials = new HashMap<>();
    private boolean isAuthenticated = false;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    public AuthServiceImpl() {
        // Default manager credentials (Replace with a secure method)
        userCredentials.put("pass", "pass123");
    }

    @Override
    public boolean login(String username, String password) {
        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            isAuthenticated = true;
            System.out.println(GREEN + "✅ Login successful! Welcome, " + username + RESET);
            return true;
        }
        System.out.println(RED + "❌ Invalid credentials! Please try again." + RESET);
        return false;
    }

    @Override
    public void logout() {
        isAuthenticated = false;
        System.out.println(YELLOW + "👋 Logged out successfully!" + RESET);
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}

