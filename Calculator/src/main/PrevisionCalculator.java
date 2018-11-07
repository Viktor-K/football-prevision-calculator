package main;

import model.*;
import model.criteria.Criteria;
import model.criteria.ScontriDirettiCriteria;
import model.criteria.Teams;
import model.criteria.impl.LazioChampionsCriteria;
import model.criteria.impl.NapoliChampionsCriteria;
import utils.CalcolatorePunti;
import utils.ClassificaSorter;
import utils.PossibilityCalculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vcaprio
 */
public class PrevisionCalculator {

    private static String subject = Teams.NAPOLI;
    private static Criteria championsCriteria = new NapoliChampionsCriteria();
    private static List<ScontriDirettiCriteria> scontriDirettiCriteria = setScontriDirettiChampions();

    private static List<Giornata> giornateRimanenti = new ArrayList<>();
    private static HashMap<String, ClassificaEntry> classifica = new HashMap<>();

    private static CalcolatorePunti calcolatorePunti = new CalcolatorePunti();
    private static ClassificaSorter classificaSorter = new ClassificaSorter();


    public static void main(String[] args) {

        init();
        Tentativi tentativi = new Tentativi(0);

        List<Previsione> previsioneList = calculate(
                subject,
                5,
                giornateRimanenti,
                new ArrayList<Giornata>(),
                classifica,
                new ArrayList<Previsione>(),
                tentativi,
                championsCriteria,
                scontriDirettiCriteria,
                6,
                7
        );

        try {
            printPrevisions(subject, previsioneList, tentativi);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<ScontriDirettiCriteria> setScontriDirettiNapoli() {
        final ScontriDirettiCriteria confrontiNapoliRoma = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.ROMA);
        confrontiNapoliRoma.addPartita(new Partita(Teams.NAPOLI, Teams.ROMA).setEsito("1"));
        confrontiNapoliRoma.addPartita(new Partita(Teams.ROMA, Teams.NAPOLI).setEsito("2"));

        final ScontriDirettiCriteria confrontiNapoliLazio = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.LAZIO);
        confrontiNapoliLazio.addPartita(new Partita(Teams.NAPOLI, Teams.LAZIO).setEsito("1"));

        return new ArrayList<ScontriDirettiCriteria>() {{
            add(confrontiNapoliRoma);
            add(confrontiNapoliLazio);
        }};
    }

    private static List<ScontriDirettiCriteria> setScontriDirettiRoma() {
        final ScontriDirettiCriteria confrontiNapoliRoma = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.ROMA);
        confrontiNapoliRoma.addPartita(new Partita(Teams.NAPOLI, Teams.ROMA).setEsito("1"));
        confrontiNapoliRoma.addPartita(new Partita(Teams.ROMA, Teams.NAPOLI).setEsito("2")); // GOL Favore Napoli

        final ScontriDirettiCriteria confrontiRomaLazio = new ScontriDirettiCriteria(Teams.ROMA, Teams.LAZIO);
        confrontiRomaLazio.addPartita(new Partita(Teams.ROMA, Teams.LAZIO).setEsito("1"));

