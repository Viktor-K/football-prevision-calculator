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
public class LazioChampionsCriteria extends Criteria{

    public LazioChampionsCriteria(){
        team = Teams.LAZIO;
    }

    @Override
    public InfoClassificazione isValid(HashMap<String, ClassificaEntry> classifica, List<ScontriDirettiCriteria> scontriDirettiSquadre) {
        InfoClassificazione infoClassificazione = new InfoClassificazione();
        infoClassificazione.setSquadra(team);
        infoClassificazione.setChampions(true);

        int romaPoints = classifica.get(Teams.ROMA).getPunteggio();
        int lazioPoints = classifica.get(Teams.LAZIO).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();

        
        if (lazioPoints < romaPoints && (romaPoints < napoliPoints || romaPoints == napoliPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(lazioPoints < napoliPoints && (napoliPoints < romaPoints || napoliPoints == romaPoints)){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        if(romaPoints == napoliPoints && napoliPoints == lazioPoints){
            //calcola classifica Avulsa
            System.out.print("\nParimerito Napoli Lazio Roma\n");
        }

        boolean vinceControNapoli = true;
        boolean vinceControRoma = true;

        if(lazioPoints < romaPoints && napoliPoints == lazioPoints){
            if (processVincitrice(scontriDirettiSquadre, Teams.NAPOLI)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.NAPOLI);
                return infoClassificazione;
            }
            else{
                vinceControNapoli = false;
            }
        }

        if(lazioPoints < napoliPoints && romaPoints == lazioPoints){
            if(processVincitrice(scontriDirettiSquadre, Teams.ROMA)){
                infoClassificazione.setChampions(true);
                infoClassificazione.addConfrontoVinto(Teams.ROMA);
                return infoClassificazione;
            }
            else{
                vinceControRoma = false;
            }
        }

        if(!vinceControNapoli || !vinceControRoma){
            infoClassificazione.setChampions(false);
            return infoClassificazione;
        }

        return infoClassificazione;
    }
}