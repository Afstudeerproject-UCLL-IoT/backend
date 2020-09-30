package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.dto.DevicePuzzleDto;
import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.dto.GamePuzzleEntry;
import com.ucll.afstudeer.IoT.dto.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;

import java.util.stream.Collectors;

public class GetAllPuzzlesInAGameHandler {

    public static GameWithPuzzlesDto handle(String gameName, GameRepository gameRepository) {
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        return new GameWithPuzzlesDto(
                game.getName(),
                gameRepository.getAllDevicesInAGame(game)
                        .stream()
                        .map(device -> new GamePuzzleEntry(
                                device.getId(),
                                device.getType().toString(),
                                device.getPuzzle().getName(),
                                device.getPuzzle().getSolution(),
                                1) // TODO FIX POSITION
                        )
                        .collect(Collectors.toList())
        );
    }
}