        return new ArrayList<ScontriDirettiCriteria>() {{
            add(confrontiNapoliRoma);
            add(confrontiRomaLazio);
        }};
    }

    private static List<ScontriDirettiCriteria> setScontriDirettiLazio() {
        final ScontriDirettiCriteria confrontiNapoliRoma = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.ROMA);
        confrontiNapoliRoma.addPartita(new Partita(Teams.NAPOLI, Teams.ROMA).setEsito("1"));
        confrontiNapoliRoma.addPartita(new Partita(Teams.ROMA, Teams.NAPOLI).setEsito("2")); // GOL Favore Napoli

        final ScontriDirettiCriteria confrontiNapoliLazio = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.LAZIO);
        confrontiNapoliLazio.addPartita(new Partita(Teams.NAPOLI, Teams.LAZIO).setEsito("1"));

        return new ArrayList<ScontriDirettiCriteria>() {{
            add(confrontiNapoliRoma);
            add(confrontiNapoliLazio);
        }};
    }

    private static List<ScontriDirettiCriteria> setScontriDirettiChampions() {
        final ScontriDirettiCriteria confrontiNapoliLiverpool = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.LIVERPOOL);
        confrontiNapoliLiverpool.addPartita(new Partita(Teams.NAPOLI, Teams.LIVERPOOL).setEsito("1"));

        final ScontriDirettiCriteria confrontiNapoliPsg = new ScontriDirettiCriteria(Teams.NAPOLI, Teams.PSG);
        confrontiNapoliPsg.addPartita(new Partita(Teams.NAPOLI, Teams.PSG).setEsito("X"));
        confrontiNapoliPsg.addPartita(new Partita(Teams.PSG, Teams.NAPOLI).setEsito("X"));

        return new ArrayList<ScontriDirettiCriteria>() {{
            add(confrontiNapoliLiverpool);
            add(confrontiNapoliPsg);
        }};
    }


    public static void printPrevisions(String team, List<Previsione> previsiones, Tentativi tentativi) throws IOException {
        String mainStream = new String();
        FileWriter fileWriter = new FileWriter(new File("/Users/berserk/Documents/Projects/football-prevision-calculator/Calculator/output/results.txt"));

        float P = (float) previsiones.size() / tentativi.getCount();
        String incipit = "\t\t\t\t\t SQUADRA " + team
                + "\nTotale Combinazioni " + tentativi.getCount()
                + "\nTatale Possibilità " + previsiones.size()
                + "\nProbabilita' di classificazione Champions :" + Math.round(P * 100) + "%";

        fileWriter.append(incipit);

        String delimiter = "____________________________________________________________________________________________";

        for (Previsione previsione : previsiones) {
            String previsionTitle = "       Previsione numero [" + previsione.getNumero() + "]";
            String fullEmptySting = new String(new char[delimiter.length()]).replace('\0', ' ');

            int finalLenght = (delimiter.length() - previsionTitle.length());

            String smallEmptyString = new String(new char[finalLenght]).replace('\0', ' ');

            String previsionNum =
                    "\n " + delimiter +
                            "\n|" + fullEmptySting + "|" +
                            "\n|" + previsionTitle + smallEmptyString + "|" +
                            "\n|" + delimiter + "|\n\n";

            mainStream += previsionNum;

            List<Giornata> giornate = previsione.getGiornate();
            for (Giornata giornata : giornate) {
                String giornateTitle = "\n\t\tGiornata" + giornata.getNumeroGiornate();
                mainStream += giornateTitle;

                for (Partita partita : giornata.getPartite()) {
                    String partite = "\n\t\t\t" + partita.getSquadraCasa() + " - " + partita.getSquadraTrasferta() + " : " + partita.getEsito();
                    mainStream += partite;
                }
            }

            List<ClassificaEntry> classificaSorted = classificaSorter.getClassificaOrdinata(previsione.getClassifica());

            String prima = classificaSorted.get(0).getSquadra();
            int primaPunti = classificaSorted.get(0).getPunteggio();

            String seconda = classificaSorted.get(1).getSquadra();
            int secondaPunti = classificaSorted.get(1).getPunteggio();


            String terzaTmp = classificaSorted.get(2).getSquadra();
            String currTerza = terzaTmp;
            int terzaTmpPunti = classificaSorted.get(2).getPunteggio();
            int currTerzaPunti = terzaTmpPunti;

            String quartaTmp = classificaSorted.get(3).getSquadra();
            String currQuarta = quartaTmp;
            int quartaTmpPunti = classificaSorted.get(3).getPunteggio();
            int currQuartaPunti = quartaTmpPunti;

            String nota = "";
            if (quartaTmpPunti == terzaTmpPunti) {
                if (classificaSorted.get(3).getSquadreVinceScontriDiretti().contains(terzaTmp)) {
                    nota = "(" + classificaSorted.get(3).getSquadra() + "Vince scontri diretti con " + terzaTmp + ")";
                    currTerza = quartaTmp;
                    currTerzaPunti = quartaTmpPunti;
                    currQuarta = terzaTmp;
                    currQuartaPunti = terzaTmpPunti;
                } else {
                    if (classificaSorted.get(2).getSquadreVinceScontriDiretti().contains(quartaTmp)) {
                        nota = "(" + classificaSorted.get(3).getSquadra() + "Vince scontri diretti con " + quartaTmp + ")";
                    }
                }
            }

            String classificaString = String.format("\nClassifica Finale : \n%s[%d] WIN - %s[%d] * - %s[%d] * %s - %s[%d]\n",
                    prima, primaPunti,
                    seconda, secondaPunti,
                    currTerza, currTerzaPunti,
                    nota,
                    currQuarta, currQuartaPunti
            );

            mainStream += classificaString;
            fileWriter.append(mainStream);

            mainStream = "";
        }
        fileWriter.close();
        stampa("\n\n\n !! Operazione Terminata !!");
    }

    private static void init() {
        Giornata giornata5 = new Giornata(5);
        giornata5.setPartite(new ArrayList<Partita>() {{
            add(new Partita(Teams.NAPOLI, Teams.STELLA_ROSSA));
            add(new Partita(Teams.PSG, Teams.LIVERPOOL));
        }});

        Giornata giornata6 = new Giornata(6);
        giornata6.setPartite(new ArrayList<Partita>() {{
            add(new Partita(Teams.LIVERPOOL, Teams.NAPOLI));
            add(new Partita(Teams.STELLA_ROSSA, Teams.PSG));
        }});

        giornateRimanenti.add(giornata5);
        giornateRimanenti.add(giornata6);

        classifica.put(Teams.NAPOLI, new ClassificaEntry(Teams.NAPOLI, 6));
        classifica.put(Teams.LIVERPOOL, new ClassificaEntry(Teams.LIVERPOOL, 6));
        classifica.put(Teams.PSG, new ClassificaEntry(Teams.PSG, 5));
        classifica.put(Teams.STELLA_ROSSA, new ClassificaEntry(Teams.STELLA_ROSSA, 4));
    }


    private static void updateClassifica(Map<String, ClassificaEntry> classifica, List<RisultatoPartita> risultatoPartite) {
        for (RisultatoPartita risultatoPartita : risultatoPartite) {
            if (classifica.get(risultatoPartita.getSquadra()) == null) {
                classifica.put(risultatoPartita.getSquadra(), new ClassificaEntry(risultatoPartita.getSquadra(), 0));
            }
            classifica.get(risultatoPartita.getSquadra()).addPunteggio(risultatoPartita.getPunti());
        }
    }

    private static Previsione makePrevisions(int previsionNum, List<Giornata> risultatiConseguiti, HashMap<String, ClassificaEntry> classicaTemporanea) {
        Previsione previsione = new Previsione();
        previsione.setNumero(previsionNum);
        previsione.setGiornate(risultatiConseguiti);
        previsione.setClassifica(classicaTemporanea);
        return previsione;
    }

    private static Giornata getGiornata(List<Giornata> giornate, int giorno) {
        //Add Collections2 google lib
        for (Giornata giornata : giornate) {
            if (giornata.getNumeroGiornate() == giorno) {
                return giornata;
            }
        }
        return null;
    }

    private static HashMap<String, ClassificaEntry> cloneClassifica(HashMap<String, ClassificaEntry> subject) {

        HashMap<String, ClassificaEntry> newClassifica = new HashMap<>();
        for (String key : subject.keySet()) {
            ClassificaEntry classificaEntry = subject.get(key);
            newClassifica.put(key, new ClassificaEntry(classificaEntry.getSquadra(), classificaEntry.getPunteggio()));
        }
        return newClassifica;
    }

    private static ScontriDirettiCriteria retriveScontroDiretto(List<ScontriDirettiCriteria> scontriDirettiCriteria, String squadra1, String squadra2) {
        for (ScontriDirettiCriteria scontroDir : scontriDirettiCriteria) {
            if (scontroDir.isSControTra(squadra1, squadra2))
                return scontroDir;
        }

        ScontriDirettiCriteria newScontroDiretto = new ScontriDirettiCriteria(squadra1, squadra2);
        scontriDirettiCriteria.add(newScontroDiretto);

        return newScontroDiretto;
    }

    private static List<RisultatoPartita> simulaPartita(Partita partita, String pronostico) {
        Partita esito = new Partita(partita.getSquadraCasa(), partita.getSquadraTrasferta());
        esito.setEsito(pronostico);

        return calcolatorePunti.calculatePoint(esito);
    }


    private static List<Previsione> calculate(String team,
                                              int day,
                                              List<Giornata> giornate,
                                              List<Giornata> risultatiConseguiti,
                                              HashMap<String, ClassificaEntry> classifica,
                                              List<Previsione> previsions,
                                              Tentativi tentativi,
                                              Criteria championsCriteria,
                                              List<ScontriDirettiCriteria> scontriDiretti,
                                              int penultimaGiornata,
                                              int ultimaGiornata) {

        if (day > penultimaGiornata) {
            tentativi.increment();
            InfoClassificazione infoClassificazione = championsCriteria.isValid(classifica, scontriDiretti);
            if (infoClassificazione.isChampions()) {
                classifica.get(team).getSquadreVinceScontriDiretti()
                        .addAll(infoClassificazione.getConfrontiVinti());

                previsions.add(makePrevisions(previsions.size() + 1, risultatiConseguiti, classifica));
                return previsions;
            }
            return null;
        }

        Giornata giornata = getGiornata(giornate, day);
        HashMap<String, ClassificaEntry> classificaParziale = cloneClassifica(classifica);

        Possibilities possibilityTarget = getPossibilitaPerPartite(giornata);

        for (final String[] possibility : possibilityTarget.getPossibilityList()) {

            List<RisultatoPartita> results = new ArrayList<>();
            Giornata pronosticoGiornata = new Giornata(day);
            ArrayList<Partita> partitas = new ArrayList<>();

            List<ScontriDirettiCriteria> scontriDirettiParziali = cloneScontriDirettiList(scontriDiretti);

            int numeroPartiteNellaGiornata = possibility.length;
            for (int index = 0; index < numeroPartiteNellaGiornata; index++) {
                Partita partita = giornata.getPartite().get(index);
                results.addAll(simulaPartita(partita, possibility[index]));
                Partita partitaCorrente = new Partita(partita.getSquadraCasa(), partita.getSquadraTrasferta()).setEsito(possibility[index]);
                partitas.add(partitaCorrente);

                ScontriDirettiCriteria scontroDiretto = retriveScontroDiretto(scontriDirettiParziali, partita.getSquadraCasa(), partita.getSquadraTrasferta());
                scontroDiretto.addPartita(partitaCorrente);
            }

            updateClassifica(classificaParziale, results);
            pronosticoGiornata.setPartite(partitas);

            List<Giornata> risultatiCalcolatiCurrent = new ArrayList(risultatiConseguiti);
            risultatiCalcolatiCurrent.add(pronosticoGiornata);

            if (day < ultimaGiornata) {
                calculate(team, day + 1, giornate, risultatiCalcolatiCurrent, classificaParziale, previsions, tentativi, championsCriteria, scontriDirettiParziali, penultimaGiornata, ultimaGiornata);
            } else {
                calculate(team, day, giornate, risultatiCalcolatiCurrent, classificaParziale, previsions, tentativi, championsCriteria, scontriDirettiParziali, penultimaGiornata, ultimaGiornata);
            }

            classificaParziale = cloneClassifica(classifica);
        }

        return previsions;
    }

    private static Possibilities getPossibilitaPerPartite(Giornata giornata) {
        PossibilityCalculator possibilityCalculator = new PossibilityCalculator();
        return possibilityCalculator.getPossibility(giornata.getPartite().size(), giornata.getNumeroGiornate());
    }

    private static ArrayList<ScontriDirettiCriteria> cloneScontriDirettiList(List<ScontriDirettiCriteria> scontriDiretti) {
        ArrayList<ScontriDirettiCriteria> result = new ArrayList<>();

        for (ScontriDirettiCriteria criteria : scontriDiretti) {
            ScontriDirettiCriteria newScontroDiretto = new ScontriDirettiCriteria(criteria.getSquadre());

            for (Partita partita : criteria.getStoricoPartite()) {
                Partita newPartita = new Partita(partita.getSquadraCasa(), partita.getSquadraTrasferta());
                partita.getEsito();

                newScontroDiretto.getStoricoPartite().add(newPartita);
            }

            HashMap<String, ClassificaEntry> newClasificaAvulsa = new HashMap<>();
            for (String key : criteria.getClassificaAvulsa().keySet()) {
                ClassificaEntry classificaEntry = criteria.getClassificaAvulsa().get(key);
                ClassificaEntry nuovaClassificaEntry = new ClassificaEntry(classificaEntry.getSquadra(), classificaEntry.getPunteggio());

                for (String vinceContro : classificaEntry.getSquadreVinceScontriDiretti()) {
                    nuovaClassificaEntry.addSquadraVinceSContriDiretti(vinceContro);
                }

                newClasificaAvulsa.put(key, nuovaClassificaEntry);
            }
            newScontroDiretto.setClassificaAvulsa(newClasificaAvulsa);
            result.add(newScontroDiretto);
        }

        return result;
    }

    private static void stampa(String string) {
        System.out.println(string);
    }
}


