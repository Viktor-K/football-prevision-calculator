package com.prevision.test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vcaprio
 */
public class Giornata {

    int numeroGiornate;
    List<Partita> partite;

    public Giornata(int num){
        numeroGiornate = num;
        partite = new ArrayList<Partita>();
    }


    public int getNumeroGiornate() {
        return numeroGiornate;
    }

    public void setNumeroGiornate(int numeroGiornate) {
        this.numeroGiornate = numeroGiornate;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    public void setPartite(List<Partita> partite) {
        this.partite = partite;
    }

    public void addPartita(Partita partita){
        this.partite.add(partita);
    }

}
