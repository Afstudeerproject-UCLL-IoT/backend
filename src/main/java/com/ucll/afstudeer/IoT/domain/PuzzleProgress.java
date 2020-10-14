package com.ucll.afstudeer.IoT.domain;

import java.time.LocalDateTime;

public class PuzzleProgress {

    private final String puzzleName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int totalAttempts;

    private PuzzleProgress(String puzzleName, LocalDateTime startTime, LocalDateTime endTime, int totalAttempts) {
        this.puzzleName = puzzleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalAttempts = totalAttempts;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public boolean isCompleted() {
        return endTime != null;
    }

    public static class Builder {
        private String puzzleName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int totalAttempts;

        public Builder withPuzzleName(String puzzleName) {
            this.puzzleName = puzzleName;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder withAmountOfAttempts(int totalAttempts) {
            this.totalAttempts = totalAttempts;
            return this;
        }

        public PuzzleProgress build() {
            return new PuzzleProgress(puzzleName, startTime, endTime, totalAttempts);
        }
    }
}
