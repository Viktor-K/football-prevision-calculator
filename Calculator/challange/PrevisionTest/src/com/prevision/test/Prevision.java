package com.prevision.test;

import com.prevision.test.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vcaprio
 */
public class Prevision {
    public static final int VITTORIA = 3;
    public static final int SCONFITTA = 0;
    public static final int PAREGGIO = 1;
    private static String PARMA="PARMA";
    private static String NAPOLI="NAPOLI";
    private static String LAZIO="LAZIO";
    private static String JUVE="JUVE";
    private static String ROMA="ROMA";
    private static String SAMPDORIA="SAMPORIA";
    private static String MILAN="MILAN";
    private static String UDINESE="UDINESE";
    private static String PALERMO="PALERMO";
    private static String INTER="INTER";
    private static String CESENA="CESENA";

    private static List<Giornata> giornateRimanenti = new ArrayList<>();
    private static HashMap<String,ClassificaEntry> classifica = new HashMap<>();
    private static Possibilities possibilities3 = new Possibilities();
    private static Possibilities possibilities2 = new Possibilities();


    private static void stampa(String string){
        System.out.println(string);
    }

    public static void printPrevisions(List<Previsione> previsiones){
        for(Previsione previsione : previsiones){
            stampa("---------------------- Previsione numero ["+previsione.getNumero()+"]");
            stampa("-- Giornate calcolate");
            List<Giornata> giornate = previsione.getGiornate();
            for(Giornata giornata : giornate){
                stampa("\t\t\tGiornata" + giornata.getNumeroGiornate());
                for(Partita partita: giornata.getPartite()){
                    stampa("\t\t\t" + partita.getSquadraCasa() +" - "+ partita.getSquadraTrasferta() + " : " +partita.getEsito());
                }
            }
        }
    }

    public static void main(String[] args) {
        init();
        List<Previsione> previsioneList = calculate(35, giornateRimanenti, new ArrayList<Giornata>(), classifica, new ArrayList<Previsione>());
        printPrevisions(previsioneList);
    }

    private static void init(){

        possibilities3.addPossibility(new String[]{"1","1","1"});
        possibilities3.addPossibility(new String[]{"x","1","1"});
        possibilities3.addPossibility(new String[]{"2","1","1"});
        possibilities3.addPossibility(new String[]{"1","1","x"});
        possibilities3.addPossibility(new String[]{"x","1","x"});
        possibilities3.addPossibility(new String[]{"2","1","x"});
        possibilities3.addPossibility(new String[]{"1","1","2"});
        possibilities3.addPossibility(new String[]{"2","1","2"});
        possibilities3.addPossibility(new String[]{"x","1","2"});
        possibilities3.addPossibility(new String[]{"1","x","1"});
        possibilities3.addPossibility(new String[]{"x","x","1"});
        possibilities3.addPossibility(new String[]{"2","x","1"});
        possibilities3.addPossibility(new String[]{"1","x","x"});
        possibilities3.addPossibility(new String[]{"x","x","x"});
        possibilities3.addPossibility(new String[]{"2","x","x"});
        possibilities3.addPossibility(new String[]{"1","x","2"});
        possibilities3.addPossibility(new String[]{"x","x","2"});
        possibilities3.addPossibility(new String[]{"2","x","2"});
        possibilities3.addPossibility(new String[]{"1","2","1"});
        possibilities3.addPossibility(new String[]{"x","2","1"});
        possibilities3.addPossibility(new String[]{"2","2","1"});
        possibilities3.addPossibility(new String[]{"1","2","x"});
        possibilities3.addPossibility(new String[]{"x","2","x"});
        possibilities3.addPossibility(new String[]{"2","2","x"});
        possibilities3.addPossibility(new String[]{"1","2","2"});
        possibilities3.addPossibility(new String[]{"x","2","2"});
        possibilities3.addPossibility(new String[]{"2","2","2"});


        possibilities2.addPossibility(new String[]{"1","1"});
        possibilities2.addPossibility(new String[]{"x","1"});
        possibilities2.addPossibility(new String[]{"2","1"});
        possibilities2.addPossibility(new String[]{"1","2"});
        possibilities2.addPossibility(new String[]{"x","x"});
        possibilities2.addPossibility(new String[]{"2","x"});
        possibilities2.addPossibility(new String[]{"1","x"});
        possibilities2.addPossibility(new String[]{"x","2"});
        possibilities2.addPossibility(new String[]{"2","2"});


        Giornata giornata = new Giornata(35);
        giornata.setPartite(new ArrayList<Partita>(){{
            add(new Partita(PARMA, NAPOLI));
            add(new Partita(LAZIO, INTER));
            add(new Partita(MILAN, ROMA));
        }});

        Giornata giornata2 = new Giornata(36);
        giornata2.setPartite(new ArrayList<Partita>(){{
            add(new Partita(NAPOLI, CESENA));
            add(new Partita(SAMPDORIA, LAZIO));
            add(new Partita(ROMA, UDINESE));
        }});

        Giornata giornata3 = new Giornata(37);
        giornata3.setPartite(new ArrayList<Partita>(){{
            add(new Partita(JUVE, NAPOLI));
            add(new Partita(LAZIO, ROMA));
        }});

        Giornata giornata4 = new Giornata(38);
        giornata4.setPartite(new ArrayList<Partita>(){{
            add(new Partita(NAPOLI, LAZIO));
            add(new Partita(ROMA, PALERMO));
        }});

        giornateRimanenti.add(giornata);
        giornateRimanenti.add(giornata2);
        giornateRimanenti.add(giornata3);
        giornateRimanenti.add( giornata4 );

        classifica.put(JUVE, new ClassificaEntry(JUVE, 79));
        classifica.put(ROMA, new ClassificaEntry(ROMA, 64));
        classifica.put(LAZIO, new ClassificaEntry(LAZIO, 63));
        classifica.put(NAPOLI, new ClassificaEntry(NAPOLI, 59));

    }


