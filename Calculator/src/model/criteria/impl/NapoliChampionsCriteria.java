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

        int romaPoints = classifica.get(Teams.ROMA).getPunteggio();
        int lazioPoints = classifica.get(Teams.LAZIO).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();


        if (napoliPoints < lazioPoints && (lazioPoints < romaPoints || lazioPoints == romaPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(napoliPoints < romaPoints && (romaPoints < lazioPoints || romaPoints == lazioPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(romaPoints == napoliPoints && napoliPoints == lazioPoints){
            //calcola classifica Avulsa
            System.out.print("\nParimerito Napoli Lazio Roma\n");
        }

        boolean vinceControLazio = true;
        boolean vinceControRoma = true;

        if(napoliPoints < romaPoints && napoliPoints == lazioPoints){
            if (processVincitrice(scontriDirettiSquadre, Teams.LAZIO)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.LAZIO);
                return infoClassificazione;
            }
            else{
                System.out.print("\nNapoli perde scontro diretto contro la Lazio");
                vinceControLazio = false;
            }
        }

        if(napoliPoints < lazioPoints && romaPoints == napoliPoints){
            if(processVincitrice(scontriDirettiSquadre, Teams.ROMA)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.ROMA);
                return infoClassificazione;
            }
            else{
                System.out.print("\nNapoli perde scontro diretto contro la Roma");
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