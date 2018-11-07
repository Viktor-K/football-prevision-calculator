package model.criteria.impl;

import model.ClassificaEntry;
import model.InfoClassificazione;
import model.criteria.Criteria;
import model.criteria.ScontriDirettiCriteria;
import model.criteria.Teams;

import java.util.HashMap;
import java.util.List;

/**
 * @author vcaprio
 */
public class NapoliChampionsCriteria extends Criteria{

    public NapoliChampionsCriteria(){
        team = Teams.NAPOLI;
    }

    @Override
    public InfoClassificazione isValid(HashMap<String, ClassificaEntry> classifica, List<ScontriDirettiCriteria> scontriDirettiSquadre) {
        InfoClassificazione infoClassificazione = new InfoClassificazione();
        infoClassificazione.setSquadra(team);
        infoClassificazione.setChampions(true);

        int liverPoolPoints = classifica.get(Teams.LIVERPOOL).getPunteggio();
        int psgPoints = classifica.get(Teams.PSG).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();


        if (napoliPoints < psgPoints && (psgPoints < liverPoolPoints || psgPoints == liverPoolPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(napoliPoints < liverPoolPoints && (liverPoolPoints < psgPoints || liverPoolPoints == psgPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(liverPoolPoints == napoliPoints && napoliPoints == psgPoints){
            //calcola classifica Avulsa
            System.out.print("\nParimerito Napoli Lazio Roma\n");
        }

        boolean vinceControLazio = true;
        boolean vinceControRoma = true;

        if(napoliPoints < liverPoolPoints && napoliPoints == psgPoints){
            if (processVincitrice(scontriDirettiSquadre, Teams.PSG)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.PSG);
                return infoClassificazione;
            }
            else{
                System.out.print("\nNapoli perde scontro diretto contro la PSG");
                vinceControLazio = false;
            }
        }

        if(napoliPoints < psgPoints && liverPoolPoints == napoliPoints){
            if(processVincitrice(scontriDirettiSquadre, Teams.LIVERPOOL)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.LIVERPOOL);
                return infoClassificazione;
            }
            else{
                System.out.print("\nNapoli perde scontro diretto contro la Liverpool");
                vinceControRoma = false;
            }
        }

        if(!vinceControLazio || !vinceControRoma){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        return infoClassificazione;
    }
}