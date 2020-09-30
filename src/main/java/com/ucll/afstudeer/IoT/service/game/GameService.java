package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.dto.GameWithPuzzlesDto;

import java.time.LocalDateTime;
import java.util.List;

public interface GameService {
    boolean createGame(Game game);

    boolean startGame(Game game);

    LocalDateTime endGame(Game game);

    boolean addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle, int position);

    boolean addFirstDevicePuzzle(Game game, Device device);

    List<GameDto> getAllGames();

    GameWithPuzzlesDto getAllPuzzlesInAGame(String gameName);


}
