package dao;

import model.User;

public interface UserDAO {
    boolean registerUser(String username, String password, String role, String fullName, String email);
    User authenticate(String username, String password);
}