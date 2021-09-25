package com.example.qrcodes;

public class Clases {

    String claseid,seccion, area, tema;
    public Clases(String claseid, String seccion, String area, String tema) {
        this.claseid = claseid;
        this.seccion = seccion;
        this.area = area;
        this.tema = tema;
    }
    public String getClaseid() {
        return claseid;
    }
    public void setClaseid(String claseid) {
        this.claseid = claseid;
    }
    public String getSeccion() {
        return seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getTema() {
        return tema;
    }
    public void setTema(String tema) {
        this.tema = tema;
    }

}
