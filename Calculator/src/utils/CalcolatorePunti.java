package utils;

import model.Partita;
import model.RisultatoPartita;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vcaprio
 */
public class CalcolatorePunti {

    private static final int VITTORIA = 3;
    private static final int SCONFITTA = 0;
    private static final int PAREGGIO = 1;

    public List<RisultatoPartita> calculatePoint(final Partita partita){

        switch( partita.getEsito() ){
            case "1" :

                return new ArrayList<RisultatoPartita>(){{
                    add(new RisultatoPartita(partita.getSquadraCasa(), VITTORIA, partita));
                    add(new RisultatoPartita(partita.getSquadraTrasferta(), SCONFITTA, partita));
                }};

            case "2" :
                return new ArrayList<RisultatoPartita>(){{
                    add(new RisultatoPartita(partita.getSquadraCasa(), SCONFITTA, partita));
                    add(new RisultatoPartita(partita.getSquadraTrasferta(), VITTORIA, partita));
                }};


            default :
                return new ArrayList<RisultatoPartita>(){{
                    add(new RisultatoPartita(partita.getSquadraCasa(), PAREGGIO, partita));
                    add(new RisultatoPartita(partita.getSquadraTrasferta(), PAREGGIO, partita));
                }};
        }
    }


}
