package com.example.dietmania.Services.Settings;

import com.example.dietmania.Models.User;

import java.io.*;

/**
 * Session manager for handling user session persistence
 * Saves and loads user data from settings.dat file
 */
public class SessionManager {
    private static final String SETTINGS_FILE = "settings.dat";

    /**
     * Save user to settings file
     */
    public static void saveUser(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SETTINGS_FILE))) {
            oos.writeObject(user);
        } catch (IOException e) {
            System.err.println("Error saving user session: " + e.getMessage());
        }
    }

    /**
     * Load user from settings file
     */
    public static User loadUser() {
        File file = new File(SETTINGS_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SETTINGS_FILE))) {
            return (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading user session: " + e.getMessage());
            return null;
        }
    }

    /**
     * Clear user session
     */
    public static void clearSession() {
        File file = new File(SETTINGS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Check if session exists
     */
    public static boolean hasSession() {
        return new File(SETTINGS_FILE).exists();
    }
}
