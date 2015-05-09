package model;

/**
 * @author vcaprio
 */
public class Partita {

    String squadraCasa;
    String squadraTrasferta;
    String esito;

    public Partita(String squadraCasa, String squadraTrasferta){
        this.squadraCasa = squadraCasa;
        this.squadraTrasferta = squadraTrasferta;
        this.esito = "X";
    }

    public String getSquadraCasa() {
        return squadraCasa;
    }

    public void setSquadraCasa(String squadraCasa) {
        this.squadraCasa = squadraCasa;
    }

    public String getSquadraTrasferta() {
        return squadraTrasferta;
    }

    public void setSquadraTrasferta(String squadraTrasferta) {
        this.squadraTrasferta = squadraTrasferta;
    }

    public String getEsito() {
        return esito;
    }

    public Partita setEsito(String esito) {
        this.esito = esito;
        return this;
    }
}
