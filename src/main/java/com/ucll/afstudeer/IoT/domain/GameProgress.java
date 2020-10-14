package com.ucll.afstudeer.IoT.domain;

import java.util.List;

public class GameProgress {
    private final String gameName;
    private final List<PuzzleProgress> puzzleProgresses;

    private GameProgress(String gameName, List<PuzzleProgress> puzzleProgresses) {
        this.gameName = gameName;
        this.puzzleProgresses = puzzleProgresses;
    }

    public String getGameName() {
        return gameName;
    }

    public List<PuzzleProgress> getPuzzleProgresses() {
        return puzzleProgresses;
    }

    public static class Builder {
        private String gameName;
        private List<PuzzleProgress> puzzleProgresses;

        public Builder withGameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        public Builder withPuzzleProgresses(List<PuzzleProgress> puzzleProgresses) {
            this.puzzleProgresses = puzzleProgresses;
            return this;
        }

        public GameProgress build() {
            return new GameProgress(gameName, puzzleProgresses);
        }
    }
}
