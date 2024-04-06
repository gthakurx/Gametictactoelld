package exceptions;

public class InvalidGameParamException extends Exception{

    public InvalidGameParamException(String invalidParamsForTheGame) {
        super(invalidParamsForTheGame);
    }
}
