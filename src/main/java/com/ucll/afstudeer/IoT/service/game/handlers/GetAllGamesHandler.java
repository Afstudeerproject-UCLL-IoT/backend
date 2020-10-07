package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;

public class GetAllGamesHandler {

    public static ServiceActionResponse<List<Game>> handle(GameRepository gameRepository) {
        var result = gameRepository.getAllGames();

        return new ServiceActionResponse<>(result);
    }
}
