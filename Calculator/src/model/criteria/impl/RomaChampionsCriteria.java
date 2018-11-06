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
public class RomaChampionsCriteria extends Criteria{

    public RomaChampionsCriteria(){
        team = Teams.ROMA;
    }

    public InfoClassificazione isValid(HashMap<String, ClassificaEntry> classifica, List<ScontriDirettiCriteria> scontriDirettiSquadre){
        InfoClassificazione infoClassificazione = new InfoClassificazione();
        infoClassificazione.setSquadra(team);
        infoClassificazione.setChampions(true);

        int romaPoints = classifica.get(Teams.ROMA).getPunteggio();
        int lazioPoints = classifica.get(Teams.LAZIO).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();


        if (romaPoints < napoliPoints && (napoliPoints < lazioPoints || napoliPoints == lazioPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(romaPoints < lazioPoints && (lazioPoints < napoliPoints || lazioPoints == napoliPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(romaPoints == napoliPoints && napoliPoints == lazioPoints){
            //calcola classifica Avulsa
            System.out.print("\nParimerito Napoli Lazio Roma\n");
        }

        boolean vinceControNapoli = true;
        boolean vinceControLazio = true;

        if(romaPoints < lazioPoints && napoliPoints == romaPoints){
            if (processVincitrice(scontriDirettiSquadre, Teams.NAPOLI)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.NAPOLI);
                return infoClassificazione;
            }else{
                vinceControNapoli=false;
                System.out.print("\nRoma perde scontro diretto contro la Napoli");
            }
        }

        if(romaPoints < napoliPoints && romaPoints == lazioPoints){
            if(processVincitrice(scontriDirettiSquadre, Teams.LAZIO)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.LAZIO);
                return infoClassificazione;
            }
            else{
                vinceControLazio = false;
                System.out.print("\nRoma perde scontro diretto contro la Lazio");
            }
        }

        if(!vinceControLazio || !vinceControNapoli){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        return infoClassificazione;
    }
}
