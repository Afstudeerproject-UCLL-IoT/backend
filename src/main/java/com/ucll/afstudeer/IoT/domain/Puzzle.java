package com.ucll.afstudeer.IoT.domain;

import java.util.Objects;

public class Puzzle {
    private final String name;
    private final String solution;

    private Puzzle(String name, String solution) {
        this.name = name;
        this.solution = solution;
    }

    // getters
    public String getName() {
        return name;
    }

    public String getSolution() {
        return solution;
    }

    // overrides
    @Override
    public String toString() {
        if (solution.equals("")) {
            return name;
        }

        return String.format("%s-%s", name, solution);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Puzzle) {
            var other = (Puzzle) o;
            return other.getName().equals(getName()) &&
                    other.getSolution().equals(getSolution());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSolution());
    }

    // builder
    public static class Builder {
        private String name;
        private String solution;

        public Builder withName(String name) {
            if (name == null || name.isBlank())
                throw new IllegalArgumentException("Puzzle name cannot be empty");

            this.name = name;
            return this;
        }

        public Builder withoutSolution() {
            this.solution = "";
            return this;
        }

        public Builder withSolution(String solution) {
            if (solution == null) {
                throw new IllegalArgumentException("Solution cannot be null for a puzzle");
            }

            this.solution = solution;
            return this;
        }

        public Puzzle build() {
            return new Puzzle(name, solution);
        }
    }
}
