package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.*;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;

public interface GameService {
    // actions
    ServiceActionResponse<Game> createGame(Game game);

    ServiceActionResponse<GameSession> startGame(Game game);

    ServiceActionResponse<GameSession> endGame(Game game);

    ServiceActionResponse<Boolean> addPuzzleSubscription(Game game, PuzzleSubscription subscription);

    ServiceActionResponse<Boolean> addPuzzleSubscriptions(Game game, List<PuzzleSubscription> subscriptions);

    // queries
    ServiceActionResponse<List<Game>> getAllGames();

    ServiceActionResponse<GameWithPuzzlesDto> getAllPuzzlesInAGame(String gameName);


}
