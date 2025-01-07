package src.domain;

import javax.swing.*;

public class Item {
    private String nombreItem;
    private int precioItem;
    private String tipoItem; // "foto", "moneda" o "label"
    private String contenido; // Ruta de foto o texto del label

    public Item(String nombreItem, int precioItem, String tipoItem, String contenido) {
        this.nombreItem = nombreItem;
        this.precioItem = precioItem;
        this.tipoItem = tipoItem;
        this.contenido = contenido;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public int getPrecioItem() {
        return precioItem;
    }

    public void setPrecioItem(int precioItem) {
        this.precioItem = precioItem;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Object getRepresentacionVisual() {
        if (tipoItem.equals("foto")) {
            return new ImageIcon(contenido); // Representación como ImageIcon
        } else if (tipoItem.equals("moneda")) {
            return new ImageIcon(contenido); // Representación como ImageIcon
    } else if (tipoItem.equals("label")) {
            return new JLabel(contenido); // Representación como JLabel
        }
        return null;
    }
}
