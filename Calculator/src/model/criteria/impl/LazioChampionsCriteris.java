package model.criteria.impl;

import model.ClassificaEntry;
import model.criteria.Criteria;
import model.criteria.Teams;

import java.util.HashMap;

/**
 * @author vcaprio
 */
public class LazioChampionsCriteris extends Criteria{

    private String team = Teams.LAZIO;

    public boolean isValid(HashMap<String, ClassificaEntry> classifica) {

        int romaPoints = classifica.get(Teams.ROMA).getPunteggio();
        int lazioPoints = classifica.get(Teams.LAZIO).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();

        
        if (lazioPoints < romaPoints && (romaPoints < napoliPoints || romaPoints == napoliPoints)){
            return false;
        }

        if(lazioPoints < napoliPoints && (napoliPoints < romaPoints || napoliPoints == romaPoints)){
            return false;
        }

        if(lazioPoints == romaPoints && romaPoints == napoliPoints){

            //calcola classifica Avulsa
        }

        return true;
    }
}
