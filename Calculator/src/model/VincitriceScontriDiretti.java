package model;

/**
 * @author vcaprio
 */
public class VincitriceScontriDiretti {

    private boolean esisteVincitrice = false;

    private String vincitrice;

    public boolean isEsisteVincitrice() {
        return esisteVincitrice;
    }

    public void setEsisteVincitrice(boolean esisteVincitrice) {
        this.esisteVincitrice = esisteVincitrice;
    }

    public String getVincitrice() {
        return vincitrice;
    }

    public void setVincitrice(String vincitrice) {
        this.vincitrice = vincitrice;
    }
}
