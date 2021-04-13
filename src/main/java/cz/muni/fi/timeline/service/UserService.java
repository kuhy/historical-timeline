package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.User;

import java.util.List;

public interface UserService {
    void registerUser(User user, String password);
    boolean authenticateUser(User user, String password);
    List<User> getAllUsers();
    List<User> getAllStudents();
    List<User> getAllTeachers();
}
