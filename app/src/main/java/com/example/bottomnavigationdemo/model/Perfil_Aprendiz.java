package com.example.bottomnavigationdemo.model;

public class Perfil_Aprendiz {
    private String userId;
    private String _id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String numTelefono;

    private String urlImg;

    public String getUrlImg() {
        return urlImg;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUserId() {
        return userId;
    }

    public String get_id() {
        return _id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "\n userId=" + userId +
                ",\n id=" + _id +
                ",\n nombres='" + nombres + '\'' +
                ",\n apellidos='" + apellidos + '\'' +
                ",\n correo='" + correo + '\'' +
                ",\n numTelefono='" + numTelefono + '\'' +
                ",\n urlImg='" + urlImg + '\'' +
                '}';
    }
}
