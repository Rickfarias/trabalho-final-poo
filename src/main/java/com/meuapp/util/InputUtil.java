package main.java.com.meuapp.util;

import main.java.com.meuapp.exception.CampoInvalidoException;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InputUtil {

    /*
    * TODO: Criar showMessagesDialogs com titulo e information messages
    */

    public static String inputString(String mensagem) {
        return JOptionPane.showInputDialog(null, mensagem);
    }

    public static int inputInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(
                        JOptionPane.showInputDialog(null, mensagem)
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Digite um número inteiro válido!",
                        "Erro de entrada",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static double inputDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(
                        JOptionPane.showInputDialog(null, mensagem)
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Digite um número válido!",
                        "Erro de entrada",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void info(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public static void warn(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public static void error(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    public static <T> T inputValidado(Supplier<T> fornecedor, Consumer<T> validador) {
        while (true) {
            try {
                T valor = fornecedor.get();
                validador.accept(valor);
                return valor;
            } catch (IllegalArgumentException e) {
                info(e.getMessage());
            }
        }
    }

    public static int inputOpcao(String menuLoja, String s, String[] opcoes) {
        return 0;
    }
}

