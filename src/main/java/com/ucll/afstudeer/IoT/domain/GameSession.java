package com.ucll.afstudeer.IoT.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class GameSession {
    private final int id;
    private final LocalDateTime start;
    private final LocalDateTime end;

    private GameSession(int id, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    // getters
    public int getId() {
        return id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    // overrides
    @Override
    public String toString() {
        var startFormat = start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var endFormat = end.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return String.format("Game session %d: %s-%s", id, startFormat, endFormat);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof GameSession){
            var other = (GameSession)o;
            return other.getId() == getId() &&
                    other.getStart().equals(getStart()) &&
                    other.getEnd().equals(getEnd());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStart(), getEnd());
    }

    // builder
    public static class Builder {
        private int id;
        private LocalDateTime start;
        private LocalDateTime end;

        public Builder withId(int id){
            this.id = id;
            return this;
        }

        public Builder withoutId(){
            this.id = 0;
            return this;
        }

        public Builder withStartTime(LocalDateTime start){
            this.start = start;
            return this;
        }

        public Builder withEndTime(LocalDateTime end){
            this.end = end;
            return this;
        }

        public Builder withoutEndTime(){
            this.end = null;
            return this;
        }

        public GameSession build(){
            return new GameSession(id, start, end);
        }
    }
}
