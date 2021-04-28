package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
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
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        if (unencryptedPassword == null) {
            throw new IllegalArgumentException("Password is null.");
        }

        try {
            user.setHashedPassword(encoder.encode(unencryptedPassword));
            userDao.create(user);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public boolean authenticateUser(User user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password is null.");
        }

        return encoder.matches(password, user.getHashedPassword());
    }

    @Override
    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        try {
            userDao.update(user);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        try {
            userDao.remove(user);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }

        try {
            return userDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username is null.");
        }

        try {
            return userDao.findByUserName(username);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public boolean isTeacher(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        try {
            User find = userDao.findById(user.getId()).orElseThrow(() ->
                new ServiceLayerException("User with given id does not exist.")
            );

            return find.getIsTeacher();
        } catch (ServiceLayerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllStudents() {
        try {
            return userDao.findAllStudents();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllTeachers() {
        try {
            return userDao.findAllTeachers();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
