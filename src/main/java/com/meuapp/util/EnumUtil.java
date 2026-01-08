package main.java.com.meuapp.util;

import javax.swing.*;

public class EnumUtil {
    public static <T extends Enum<T>> T selecionarEnum(
            String titulo,
            String mensagem,
            T[] valores
    ) {
        T selecionado = (T) JOptionPane.showInputDialog(
                null,
                mensagem,
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                valores,
                valores[0]
        );

        if (selecionado == null) {
            InputUtil.info("Operação cancelada.");
        }

        return selecionado;
    }

}
