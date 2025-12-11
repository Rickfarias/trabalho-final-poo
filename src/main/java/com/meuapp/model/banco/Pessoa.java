package main.java.com.meuapp.model.banco;

public class Pessoa {
    private String nome;
    private String senha;
    private String cpf;
    private String email;
    private String endereco;

    private static int contadorPessoa = 0;

    public Pessoa() {
        contadorPessoa++;
    }

    public Pessoa(String nome, String senha, String cpf, String email, String endereco) {
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;


        contadorPessoa++;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public static int getContadorPessoa() {
        return contadorPessoa;
    }

    @Override
    public String toString() {
        return String.format("""
               Nome: %s
               CPF: %s
               Email: %s
               Endereço: %s
               """, nome, cpf, email, endereco);
    }
}
