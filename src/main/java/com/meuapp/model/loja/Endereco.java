package main.java.com.meuapp.model.loja;

public class Endereco {
    private String CEP;
    private String estado;
    private String cidade;
    private String Bairro;
    private String Rua;
    private String numero;

    public Endereco(String CEP, String estado, String cidade, String bairro, String rua, String numero) {
        this.CEP = CEP;
        this.estado = estado;
        this.cidade = cidade;
        Bairro = bairro;
        Rua = rua;
        this.numero = numero;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String rua) {
        Rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
