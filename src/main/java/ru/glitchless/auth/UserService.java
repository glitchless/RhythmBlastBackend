package ru.glitchless.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glitchless.auth.validators.IUserValidator;
import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;
import ru.glitchless.utils.IPropertiesFile;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    final IPropertiesFile propertiesFile;

    final IUserValidator validator;

    private ConcurrentHashMap<String, UserLocalModel> usersByLogin = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, UserLocalModel> usersBySession = new ConcurrentHashMap<>();

    @Autowired
    public UserService(IPropertiesFile propertiesFile, IUserValidator validator) {
        this.propertiesFile = propertiesFile;
        this.validator = validator;
    }

    /**
     * @param userModel New user
     * @return session Not-null if user sucs register
     */
    public String registerUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        if (usersByLogin.containsKey(userModel.getLoginOrEmail())) {
            throw new InvalidData("User already exist");
        }

        final UserLocalModel userLocalModel = new UserLocalModel(userModel.getLoginOrEmail(),
                userModel.getPassword(),
                propertiesFile.getSalt());
        userLocalModel.setEmail(userModel.getEmail());

        usersByLogin.put(userModel.getLoginOrEmail(), userLocalModel);

        return getSession(userLocalModel);
    }

    public void logout(String session) {
        usersBySession.remove(session);
    }

    private String getSession(UserLocalModel localModel) {
        final String session = UUID.randomUUID().toString();
        usersBySession.put(session, localModel);
        return session;
    }

    public UserModel getUserBySession(String session) {
        final UserLocalModel user = usersBySession.get(session);

        if (user == null) {
            return null;
        }

        return new UserModel(user.getLoginOrEmail(), null).setEmail(user.getEmail());
    }

    public String authUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        final UserLocalModel model = usersByLogin.get(userModel.getLoginOrEmail());

        if (model == null || !model.comparePassword(userModel.getPassword())) {
            throw new InvalidData("Invalid login or password");
        }

        return getSession(model);
    }
}
