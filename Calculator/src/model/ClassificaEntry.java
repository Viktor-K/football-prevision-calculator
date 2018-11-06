package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vcaprio
 */
public class ClassificaEntry {
    String squadra;
    int punteggio;
    List<String> squadreVinceScontriDiretti = new ArrayList<>();

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

    public List<String> getSquadreVinceScontriDiretti() {
        return squadreVinceScontriDiretti;
    }

    public void setSquadreVinceScontriDiretti(List<String> squadreVinceScontriDiretti) {
        this.squadreVinceScontriDiretti = squadreVinceScontriDiretti;
    }

    public void addSquadraVinceSContriDiretti(String squadra){
        this.squadreVinceScontriDiretti.add(squadra);
    }
}
