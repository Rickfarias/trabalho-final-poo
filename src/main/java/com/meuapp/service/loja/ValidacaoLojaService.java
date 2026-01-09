package main.java.com.meuapp.service.loja;

public class ValidacaoLojaService {
    public String validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }

        String regexCnpj = "^(?!^(\\d)\\1{13}$)\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}-?\\d{2}$";

        if (!cnpj.matches(regexCnpj)) {
            throw new IllegalArgumentException("CNPJ em formato inválido");
        }

        return cnpj.replaceAll("\\D", "");
    }

    public String validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email é obrigatório.");
        }

        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(regexEmail)) {
            throw new IllegalArgumentException("Email inválido");
        }

        return email.trim();
    }

    public String validarCEP(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }

        String regexCep = "^(?!^(\\d)\\1{7}$)\\d{5}-?\\d{3}$";

        if (!cep.matches(regexCep)) {
            throw new IllegalArgumentException("CEP inválido");
        }

        return cep.replaceAll("\\D", "");
    }

    public String validarNumeroCasa(String numeroCasa) {
        if (numeroCasa == null || numeroCasa.isBlank()) {
            throw new IllegalArgumentException("Numero da casa é obrigatório.");
        }

        if (!numeroCasa.matches("^\\d+[A-Za-z-]?$") && !numeroCasa.equalsIgnoreCase("s/n")) {
            throw new IllegalArgumentException("Número da residência inválido");
        }

        return numeroCasa;
    }

    public String validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Número de celular é obrigatório.");
        }

        String regexNumero = "^(?:\\+55\\s?)?\\(?[1-9]{2}\\)?\\s?9\\d{4}-?\\d{4}$";

        if (!telefone.matches(regexNumero)) {
            throw new IllegalArgumentException("Número de celular inválido");
        }

        String numeros = removerNaoNumeros(telefone);

        if (numeros.length() != 11) {
            throw new IllegalArgumentException("Número de celular deve conter 11 dígitos");
        }

        return "55" + numeros;
    }

    private String removerNaoNumeros(String valor) {
        return valor.replaceAll("\\D", "");
    }
}
