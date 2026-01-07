package main.java.com.meuapp.service.loja;

public class ValidacaoLojaService {
    public static String validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }

        String regexCnpj = "^(?!^(\\d)\\1{13}$)\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}-?\\d{2}$";

        if (!cnpj.matches(regexCnpj)) {
            throw new IllegalArgumentException("CNPJ em formato inválido");
        }

        return cnpj.replaceAll("\\D", "");
    }

    public static String validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email é obrigatório.");
        }

        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(regexEmail)) {
            throw new IllegalArgumentException("Email inválido");
        }

        email = email.trim();
        return email;
    }

    public static String validarCEP(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }

        String regexCep = "^(?!^(\\d)\\1{7}$)\\d{5}-?\\d{3}$";

        if (!cep.matches(regexCep)) {
            throw new IllegalArgumentException("CEP inválido");
        }

        return cep.replaceAll("\\D", "");
    }

    public static String validarNumeroCasa(String numeroCasa) {
        if (numeroCasa == null || numeroCasa.isBlank()) {
            throw new IllegalArgumentException("Numero da casa é obrigatório.");
        }

        if (!numeroCasa.matches("^\\d+[A-Za-z-]?$") && !numeroCasa.equalsIgnoreCase("s/n")) {
            throw new IllegalArgumentException("Número da residência inválido");
        }

        return numeroCasa;
    }

    public static String validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Número de celular é obrigatório.");
        }

        String regexNumero = "^(?:\\+55\\s?)?\\(?[1-9]{2}\\)?\\s?9\\d{4}-?\\d{4}$";

        if (!telefone.matches(regexNumero)) {
            throw new IllegalArgumentException("Número de celular inválido");
        }

        return "55" + removerNaoNumeros(telefone).substring(0, 11);
    }

    private static String removerNaoNumeros(String valor) {
        return valor.replaceAll("\\D", "");
    }
}
