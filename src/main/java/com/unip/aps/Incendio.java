package com.unip.aps;

public class Incendio {
    private String data;
    private String municipio;
    private String estado;
    private String bioma;
    private double riscoFogo;

    public Incendio(String data, String municipio, String estado, String bioma, double riscoFogo) {
        this.data = data;
        this.municipio = municipio;
        this.estado = estado;
        this.bioma = bioma;
        this.riscoFogo = riscoFogo;
    }

    public String getData() { return data; }
    public String getMunicipio() { return municipio; }
    public String getEstado() { return estado; }
    public String getBioma() { return bioma; }
    public double getRiscoFogo() { return riscoFogo; }

    @Override
    public String toString() {
        return "Incendio{" +
                "data='" + data + '\'' +
                ", municipio='" + municipio + '\'' +
                ", estado='" + estado + '\'' +
                ", bioma='" + bioma + '\'' +
                ", riscoFogo=" + riscoFogo +
                '}';
    }
}