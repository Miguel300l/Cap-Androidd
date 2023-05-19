package com.example.bottomnavigationdemo.model;

public class Eventos {

    ImagenData imagen;
    PdfData pdf;
    private String _id;
    private String titulo;
    private String descripcion;
    private String multimedia;
    private String fecha_inicio;
    private String fecha_final;
    private String tipo;
    private String estado;

    public Eventos() {
    }

    public Eventos(ImagenData imagen, PdfData pdf, String _id, String titulo, String descripcion, String multimedia, String fecha_inicio, String fecha_final, String tipo, String estado) {
        this.imagen = imagen;
        this.pdf = pdf;
        this._id = _id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.multimedia = multimedia;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.tipo = tipo;
        this.estado = estado;
    }

    public ImagenData getImagen() {
        return imagen;
    }

    public void setImagen(ImagenData imagen) {
        this.imagen = imagen;
    }

    public PdfData getPdf() {
        return pdf;
    }

    public void setPdf(PdfData pdf) {
        this.pdf = pdf;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
