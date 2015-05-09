package com.prevision.test.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author vcaprio
 */
public class Previsione {

    int numero;
    HashMap<String, ClassificaEntry> classifica = new HashMap<>();
    List<Giornata> giornate = new ArrayList<>();

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public HashMap<String, ClassificaEntry> getClassifica() {
        return classifica;
    }

    public void setClassifica(HashMap<String, ClassificaEntry> classifica) {
        this.classifica = classifica;
    }

    public void putInClassifica(String key, ClassificaEntry value){
        this.putInClassifica(key, value);
    }

    public List<Giornata> getGiornate() {
        return giornate;
    }

    public void setGiornate(List<Giornata> giornate) {
        this.giornate = giornate;
    }

    public void pushGiornate(Giornata giornata){
        this.giornate.add(giornata);
    }
}
