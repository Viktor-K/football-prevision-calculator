package model;

/**
 * @author vcaprio
 */
public class ClassificaEntry {
    String squadra;
    int punteggio;

    public ClassificaEntry(String squadra, int punteggio){
        this.squadra = squadra;
        this.punteggio = punteggio;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public void addPunteggio(int punteggio){
        this.punteggio += punteggio;
    }
}
