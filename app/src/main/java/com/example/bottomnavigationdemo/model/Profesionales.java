package com.example.bottomnavigationdemo.model;

public class Profesionales {
    Perfil perfil;
    Documento documento;
    private String _id;
    private String nombres;
    private String apellidos;
    private String genero;
    private String correo;
    private Number numTelefono;
    private String profesion;


    public Profesionales() {
    }

    public Profesionales(Perfil perfil, Documento documento, String _id, String nombres, String apellidos, String genero, String correo, Number numTelefono, String profesion) {
        this.perfil = perfil;
        this.documento = documento;
        this._id = _id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.correo = correo;
        this.numTelefono = numTelefono;
        this.profesion = profesion;

    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setTitulo(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Number getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(Number fecha_final) {
        this.numTelefono = numTelefono;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }


    @Override
    public String toString() {
        return "Eventos{" +
                "perfil=" + perfil +
                ", documento=" + documento +
                ", _id='" + _id + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", genero='" + genero + '\'' +
                ", correo='" + correo + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                ", profesion='" + profesion + '\'' +
                '}';
    }

}
