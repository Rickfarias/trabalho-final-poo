package main.java.com.meuapp.repository;

import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.enums.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FornecedorRepository {
    private final List<Fornecedor> fornecedores = new ArrayList<>();

    public FornecedorRepository() {
        inicializarFornecedoresPadrao();
    }

    private void inicializarFornecedoresPadrao() {
        Pessoa titular1 = new Pessoa(
                "Distribuidora Moda Ltda",
                "1234",
                "12.345.678/0001-10",
                "teste@teste.com",
                "teste, teste, 1234");
        ContaBancaria conta1 = new ContaBancaria(titular1, 50000.0);
        Contato contato1 = new Contato("(85) 3200-1000", "contato@modaltda.com.br");
        Fornecedor fornecedor1 = new Fornecedor(
                "Moda Fashion Distribuidora",
                "12.345.678/0001-10",
                Categoria.VESTUARIO,
                contato1,
                conta1
        );
        fornecedores.add(fornecedor1);

        // Fornecedor 2 - Eletrônicos
        Pessoa titular2 = new Pessoa(
                "TechSupply S.A.",
                "1234",
                "23.456.789/0001-20",
                "teste@teste.com",
                "teste, teste, 123");
        ContaBancaria conta2 = new ContaBancaria(titular2, 100000.0);
        Contato contato2 = new Contato("(85) 3300-2000", "vendas@techsupply.com.br");
        Fornecedor fornecedor2 = new Fornecedor(
                "TechSupply Eletrônicos",
                "23.456.789/0001-20",
                Categoria.ELETRONICOS,
                contato2,
                conta2
        );
        fornecedores.add(fornecedor2);

        // Fornecedor 3 - Alimentos
        Pessoa titular3 = new Pessoa("Alimentos Frescos Ltda",
                "1234",
                "34.567.890/0001-30",
                "teste@teste.com",
                "teste, teste, 123");
        ContaBancaria conta3 = new ContaBancaria(titular3, 75000.0);
        Contato contato3 = new Contato("(85) 3400-3000", "comercial@alimentosfrescos.com.br");
        Fornecedor fornecedor3 = new Fornecedor(
                "Alimentos Frescos",
                "34.567.890/0001-30",
                Categoria.ALIMENTOS,
                contato3,
                conta3
        );
        fornecedores.add(fornecedor3);

        // Fornecedor 4 - Eletrodomésticos
        Pessoa titular4 = new Pessoa(
                "Casa & Conforto Distribuidora",
                "1234",
                "45.678.901/0001-40",
                "teste@teste.com",
                "teste, teste, 123");
        ContaBancaria conta4 = new ContaBancaria(titular4, 80000.0);
        Contato contato4 = new Contato("(85) 3500-4000", "vendas@casaconforto.com.br");
        Fornecedor fornecedor4 = new Fornecedor(
                "Casa & Conforto",
                "45.678.901/0001-40",
                Categoria.ELETRODOMESTICOS,
                contato4,
                conta4
        );
        fornecedores.add(fornecedor4);

        // Fornecedor 5 - Informática
        Pessoa titular5 = new Pessoa(
                "InfoTech Distribuidora",
                "1234",
                "56.789.012/0001-50",
                "teste@teste.com",
                "teste, teste, 123");
        ContaBancaria conta5 = new ContaBancaria(titular5, 120000.0);
        Contato contato5 = new Contato("(85) 3600-5000", "atacado@infotech.com.br");
        Fornecedor fornecedor5 = new Fornecedor(
                "InfoTech Distribuidora",
                "56.789.012/0001-50",
                Categoria.INFORMATICA,
                contato5,
                conta5
        );
        fornecedores.add(fornecedor5);
    }

    public void salvar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }

    public Optional<Fornecedor> buscarProdutoPorId(String idFornecedor) {
        for (Fornecedor f : fornecedores) {
            if (f.getContaEmpresarialFornecedor().getId().equals(idFornecedor)) {
                return Optional.of(f);
            }
        }
        return Optional.empty();
    }

    public List<Fornecedor> acharTodos() {
        return new ArrayList<>(fornecedores);
    }


}
