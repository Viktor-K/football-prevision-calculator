package model.criteria;

import model.ClassificaEntry;
import model.InfoClassificazione;
import model.VincitriceScontriDiretti;

import java.util.HashMap;
import java.util.List;

/**
 * @author vcaprio
 */
public abstract class Criteria {

    protected String team;

    private ScontriDirettiCriteria scontriDirettiCriteria;

    public abstract InfoClassificazione isValid(HashMap<String, ClassificaEntry> classifica, List<ScontriDirettiCriteria> scontriDirettiSquadre);

    protected VincitriceScontriDiretti calcolaVincitriceScontriDiretti(List<ScontriDirettiCriteria> scontriDirettiSquadre, String squadra1, String squadra2){
        VincitriceScontriDiretti vincitrice = new VincitriceScontriDiretti();
        vincitrice.setEsisteVincitrice(false);
        
        for(ScontriDirettiCriteria scontriDirettiCriteria : scontriDirettiSquadre){
            if(scontriDirettiCriteria.isSControTra(squadra1, squadra2)){
                vincitrice = scontriDirettiCriteria.getVincitrice();
                vincitrice.setEsisteVincitrice(true);
            }
        }
        
        return vincitrice;
    }

    protected boolean processVincitrice(List<ScontriDirettiCriteria> scontriDirettiSquadre, String squadraAvversaria) {
        VincitriceScontriDiretti vincitrice = calcolaVincitriceScontriDiretti(scontriDirettiSquadre, squadraAvversaria, team);

        if(vincitrice.isEsisteVincitrice() && team.equals(vincitrice.getVincitrice())){
            return true;
        }

        if(vincitrice.isEsisteVincitrice()){
            return false;
        }
        else{
            return true;
        }
    }
}
