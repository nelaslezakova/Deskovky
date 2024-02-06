public class DeskoveHry {
    private String jmeno;
    private boolean vlastneno;
    private int hodnoceni;

    public DeskoveHry(String jmeno, boolean vlastneno, int hodnoceni){
        this.jmeno = jmeno;
        this.vlastneno = vlastneno;
        this.hodnoceni = hodnoceni;
    }
    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public boolean isVlastneno() {
        return vlastneno;
    }

    public void setVlastneno(boolean vlastneno) {
        this.vlastneno = vlastneno;
    }

    public int getHodnoceni() {
        return hodnoceni;
    }

    public void setHodnoceni(int hodnoceni) {
        this.hodnoceni = hodnoceni;
    }
}
