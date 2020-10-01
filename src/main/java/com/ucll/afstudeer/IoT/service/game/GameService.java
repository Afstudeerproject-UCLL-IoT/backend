package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.dto.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface GameService {
    // actions
    Game createGame(Game game);

    ServiceActionResponse startGame(Game game);

    LocalDateTime endGame(Game game);

    ServiceActionResponse addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle, int position);

    // TODO remove this abomination (method above has a position now)
    boolean addFirstDevicePuzzle(Game game, Device device);

    // queries
    List<GameDto> getAllGames();

    GameWithPuzzlesDto getAllPuzzlesInAGame(String gameName);


}
