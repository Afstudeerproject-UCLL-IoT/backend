package core.domain;

import core.exceptions.puzzle.InvalidPuzzleNameException;

import java.util.Objects;

public class Puzzle {
    private final String name;
    private final String solution;

    private Puzzle(String name, String solution) {
        this.name = name;
        this.solution = solution;
    }

    public static Puzzle instance(String name, String solution){
        if(name == null || name.isBlank())
            throw new InvalidPuzzleNameException();

        return new Puzzle(name, solution);
    }

    public String getName() {
        return name;
    }

    public String getSolution() {
        return solution;
    }


    // overrides
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Puzzle){
            var other = (Puzzle) o;

            return getName().equals(other.getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
