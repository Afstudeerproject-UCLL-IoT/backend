package core.domain.puzzle;

public class Puzzle {
    private final String name;
    private final String solution;

    private Puzzle(String name, String solution) {
        this.name = name;
        this.solution = solution;
    }

    public String getName() {
        return name;
    }

    public static Puzzle instance(String name){
        return new Puzzle(name, "");
    }

    public String getSolution() {
        return solution;
    }
}
