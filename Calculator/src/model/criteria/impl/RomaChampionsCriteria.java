package model.criteria.impl;

import model.ClassificaEntry;
import model.criteria.Criteria;
import model.criteria.Teams;

import java.util.HashMap;

/**
 * @author vcaprio
 */
public class RomaChampionsCriteria extends Criteria{

    public boolean isValid(HashMap<String, ClassificaEntry> classifica) {
        int romaPoints = classifica.get(Teams.ROMA).getPunteggio();
        int lazioPoints = classifica.get(Teams.LAZIO).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();


        if (romaPoints < napoliPoints && (napoliPoints < lazioPoints || napoliPoints == lazioPoints)){
            return false;
        }

        if(romaPoints < lazioPoints && (lazioPoints < napoliPoints || lazioPoints == napoliPoints)){
            return false;
        }

        if(romaPoints == napoliPoints && napoliPoints == lazioPoints){

            //calcola classifica Avulsa
        }

        return true;
    }
}
