package main.java.com.meuapp.exception;

public class LojaBloqueadaException extends IllegalStateException {
    public LojaBloqueadaException(String message) {
        super(message);
    }
}
