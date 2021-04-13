package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.entity.User;

import java.util.List;

public interface UserFacade {
    // void register(UserDTO, password)
    // boolean authenticate(UserAuthenticateDTO)
    List<User> getAllUsers();
    List<User> getAllStudents();
    List<User> getAllTeachers();
}
