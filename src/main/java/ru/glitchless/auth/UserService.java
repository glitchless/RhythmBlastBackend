package ru.glitchless.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.glitchless.auth.validators.IUserValidator;
import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final IUserValidator validator;
    private final PasswordEncoder encoder;

    private ConcurrentHashMap<String, UserLocalModel> usersByLogin = new ConcurrentHashMap<>();

    public UserService(PasswordEncoder passwordEncoder, IUserValidator validator) {
        this.encoder = passwordEncoder;
        this.validator = validator;
    }

    public UserLocalModel registerUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        if (usersByLogin.containsKey(userModel.getLogin())) {
            throw new InvalidData("User already exist");
        }

        final UserLocalModel userLocalModel = new UserLocalModel(userModel.getLogin(),
                encoder.encode(userModel.getPassword()));
        userLocalModel.setEmail(userModel.getEmail());

        usersByLogin.put(userModel.getLogin(), userLocalModel);

        return userLocalModel;
    }

    public UserLocalModel authUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        final UserLocalModel model = usersByLogin.get(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPasswordBCrypt())) {
            throw new InvalidData("Invalid login or password");
        }

        return model;
    }

    public UserModel changeUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        final UserLocalModel model = usersByLogin.get(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPasswordBCrypt())) {
            throw new InvalidData("Invalid login or password");
        }

        model.setEmail(userModel.getEmail());

        final UserModel outputMode = new UserModel(userModel.getLogin(), null);
        outputMode.setEmail(userModel.getEmail());
        return outputMode;
    }
}
