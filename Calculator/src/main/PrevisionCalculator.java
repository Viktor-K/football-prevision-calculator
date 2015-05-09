package main;

import model.*;
import model.criteria.Criteria;
import model.criteria.Teams;
import model.criteria.impl.NapoliChampionsCriteria;
import model.criteria.impl.RomaChampionsCriteria;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author vcaprio
 */
public class PrevisionCalculator {
    private static String subject;

    private static final int VITTORIA = 3;
    private static final int SCONFITTA = 0;
    private static final int PAREGGIO = 1;

    private static List<Giornata> giornateRimanenti = new ArrayList<>();
    private static HashMap<String,ClassificaEntry> classifica = new HashMap<>();
    private static Possibilities possibilities3 = new Possibilities();
    private static Possibilities possibilities2 = new Possibilities();

    private static Criteria championsCriteria;

    private static void stampa(String string){
        System.out.println(string);
    }

    public static void printPrevisions(List<Previsione> previsiones, Tentativi tentativi) throws IOException {
        String mainStream = new String();
        FileWriter fileWriter = new FileWriter(new File("/Users/Berserk/Documents/Project/Results/results.txt"));

        float P = (float) previsiones.size() / tentativi.getCount();
        String incipit = "Totale Combinazioni " + tentativi.getCount()
                + "\nTatale Possibilità " + previsiones.size()
                +"\nProbabilita' di classificazione Champions :" + Math.round( P * 100) +"%";

        printIntoFile(fileWriter, incipit);

        for(Previsione previsione : previsiones){
            String previsionNum = "\n______________________________\n\n  Previsione numero [" + previsione.getNumero() + "]  \n______________________________";
            mainStream += previsionNum;

            List<Giornata> giornate = previsione.getGiornate();
            for(Giornata giornata : giornate){
                String giornateTitle = "\n\t\tGiornata" + giornata.getNumeroGiornate();
                mainStream += giornateTitle;

                for(Partita partita: giornata.getPartite()){
                    String partite = "\n\t\t\t" + partita.getSquadraCasa() + " - " + partita.getSquadraTrasferta() + " : " + partita.getEsito();
                    mainStream += partite;
                }
            }
            final HashMap<String, ClassificaEntry> classifica = previsione.getClassifica();

            List<ClassificaEntry> classificaSorted = new ArrayList<ClassificaEntry>(){{
                add(classifica.get(Teams.NAPOLI));
                add(classifica.get(Teams.ROMA));
                add(classifica.get(Teams.LAZIO));
            }};


            Collections.sort(classificaSorted, new Comparator<ClassificaEntry>() {
                @Override
                public int compare(ClassificaEntry entry1, ClassificaEntry entry2) {
                    if(entry1.getPunteggio() > entry2.getPunteggio()){
                        return 1;
                    }

                    if(entry1.getPunteggio() < entry2.getPunteggio()){
                        return -1;
                    }
                    return 0;
                }
            });

            String classificaString = String.format("\nClassifica Finale : \n%s[%d] - %s[%d] - %s[%d]\n",
                    classificaSorted.get(2).getSquadra(), classificaSorted.get(2).getPunteggio(),
                    classificaSorted.get(1).getSquadra(), classificaSorted.get(1).getPunteggio(),
                    classificaSorted.get(0).getSquadra(), classificaSorted.get(0).getPunteggio()
            );

            mainStream += classificaString;
            printIntoFile(fileWriter, mainStream);

            mainStream = "";
        }
        fileWriter.close();
        stampa("Operazione Terminata");
    }

    public static void printIntoFile(FileWriter fileWriter, String string) throws IOException {
        fileWriter.append(string);
    }

    public static void main(String[] args) {

        init();
        Tentativi tentativi = new Tentativi(0);
        List<Previsione> previsioneList = calculate(subject, 35, giornateRimanenti, new ArrayList<Giornata>(), classifica, new ArrayList<Previsione>(), tentativi, championsCriteria);

        try {
            printPrevisions(previsioneList, tentativi);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init(){
        subject = Teams.NAPOLI;
        championsCriteria = new NapoliChampionsCriteria();


        // 27 possibilita' 3 partite
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


        // 9 possibilita' 2 partite
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
            add(new Partita(Teams.MILAN, Teams.ROMA));
            add(new Partita(Teams.PARMA, Teams.NAPOLI));
            add(new Partita(Teams.LAZIO, Teams.INTER));
        }});

        Giornata giornata2 = new Giornata(36);
        giornata2.setPartite(new ArrayList<Partita>(){{
            add(new Partita(Teams.NAPOLI, Teams.CESENA));
            add(new Partita(Teams.SAMPDORIA, Teams.LAZIO));
            add(new Partita(Teams.ROMA, Teams.UDINESE));
        }});

        Giornata giornata3 = new Giornata(37);
        giornata3.setPartite(new ArrayList<Partita>(){{
            add(new Partita(Teams.JUVE, Teams.NAPOLI));
            add(new Partita(Teams.LAZIO, Teams.ROMA));
        }});

        Giornata giornata4 = new Giornata(38);
        giornata4.setPartite(new ArrayList<Partita>(){{
            add(new Partita(Teams.NAPOLI, Teams.LAZIO));
            add(new Partita(Teams.ROMA, Teams.PALERMO));
        }});

        giornateRimanenti.add(giornata);
        giornateRimanenti.add(giornata2);
        giornateRimanenti.add(giornata3);
        giornateRimanenti.add( giornata4 );

        classifica.put(Teams.JUVE, new ClassificaEntry(Teams.JUVE, 79));
        classifica.put(Teams.ROMA, new ClassificaEntry(Teams.ROMA, 64));
        classifica.put(Teams.LAZIO, new ClassificaEntry(Teams.LAZIO, 63));
        classifica.put(Teams.NAPOLI, new ClassificaEntry(Teams.NAPOLI, 59));
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
            if(classifica.get(risultatoPartita.getSquadra()) == null ){
                classifica.put(risultatoPartita.getSquadra(),new ClassificaEntry(risultatoPartita.getSquadra(),0));
            }
            classifica.get(risultatoPartita.getSquadra()).addPunteggio(risultatoPartita.getPunti());
        }
    }

    private static Previsione makePrevisions(int previsionNum, List<Giornata> risultatiConseguiti, HashMap<String, ClassificaEntry> classicaTemporanea){
        Previsione previsione = new Previsione();
        previsione.setNumero(previsionNum);
        previsione.setGiornate(risultatiConseguiti);
        previsione.setClassifica(classicaTemporanea);
        return previsione;
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




    private static List<Previsione> calculate(String team, int day, List<Giornata> giornate, List<Giornata> risultatiConseguiti, HashMap<String, ClassificaEntry> classifica, List<Previsione> previsions, Tentativi tentativi, Criteria championsCriteria){

        if(day > 38){
            tentativi.increment();
            if( championsCriteria.isValid(classifica) ){
                previsions.add(makePrevisions( previsions.size() + 1, risultatiConseguiti, classifica));
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

            List<Giornata> risultatiCalcolatiCurrent = new ArrayList(risultatiConseguiti);
            risultatiCalcolatiCurrent.add(pronosticoGiornata);

            if(day < 39){
                calculate(team, day + 1, giornate, risultatiCalcolatiCurrent, classificaParziale, previsions, tentativi, championsCriteria);
            }else{
                calculate(team, day, giornate, risultatiCalcolatiCurrent, classificaParziale, previsions, tentativi, championsCriteria);
            }

            classificaParziale = cloneClassifica(classifica);
        }

        return previsions;
    }

}


