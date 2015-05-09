package model.criteria.impl;

import model.ClassificaEntry;
import model.criteria.Criteria;
import model.criteria.Teams;

import java.util.HashMap;

/**
 * @author vcaprio
 */
public class NapoliChampionsCriteria extends Criteria{

    @Override
    public boolean isValid(HashMap<String, ClassificaEntry> classifica) {

        int romaPoints = classifica.get(Teams.ROMA).getPunteggio();
        int lazioPoints = classifica.get(Teams.LAZIO).getPunteggio();
        int napoliPoints = classifica.get(Teams.NAPOLI).getPunteggio();


        if (napoliPoints < lazioPoints && (lazioPoints < romaPoints || lazioPoints == romaPoints)){
            return false;
        }

        if(napoliPoints < romaPoints && (romaPoints < lazioPoints || romaPoints == lazioPoints)){
            return false;
        }

        if(napoliPoints == lazioPoints && lazioPoints == romaPoints){

            //calcola classifica Avulsa
        }

        return true;
    }

}
