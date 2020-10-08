package com.ucll.afstudeer.IoT.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class PuzzleAttempt {

    private final int id;
    private final LocalDateTime at;
    private final boolean success;

    // navigational props
    private final String puzzleName;
    private final int gameSessionId;

    private PuzzleAttempt(int id, LocalDateTime at, boolean success, String puzzleName, int gameSessionId) {
        this.id = id;
        this.at = at;
        this.success = success;
        this.puzzleName = puzzleName;
        this.gameSessionId = gameSessionId;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public int getGameSessionId() {
        return gameSessionId;
    }

    // equals and hash
    @Override
    public boolean equals(Object o) {
        if (o instanceof PuzzleAttempt) {
            var other = (PuzzleAttempt) o;

            return isSuccess() == other.isSuccess() &&
                    getGameSessionId() == other.getGameSessionId() &&
                    getAt().equals(other.getAt()) &&
                    getPuzzleName().equals(other.getPuzzleName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAt(), isSuccess(), getPuzzleName(), getGameSessionId());
    }

    public static class Builder {
        private int id;
        private LocalDateTime at;
        private boolean success;
        private String puzzleName;
        private int gameSessionId;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withoutId() {
            this.id = 0;
            return this;
        }

        public Builder withAttemptAt(LocalDateTime at) {
            this.at = at;
            return this;
        }

        public Builder withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder withPuzzleName(String puzzleName) {
            this.puzzleName = puzzleName;
            return this;
        }

        public Builder withGameSessionId(int gameSessionId) {
            this.gameSessionId = gameSessionId;
            return this;
        }

        public PuzzleAttempt build() {
            return new PuzzleAttempt(id, at, success, puzzleName, gameSessionId);
        }
    }
}
