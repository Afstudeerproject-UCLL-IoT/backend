package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllGamesHandler {

    public static List<GameDto> handle(GameRepository gameRepository){
        return gameRepository.getAllGames()
                .stream()
                .map(game -> new GameDto(game.getName()))
                .collect(Collectors.toList());
    }
}
