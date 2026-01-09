package main.java.com.meuapp.service.banco;

/*
* TODO: Adicionar regras de negocio de validacao aqui
*   CRIAR NOVAS validacoes, para email, cpf...
*       regex de email e cpf,
*  separar endereco em Rua, nº, bairro...
*/

import main.java.com.meuapp.util.InputUtil;

public class ValidacaoService {

    public String confirmarSenha(String senha) {
        while (true) {
            String confirmar = InputUtil.inputString("Confirme a senha:");

            if (senha.equals(confirmar)) {
                return senha;
            }

            InputUtil.info("Senhas não coincidem, tente novamente.");
            senha = InputUtil.inputString("Digite a senha novamente:");
        }
    }

    public String validarCPF(String cpf) {
        return null;
    }
}
