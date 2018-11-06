package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vcaprio
 */
public class InfoClassificazione {

    private String squadra;

    private boolean isChampions = false;

    private List<String> confrontiVinti = new ArrayList<>();


    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }

    public boolean isChampions() {
        return isChampions;
    }

    public void setChampions(boolean isChampions) {
        this.isChampions = isChampions;
    }

    public List<String> getConfrontiVinti() {
        return confrontiVinti;
    }

    public void setConfrontiVinti(List<String> confrontiVinti) {
        this.confrontiVinti = confrontiVinti;
    }

    public void addConfrontoVinto(String squadra){
        this.confrontiVinti.add(squadra);
    }
}
