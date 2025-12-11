package main.java.com.meuapp.exception;

public class SaldoInsuficienteException extends RuntimeException {
  public SaldoInsuficienteException(String mensagem) {
    super(mensagem);
  }
}

