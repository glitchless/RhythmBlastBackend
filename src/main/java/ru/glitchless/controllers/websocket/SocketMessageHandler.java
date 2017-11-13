package ru.glitchless.controllers.websocket;

import com.sun.istack.internal.NotNull;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.models.WebSocketMessage;
import ru.glitchless.data.throwables.HandleException;

public abstract class SocketMessageHandler<T extends WebSocketMessage> {
    @NotNull
    private final Class<T> clazz;

    public SocketMessageHandler(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    public void handleMessage(@NotNull WebSocketMessage message, @NotNull UserModel forUser) throws HandleException {
        try {
            handle(clazz.cast(message), forUser);
        } catch (ClassCastException ex) {
            throw new HandleException("Can't read incoming message of type " + message.getClass(), ex);
        }
    }

    public abstract void handle(@NotNull T message, @NotNull UserModel forUser) throws HandleException;
}
