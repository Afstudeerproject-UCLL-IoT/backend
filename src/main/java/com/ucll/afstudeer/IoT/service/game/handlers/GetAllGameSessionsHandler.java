package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.dto.out.GameWithSessionsDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class GetAllGameSessionsHandler {

    public static ServiceActionResponse<GameWithSessionsDto> handle(Game game, GameRepository gameRepository) {
        var dto = new GameWithSessionsDto(game, gameRepository.getAllGameSessions(game));

        return new ServiceActionResponse<>(dto);
    }
}
