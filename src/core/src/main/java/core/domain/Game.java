package core.domain;

import java.util.Objects;

public class Game {

    private final String name;

    private Game(String name) {
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    // overrides
    @Override
    public String toString() {
        return String.format("Game: %s", name);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Game){
            Game other = (Game)o;
            return other.getName().equals(getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    // builder
    public static class Builder{
        private String name;

        public Builder withName(String name){
            if(name == null || name.isBlank()){
                throw new IllegalArgumentException("Game name cannot be empty");
            }
            this.name = name;
            return this;
        }

        public Game build(){
            return new Game(name);
        }
    }
}
