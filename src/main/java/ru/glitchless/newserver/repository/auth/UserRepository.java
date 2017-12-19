package ru.glitchless.newserver.repository.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.dao.UserDao;
import ru.glitchless.newserver.data.model.UserModel;
import ru.glitchless.newserver.data.throwables.InvalidLoginOrPassword;

@Component
public class UserRepository {
    private final PasswordEncoder encoder;
    private final UserDao userDao;

    public UserRepository(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.encoder = passwordEncoder;
        this.userDao = userDao;
    }

    public UserModel registerUser(UserModel userModel) {
        final UserModel userLocalModel = new UserModel(userModel.getLogin(),
                encoder.encode(userModel.getPassword()));
        userLocalModel.setEmail(userModel.getEmail());

        return userDao.addUser(userLocalModel);
    }

    public UserModel authUser(UserModel userModel) {
        final UserModel model = userDao.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPassword())) {
            throw new InvalidLoginOrPassword();
        }

        return model;
    }

    public UserModel changeUser(UserModel userModel) {
        final UserModel model = userDao.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPassword())) {
            throw new InvalidLoginOrPassword();
        }

        return userDao.updateUser(userModel.getLogin(), userModel.getEmail());
    }

    public boolean isContains(UserModel userModel) {
        final UserModel model = userDao.getUser(userModel.getLogin());

        return model != null;
    }
}
