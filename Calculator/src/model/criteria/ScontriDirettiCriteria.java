package model.criteria;

import model.ClassificaEntry;
import model.Partita;
import model.RisultatoPartita;
import model.VincitriceScontriDiretti;
import utils.CalcolatorePunti;
import utils.ClassificaSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vcaprio
 */
public class ScontriDirettiCriteria extends Object{

    private CalcolatorePunti calcolatorePunti = new CalcolatorePunti();
    private ClassificaSorter classificaSorter = new ClassificaSorter();

    private List<String> squadre = new ArrayList<>();
    private List<Partita> storicoPartite = new ArrayList<>();
    private Map<String,ClassificaEntry> classificaAvulsa = new HashMap<>();


    public ScontriDirettiCriteria(String squadra1, String squadra2){
        this.squadre.add(squadra1);
        classificaAvulsa.put(squadra1, new ClassificaEntry(squadra1, 0));

        this.squadre.add(squadra2);
        classificaAvulsa.put(squadra2, new ClassificaEntry(squadra2, 0));

    }

    public ScontriDirettiCriteria(List<String> squadre){
        this.squadre.addAll(squadre);

        for(String squadra : squadre){
            classificaAvulsa.put(squadra, new ClassificaEntry(squadra, 0));
        }
    }

    public VincitriceScontriDiretti getVincitrice(){
        VincitriceScontriDiretti vincitrice = new VincitriceScontriDiretti();

        for(Partita partita : storicoPartite){
            List<RisultatoPartita> risultatoPartitas = calcolatorePunti.calculatePoint(partita);

            for(RisultatoPartita risultatoPartita : risultatoPartitas){
                ClassificaEntry classificaEntry = classificaAvulsa.get(risultatoPartita.getSquadra());
                classificaEntry.addPunteggio(risultatoPartita.getPunti());
            }
        }

        if(allTeamSamePoints()){
            return vincitrice;
        }

        List<ClassificaEntry> classificaOrdinata = classificaSorter.getClassificaOrdinata(classificaAvulsa);

        if(isSControTra(Teams.ROMA,Teams.NAPOLI)){
            System.out.print(String.format("\n\n Verifica Scontri diretti tra %s[%d](WIN) e %s[%d]", classificaOrdinata.get(0).getSquadra(), classificaOrdinata.get(0).getPunteggio(),
                    classificaOrdinata.get(1).getSquadra(), classificaOrdinata.get(1).getPunteggio()));
        }

        vincitrice.setVincitrice(classificaOrdinata.get(0).getSquadra());
        return vincitrice;
    }


    private boolean allTeamSamePoints() {
        ClassificaEntry previousEntry = null;
        for (ClassificaEntry entry : classificaAvulsa.values()){

            if(previousEntry!=null && previousEntry.getPunteggio() == entry.getPunteggio()){
                return true;
            }

            previousEntry = entry;
        }
        return false;
    }


    public List<Partita> getStoricoPartite() {
        return storicoPartite;
    }

    public List<String> getSquadre() {
        return squadre;
    }

    public Map<String, ClassificaEntry> getClassificaAvulsa() {
        return classificaAvulsa;
    }

    public void addPartita(Partita partita){
        storicoPartite.add(partita);
    }

    public CalcolatorePunti getCalcolatorePunti() {
        return calcolatorePunti;
    }

    public void setCalcolatorePunti(CalcolatorePunti calcolatorePunti) {
        this.calcolatorePunti = calcolatorePunti;
    }

    public ClassificaSorter getClassificaSorter() {
        return classificaSorter;
    }

    public void setClassificaSorter(ClassificaSorter classificaSorter) {
        this.classificaSorter = classificaSorter;
    }

    public void setSquadre(List<String> squadre) {
        this.squadre = squadre;
    }

    public void setStoricoPartite(List<Partita> storicoPartite) {
        this.storicoPartite = storicoPartite;
    }

    public void setClassificaAvulsa(Map<String, ClassificaEntry> classificaAvulsa) {
        this.classificaAvulsa = classificaAvulsa;
    }

    public boolean isSControTra(String squadra1, String squadra2){
        if(squadre.contains(squadra1) && squadre.contains(squadra2)){
            return true;
        }

        return false;
    }


}
