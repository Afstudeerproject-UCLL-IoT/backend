package core.domain.puzzle;

import core.domain.device.Device;
import core.exceptions.device.InvalidDeviceCreationInputException;
import core.exceptions.puzzle.InvalidPuzzleNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {

    @DisplayName("Test if given the right input a puzzle is created")
    @Test
    public void creatingAValidPuzzleSucceeds(){
        var puzzle = Puzzle.instance("Puzzle1", "SecretSentence");

        assertNotNull(puzzle);
        assertNotNull(puzzle.getName());
        assertNotNull(puzzle.getSolution());

        assertEquals("Puzzle1", puzzle.getName());
        assertEquals("SecretSentence", puzzle.getSolution());
    }

    @DisplayName("Test the toString method call")
    @Test
    public void testToStringMethod(){
        var puzzle = Puzzle.instance("AwesomePuzzle1", "");


        assertEquals("AwesomePuzzle1", puzzle.toString());
    }

    @DisplayName("Invalid input for puzzle creation throws an exception")
    @ParameterizedTest
    @NullAndEmptySource
    public void creatingADeviceFromAWrongDeviceNameGivesAnException(String puzzleName){
        assertThrows(InvalidPuzzleNameException.class, () -> Puzzle.instance(puzzleName, ""));
    }

}