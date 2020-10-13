package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.in.GameSessionId;
import com.ucll.afstudeer.IoT.dto.in.PuzzleNewSolution;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Tag(name = "Puzzle calls")
@RestController
@RequestMapping("/puzzle")
public class PuzzleController {

    private final DeviceService deviceService;
    private final GameService gameService;

    public PuzzleController(DeviceService deviceService, GameService gameService) {
        this.deviceService = deviceService;
        this.gameService = gameService;
    }

    @PutMapping("/{puzzleName}/solution")
    @Operation(summary = "Update the solution of an existing puzzle")
    public Puzzle changePuzzleSolution(@Valid @RequestBody PuzzleNewSolution newSolution, @PathVariable String puzzleName) {
        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName(puzzleName)
                .withoutSolution()
                .build();

        // update solution
        return deviceService.updatePuzzleSolution(puzzle, newSolution.getValue())
                .getValue();
    }


    @PutMapping("/{puzzleName}/completed")
    @Operation(summary = "Complete a puzzle")
    public void setThePuzzleToCompleted(@Valid @RequestBody GameSessionId gameSessionId, @PathVariable String puzzleName) {
        // time
        var at = LocalDateTime.now();

        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName(puzzleName)
                .withoutSolution()
                .build();

        gameService.puzzleAttemptSuccessful(puzzle, gameSessionId.getValue(), at);
    }
}
