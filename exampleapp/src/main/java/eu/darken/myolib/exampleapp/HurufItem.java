package eu.darken.myolib.exampleapp;

public class HurufItem {

    private int id;
    private String huruf;
    private int rawFile;

    public HurufItem(int id, String huruf, int rawFile){
        this.id = id;
        this.huruf = huruf;
        this.rawFile = rawFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHuruf() {
        return huruf;
    }

    public void setHuruf(String huruf) {
        this.huruf = huruf;
    }

    public int getRawFile() {
        return rawFile;
    }

    public void setRawFile(int rawFile) {
        this.rawFile = rawFile;
    }
}
