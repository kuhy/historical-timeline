package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService
 *
 * @author Tri Le Mau
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    private PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public void registerUser(User user, String unencryptedPassword) {
        user.setHashedPassword(encoder.encode(unencryptedPassword));
        userDao.create(user);
    }

    @Override
    public boolean authenticateUser(User user, String password) {
        return encoder.matches(password, user.getHashedPassword());
    }

    @Override
    public boolean isTeacher(User user) {
        Optional<User> find = userDao.findById(user.getId());
        return find.get().getIsTeacher();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getAllStudents() {
        return userDao.findAllStudents();
    }

    @Override
    public List<User> getAllTeachers() {
        return userDao.findAllTeachers();
    }
}
