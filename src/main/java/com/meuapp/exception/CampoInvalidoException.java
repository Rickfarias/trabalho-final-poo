package main.java.com.meuapp.exception;

public class CampoInvalidoException extends IllegalArgumentException {
    public CampoInvalidoException(String message) {
        super(message);
    }
}
