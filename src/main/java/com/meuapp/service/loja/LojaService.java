package main.java.com.meuapp.service.loja;

/*
* TODO: Criar lógica de ativação ou bloqueio da loja
*/

import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.StatusLoja;
import main.java.com.meuapp.model.varejo.Loja;
import main.java.com.meuapp.repository.LojaRepository;

import java.math.BigDecimal;
import java.util.List;

public class LojaService {
    public static Loja cadastrarLoja(
            String nomeLoja,
            String cnpj,
            Endereco endereco,
            Categoria categoria,
            Contato contato,
            StatusLoja statusLoja,
            BigDecimal caixaLoja) {

        if (nomeLoja == null || nomeLoja.isBlank()) {
            throw new IllegalArgumentException("Nome da loja é obrigatório");
        }

        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }

        if (statusLoja == null) {
            throw new IllegalArgumentException("Status da loja é obrigatório");
        }

        if (caixaLoja == null || caixaLoja.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Caixa da loja inválido");
        }

        if (endereco == null) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }

        if (contato == null) {
            throw new IllegalArgumentException("Contato é obrigatório.");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria é obrigatória.");
        }

        cnpj = ValidacaoLojaService.validarCNPJ(cnpj);
        contato.setEmail(ValidacaoLojaService.validarEmail(contato.getEmail()));
        contato.setTelefone(ValidacaoLojaService.validarTelefone(contato.getTelefone()));
        endereco.setCEP(ValidacaoLojaService.validarCEP(endereco.getCEP()));
        endereco.setNumero(ValidacaoLojaService.validarNumeroCasa(endereco.getNumero()));

        Loja loja = new Loja(
                nomeLoja.trim(),
                cnpj,
                endereco,
                categoria,
                contato,
                statusLoja,
                caixaLoja);

        LojaRepository.salvarLoja(loja);

        return loja;
    }
}
