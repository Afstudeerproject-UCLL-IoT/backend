package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;


public class GetAllPuzzlesInAGameHandler {

    public static ServiceActionResponse<GameWithPuzzlesDto> handle(String gameName, GameRepository gameRepository) {
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        var result = new GameWithPuzzlesDto(game, gameRepository.getAllDevicesInAGame(game));
        return new ServiceActionResponse<>(result);
    }
}
