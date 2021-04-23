package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService
 *
 * @author Tri Le Mau
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder encoder;

    @Inject
    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

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
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void removeUser(User user) {
        userDao.remove(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public boolean isTeacher(User user) {
        User find = userDao.findById(user.getId()).orElseThrow(() ->
            new IllegalArgumentException("User with given id does not exist.")
        );

        return find.getIsTeacher();
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
