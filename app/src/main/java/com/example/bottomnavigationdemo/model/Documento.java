package com.example.bottomnavigationdemo.model;

public class Documento {

    String tipo;
    String numeroDocumento;

    public Documento(String tipo, String numeroDocumento) {
        this.tipo = tipo;
        this.numeroDocumento = numeroDocumento;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "tipo='" + tipo + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}
