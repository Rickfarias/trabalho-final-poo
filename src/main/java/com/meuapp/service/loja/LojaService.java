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

public class LojaService {
    public static Loja cadastrarLoja(String nomeLoja, String cnpj, Endereco endereco, Categoria categoria, Contato contato, StatusLoja statusLoja, BigDecimal caixaLoja) {
        ValidacaoLojaService.validarCNPJ(cnpj);
        ValidacaoLojaService.validarEmail(contato.getEmail());
        ValidacaoLojaService.validarTelefone(contato.getTelefone());
        ValidacaoLojaService.validarTelefone(endereco.getCEP());
        ValidacaoLojaService.validarNumeroCasa(endereco.getNumero());

        Loja loja = new Loja(nomeLoja,  cnpj, endereco, categoria, contato, statusLoja, caixaLoja);

        LojaRepository.salvarLoja(loja);

        return loja;
    }
}
