package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.dto.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface GameService {
    // actions
    ServiceActionResponse<Game> createGame(Game game);

    ServiceActionResponse<GameSession> startGame(Game game);

    ServiceActionResponse<GameSession>  endGame(Game game);

    ServiceActionResponse<Boolean> addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle, int position);

    // queries
    ServiceActionResponse<List<GameDto>> getAllGames();

    ServiceActionResponse<GameWithPuzzlesDto> getAllPuzzlesInAGame(String gameName);


}
