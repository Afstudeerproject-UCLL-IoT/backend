package com.ucll.afstudeer.IoT.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {

    @DisplayName("Test if given the right input a puzzle is created")
    @Test
    public void creatingAValidPuzzleSucceeds() {
        var puzzle = new Puzzle.Builder()
                .withName("Puzzle1")
                .withSolution("SecretSentence")
                .build();

        assertNotNull(puzzle);
        assertNotNull(puzzle.getName());
        assertNotNull(puzzle.getSolution());

        assertEquals("Puzzle1", puzzle.getName());
        assertEquals("SecretSentence", puzzle.getSolution());
    }

    @DisplayName("Test the toString method call")
    @Test
    public void testToStringMethod() {
        var puzzle = new Puzzle.Builder()
                .withName("AwesomePuzzle1")
                .withSolution("SecretSentence")
                .build();

        assertEquals("AwesomePuzzle1-SecretSentence", puzzle.toString());

        var puzzleWithoutSolution = new Puzzle.Builder()
                .withName("AwesomePuzzle1")
                .withoutSolution()
                .build();

        assertEquals("AwesomePuzzle1", puzzleWithoutSolution.toString());
    }

    @DisplayName("Invalid input for puzzle creation throws an exception")
    @ParameterizedTest
    @NullSource
    public void creatingAPuzzleWithInvalidInputThrowsAnException(String input) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Puzzle.Builder()
                    .withName(input)
                    .withoutSolution();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Puzzle.Builder()
                    .withName("ValidPuzzleName")
                    .withSolution(input);
        });
    }
}