    private void makeRisultati(int day, Map<Integer,Giornata> risultatiConseguiti , List<Partita> risultati){
        Giornata giornata = risultatiConseguiti.get(day);
        for(Partita partita : risultati){
            giornata.addPartita(partita);
        }
    }

    private static List<RisultatoPartita> calculatePoint(final Partita partita){

        switch( partita.getEsito() ){
            case "1" :

                return new ArrayList<RisultatoPartita>(){{
                    add(new RisultatoPartita(partita.getSquadraCasa(), VITTORIA, partita));
                    add(new RisultatoPartita(partita.getSquadraTrasferta(), SCONFITTA, partita));
                }};

            case "2" :
                return new ArrayList<RisultatoPartita>(){{
                    add(new RisultatoPartita(partita.getSquadraCasa(), SCONFITTA, partita));
                    add(new RisultatoPartita(partita.getSquadraTrasferta(), VITTORIA, partita));
                }};


            default :
                return new ArrayList<RisultatoPartita>(){{
                    add(new RisultatoPartita(partita.getSquadraCasa(), PAREGGIO, partita));
                    add(new RisultatoPartita(partita.getSquadraTrasferta(), PAREGGIO, partita));
                }};
        }
    }

    private static void updateClassifica(Map<String, ClassificaEntry> classifica, List<RisultatoPartita> risultatoPartite){

        for(RisultatoPartita risultatoPartita : risultatoPartite){
            classifica.get(risultatoPartita.getSquadra()).addPunteggio(risultatoPartita.getPunti());
        }
    }

    private static Previsione makePrevisions(int previsionNum, List<Giornata> risultatiConseguiti, HashMap<String, ClassificaEntry> classicaTemporanea){
        Previsione previsione = new Previsione();
        previsione.setNumero(previsionNum);
        previsione.setGiornate(risultatiConseguiti);
        previsione.setClassifica(classicaTemporanea);

        System.out.print("I make a prevision");

        return previsione;
    }

    private static boolean goToChampions(HashMap<String, ClassificaEntry> classifica){

        int romaPoints   = classifica.get(ROMA).getPunteggio();
        int lazioPoints  = classifica.get(LAZIO).getPunteggio();
        int napoliPoints = classifica.get(NAPOLI).getPunteggio();

        if(napoliPoints < romaPoints && (romaPoints < lazioPoints || romaPoints == lazioPoints)){
            return false;
        }

        if(napoliPoints < lazioPoints && (lazioPoints < romaPoints || lazioPoints == romaPoints)){
            return false;
        }

        return true;
    }


    private static Giornata getGiornata(List<Giornata> giornate, int giorno){
        for(Giornata giornata : giornate){
            if(giornata.getNumeroGiornate() == giorno){
                return giornata;
            }
        }
        return null;
    }
    private static HashMap<String,ClassificaEntry> cloneClassifica(HashMap<String, ClassificaEntry> subject){
        HashMap<String,ClassificaEntry> newClassifica = new HashMap<>();
        for(String key : subject.keySet()){
            ClassificaEntry classificaEntry = subject.get(key);
            newClassifica.put(key, new ClassificaEntry(classificaEntry.getSquadra(), classificaEntry.getPunteggio()));
        }
        return newClassifica;
    }

    private static List<RisultatoPartita> partitaDecisa(Partita partita, String pronostico){
        Partita esito = new Partita(partita.getSquadraCasa(), partita.getSquadraTrasferta());
        esito.setEsito(pronostico);

        return calculatePoint(esito);
    }

    private static List<Previsione> calculate(int day, List<Giornata> giornate, List<Giornata> risultatiConseguiti, HashMap<String, ClassificaEntry> classifica, List<Previsione> previsions){

        if(day > 38){
            if( goToChampions(classifica) ){
                previsions.add(makePrevisions(previsions.size()+1, risultatiConseguiti, classifica));
                return previsions;
            }
            return null;
        }

        Giornata giornata = getGiornata(giornate, day);
        HashMap<String,ClassificaEntry> classificaParziale = cloneClassifica(classifica);

        Possibilities possibilityTarget;
        if(giornata.getPartite().size() == 3) {
            possibilityTarget = possibilities3;
        }else{
            possibilityTarget = possibilities2;
        }

        for(final String[] possibility : possibilityTarget.getPossibilityList()){


            List<RisultatoPartita> results = new ArrayList<>();
            Giornata pronosticoGiornata = new Giornata(day);
            ArrayList<Partita> partitas = new ArrayList<>();

            for(int index=0; index<possibility.length; index++){
                Partita partita = giornata.getPartite().get(index);
                results.addAll(partitaDecisa(partita, possibility[index]));
                partitas.add(new Partita(partita.getSquadraCasa(), partita.getSquadraTrasferta()).setEsito(possibility[index]));
            }


            updateClassifica(classificaParziale, results);
            pronosticoGiornata.setPartite(partitas);

            risultatiConseguiti.add(pronosticoGiornata);

            calculate(day+1, giornate, risultatiConseguiti  ,classificaParziale, previsions);

            classificaParziale = cloneClassifica(classifica);
        }

        return previsions;
    }

}


