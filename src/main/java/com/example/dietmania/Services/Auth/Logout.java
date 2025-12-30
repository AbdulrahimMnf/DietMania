package com.example.dietmania.Services.Auth;
import com.example.dietmania.Services.Settings.SessionManager;

public class Logout {

    public static void logout() {
        SessionManager.clearSession();
    }
}

