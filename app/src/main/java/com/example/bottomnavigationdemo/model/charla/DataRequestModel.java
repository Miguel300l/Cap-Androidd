package com.example.bottomnavigationdemo.model.charla;

public class DataRequestModel {
    String fechaSolicitada;
    String motivo;
    String id_aprendiz;
    String id_profesional;

    public DataRequestModel() {
    }

    public DataRequestModel(String fechaSolicitada, String motivo, String id_aprendiz, String id_profesional) {
        this.fechaSolicitada = fechaSolicitada;
        this.motivo = motivo;
        this.id_aprendiz = id_aprendiz;
        this.id_profesional = id_profesional;
    }

    public String getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(String fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getId_aprendiz() {
        return id_aprendiz;
    }

    public void setId_aprendiz(String id_aprendiz) {
        this.id_aprendiz = id_aprendiz;
    }

    public String getId_profesional() {
        return id_profesional;
    }

    public void setId_profesional(String id_profesional) {
        this.id_profesional = id_profesional;
    }

    @Override
    public String toString() {
        return "DataRequestModel{" +
                "fechaSolicitada='" + fechaSolicitada + '\'' +
                ", motivo='" + motivo + '\'' +
                ", id_aprendiz='" + id_aprendiz + '\'' +
                ", id_profesional='" + id_profesional + '\'' +
                '}';
    }
}
