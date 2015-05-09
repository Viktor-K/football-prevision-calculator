package com.prevision.test.model;

/**
 * @author vcaprio
 */
public class RisultatoPartita {
    String squadra;
    int punti;
    Partita partita;

    public RisultatoPartita(String squadra, int punti, Partita partita){
        this.squadra = squadra;
        this.punti = punti;
        this.partita = partita;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public Partita getPartita() {
        return partita;
    }

    public void setPartita(Partita partita) {
        this.partita = partita;
    }
}
