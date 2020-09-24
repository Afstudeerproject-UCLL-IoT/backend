package core.exceptions.puzzle;

public class InvalidPuzzleNameException  extends RuntimeException{

    public InvalidPuzzleNameException(){
        super();
    }

    public InvalidPuzzleNameException(String message){
        super(message);
    }

    public InvalidPuzzleNameException(String message, Throwable throwable){
        super(message, throwable);
    }

}
