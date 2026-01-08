package main.java.com.meuapp.service.loja;

/*
* TODO: Criar lógica de ativação ou bloqueio da loja
* TODO: Fazer método para listar as Lojas existentes
*/

import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.StatusLoja;
import main.java.com.meuapp.model.varejo.Loja;
import main.java.com.meuapp.repository.LojaRepository;

import java.math.BigDecimal;

public class LojaService {
    public static Loja cadastrarLoja(
            String nomeLoja,
            String cnpj,
            Endereco endereco,
            Categoria categoria,
            Contato contato,
            StatusLoja statusLoja,
            BigDecimal caixaLoja,
            ContaBancaria contaEmpresarial) {

        Loja loja = new Loja(
                nomeLoja.trim(),
                cnpj,
                endereco,
                categoria,
                contato,
                statusLoja,
                caixaLoja,
                contaEmpresarial
        );

        LojaRepository.salvarLoja(loja);
        return loja;
    }

    private static void validarLoja(
            String nomeLoja,
            Endereco endereco,
            Categoria categoria,
            Contato contato,
            StatusLoja statusLoja,
            BigDecimal caixaLoja) {

        validarNomeLoja(nomeLoja);
        validarEndereco(endereco);
        validarCategoria(categoria);
        validarContato(contato);
        validarStatusLoja(statusLoja);
        validarCaixaLoja(caixaLoja);
    }

    public static void validarNomeLoja(String nomeLoja) {
        if (nomeLoja == null || nomeLoja.isBlank()) {
            throw new IllegalArgumentException("Nome da loja é obrigatório");
        }
    }

    public static void validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }
    }

    public static void validarStatusLoja(StatusLoja statusLoja) {
        if (statusLoja == null) {
            throw new IllegalArgumentException("Status da loja é obrigatório");
        }
    }

    public static void validarCaixaLoja(BigDecimal caixaLoja) {
        if (caixaLoja == null || caixaLoja.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Caixa da loja inválido");
        }
    }


    public static void validarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }
    }


    public static void validarContato(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("Contato é obrigatório.");
        }
    }


    public static void validarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria é obrigatória.");
        }
    }

    public static void listarLojas() {
        LojaRepository.listarLojas();
    }
}
