package com.ucll.afstudeer.IoT.dto.in;

import javax.validation.constraints.NotBlank;


public class PuzzleNewSolution {

    @NotBlank
    private String value;

    public PuzzleNewSolution() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
