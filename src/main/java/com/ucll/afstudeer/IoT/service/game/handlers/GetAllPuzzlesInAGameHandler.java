package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;


public class GetAllPuzzlesInAGameHandler {

    public static ServiceActionResponse<GameWithPuzzlesDto> handle(Game game, GameRepository gameRepository) {
        if (game == null)
            throw new IllegalArgumentException("No valid game given");


        var result = new GameWithPuzzlesDto(game, gameRepository.getAllDevicesInAGame(game));
        return new ServiceActionResponse<>(result);
    }
}
