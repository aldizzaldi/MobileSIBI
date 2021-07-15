package eu.darken.myolib.exampleapp;

public class BelajarItem {

    private int id;
    private String item;
    private int rawFile;
    private int tanda;

    public BelajarItem(int id, String item, int rawFile, int tanda){
        this.id = id;
        this.item = item;
        this.rawFile = rawFile;
        this.tanda = tanda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getRawFile() {
        return rawFile;
    }

    public void setRawFile(int rawFile) {
        this.rawFile = rawFile;
    }

    public int getTanda() {
        return tanda;
    }

    public void setTanda(int tanda) {
        this.tanda = tanda;
    }
}
