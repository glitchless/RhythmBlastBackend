package ru.glitchless.game.data;

import ru.glitchless.server.data.models.WebSocketMessage;
import ru.glitchless.server.data.models.WebSocketUser;

public class ProcessingCommit<T extends WebSocketMessage> {
    private T message;
    private WebSocketUser user;

    public ProcessingCommit(T message, WebSocketUser user) {
        this.message = message;
        this.user = user;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public WebSocketUser getUser() {
        return user;
    }

    public void setUser(WebSocketUser user) {
        this.user = user;
    }
}
