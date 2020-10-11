package com.ucll.afstudeer.IoT.domain.constant;

public enum ServiceError {
    None,

    // Device
    DEVICE_DOES_NOT_EXIST,

    // Puzzle
    PUZZLE_DOES_NOT_EXIST,

    // Game
    GAME_DOES_NOT_EXIST,
    GAME_HAS_NO_PUZZLES,
    INVALID_PUZZLE_SUBSCRIPTION,

    // Game session
    NO_GAME_SESSION_WAS_ACTIVE,

    // Connection activity
    CONNECTION_ACTIVITY_NOT_ADDED,
    CONNECTION_ACTIVITY_OFFLINE_NOT_SET

}
