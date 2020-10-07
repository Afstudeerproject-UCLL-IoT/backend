package com.ucll.afstudeer.IoT.dto.in;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class GameSessionEndTime {

    @NotNull
    private LocalDateTime value;

    public GameSessionEndTime() {

    }

    public LocalDateTime getValue() {
        return value;
    }

    public void setValue(LocalDateTime value) {
        this.value = value;
    }
}
