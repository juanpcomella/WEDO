package VentanaTienda;

public class Item {
    private String nombreItem;
    private int precioItem;
    private String tipoItem;
    private String rutaItem;

    public Item(String nombreItem, int precioItem, String tipoItem, String rutaItem) {
        this.nombreItem = nombreItem;
        this.precioItem = precioItem;
        this.tipoItem = tipoItem;
        this.rutaItem = rutaItem;
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

    public String getRutaItem() {
        return rutaItem;
    }

    public void setRutaItem(String rutaItem) {
        this.rutaItem = rutaItem;
    }
}
