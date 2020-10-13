package com.ucll.afstudeer.IoT.dto.in;

import javax.validation.constraints.Min;

public class GameSessionId {

    @Min(1)
    private int value;

    public GameSessionId(@Min(1) int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
