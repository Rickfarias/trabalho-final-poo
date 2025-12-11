package main.java.com.meuapp.util;

import javax.swing.*;

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
}

