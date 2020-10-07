package com.ucll.afstudeer.IoT.domain;

public class PuzzleSubscription {

    private final Device subscriber;
    private final Puzzle puzzle;
    private final int position;

    private PuzzleSubscription(Device subscriber, Puzzle puzzle, int position) {
        this.subscriber = subscriber;
        this.puzzle = puzzle;
        this.position = position;
    }

    public Device getSubscriber() {
        return subscriber;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getPosition() {
        return position;
    }

    public static class Builder {
        private Device subscriber;
        private Puzzle puzzle;
        private int position;

        public Builder withSubscriber(Device subscriber) {
            this.subscriber = subscriber;
            return this;
        }

        public Builder withPuzzle(Puzzle puzzle) {
            this.puzzle = puzzle;
            return this;
        }

        public Builder withPosition(int position) {
            this.position = position;
            return this;
        }

        public PuzzleSubscription build() {
            return new PuzzleSubscription(subscriber, puzzle, position);
        }
    }
}


