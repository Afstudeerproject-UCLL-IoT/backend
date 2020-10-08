package com.ucll.afstudeer.IoT.dto.in;

import javax.validation.constraints.NotBlank;

public class NewGame {

    @NotBlank
    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
