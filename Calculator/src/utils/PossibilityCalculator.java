package utils;

import model.Possibilities;

/**
 * @author vcaprio
 */
public class PossibilityCalculator {

    public Possibilities getPossibility(int matchNumber, int days){

        String[] strings = new String[matchNumber];
        for(int i=0; i< matchNumber; i++){
            strings[i] = "";
        }

        return calculatePossibility(new Possibilities(), strings, matchNumber, 1);
    }

    private Possibilities calculatePossibility(Possibilities possibilities, String[] elaboratedCombination, int matchNumber, int currentMatch){

        if(currentMatch > matchNumber){
            possibilities.addPossibility(elaboratedCombination);
            return possibilities;
        }

        for(String elem :  new String[]{"1","x","2"}){
            String[] newCombination = cloneArray(elaboratedCombination);
            newCombination[currentMatch - 1] = elem;
            calculatePossibility(possibilities, newCombination, matchNumber, currentMatch + 1);
        }

        return possibilities;
    }


    private String[] cloneArray(String[] array){
        String[] newArray = new String[array.length];

        for(int i=0; i<array.length; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
}
