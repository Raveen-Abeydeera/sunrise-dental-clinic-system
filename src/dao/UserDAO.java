package dao;

import model.User;

public interface UserDAO {
    User authenticate(String username, String password);
}