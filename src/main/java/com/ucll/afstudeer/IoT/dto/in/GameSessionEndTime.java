package com.ucll.afstudeer.IoT.dto.in;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class GameSessionEndTime {

    @NotNull
    private LocalDateTime endTime;

    public GameSessionEndTime() {

    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
