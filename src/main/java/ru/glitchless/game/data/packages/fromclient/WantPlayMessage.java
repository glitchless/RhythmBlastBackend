package ru.glitchless.game.data.packages.fromclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class WantPlayMessage extends WebSocketMessage {
    private int state;
    private Object data;

    public WantPlayMessage(@JsonProperty("type") String type) {

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
