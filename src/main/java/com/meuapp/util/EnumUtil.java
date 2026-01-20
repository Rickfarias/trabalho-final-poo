package main.java.com.meuapp.util;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

    public static <T> T selecionarDaLista(
            String titulo,
            String mensagem,
            List<T> opcoes,
            Function<T, String> displayFormatter
    ) {
        if (opcoes == null || opcoes.isEmpty()) {
            InputUtil.warn("Nenhuma opção disponível.", "AVISO");
            return null;
        }

        String[] opcoesFormatadas = opcoes.stream()
                .map(displayFormatter)
                .toArray(String[]::new);

        String selecionado = (String) JOptionPane.showInputDialog(
                null,
                mensagem,
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesFormatadas,
                opcoesFormatadas[0]
        );

        if (selecionado == null) {
            InputUtil.info("Operação cancelada.");
            return null;
        }

        int index = Arrays.asList(opcoesFormatadas).indexOf(selecionado);
        return opcoes.get(index);
    }

}
