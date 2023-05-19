package com.example.bottomnavigationdemo.model;

public class ImagenData {

    String idImg;
    String urlImg;

    public ImagenData() {
    }

    public ImagenData(String idImg, String urlImg) {
        this.idImg = idImg;
        this.urlImg = urlImg;
    }

    public String getIdImg() {
        return idImg;
    }

    public void setIdImg(String idImg) {
        this.idImg = idImg;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }


}
