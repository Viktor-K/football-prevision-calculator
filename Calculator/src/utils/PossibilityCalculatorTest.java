package utils;

import model.Possibilities;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PossibilityCalculatorTest {

    private Possibilities possibilities3 = new Possibilities();
    private Possibilities possibilities2 = new Possibilities();
    private Possibilities possibilities1 = new Possibilities();


    @Before
    public void init() {

        possibilities3.addPossibility(new String[]{"1", "1", "1"});
        possibilities3.addPossibility(new String[]{"x", "1", "1"});
        possibilities3.addPossibility(new String[]{"2", "1", "1"});
        possibilities3.addPossibility(new String[]{"1", "1", "x"});
        possibilities3.addPossibility(new String[]{"x", "1", "x"});
        possibilities3.addPossibility(new String[]{"2", "1", "x"});
        possibilities3.addPossibility(new String[]{"1", "1", "2"});
        possibilities3.addPossibility(new String[]{"2", "1", "2"});
        possibilities3.addPossibility(new String[]{"x", "1", "2"});
        possibilities3.addPossibility(new String[]{"1", "x", "1"});
        possibilities3.addPossibility(new String[]{"x", "x", "1"});
        possibilities3.addPossibility(new String[]{"2", "x", "1"});
        possibilities3.addPossibility(new String[]{"1", "x", "x"});
        possibilities3.addPossibility(new String[]{"x", "x", "x"});
        possibilities3.addPossibility(new String[]{"2", "x", "x"});
        possibilities3.addPossibility(new String[]{"1", "x", "2"});
        possibilities3.addPossibility(new String[]{"x", "x", "2"});
        possibilities3.addPossibility(new String[]{"2", "x", "2"});
        possibilities3.addPossibility(new String[]{"1", "2", "1"});
        possibilities3.addPossibility(new String[]{"x", "2", "1"});
        possibilities3.addPossibility(new String[]{"2", "2", "1"});
        possibilities3.addPossibility(new String[]{"1", "2", "x"});
        possibilities3.addPossibility(new String[]{"x", "2", "x"});
        possibilities3.addPossibility(new String[]{"2", "2", "x"});
        possibilities3.addPossibility(new String[]{"1", "2", "2"});
        possibilities3.addPossibility(new String[]{"x", "2", "2"});
        possibilities3.addPossibility(new String[]{"2", "2", "2"});


        // 9 possibilita' 2 partite
        possibilities2.addPossibility(new String[]{"1", "1"});
        possibilities2.addPossibility(new String[]{"x", "1"});
        possibilities2.addPossibility(new String[]{"2", "1"});
        possibilities2.addPossibility(new String[]{"1", "2"});
        possibilities2.addPossibility(new String[]{"x", "x"});
        possibilities2.addPossibility(new String[]{"2", "x"});
        possibilities2.addPossibility(new String[]{"1", "x"});
        possibilities2.addPossibility(new String[]{"x", "2"});
        possibilities2.addPossibility(new String[]{"2", "2"});


        // 3 possibilita 1 partita
        possibilities1.addPossibility(new String[]{"1"});
        possibilities1.addPossibility(new String[]{"2"});
        possibilities1.addPossibility(new String[]{"x"});

    }
    @Test
    public void testPossibility_27(){
        PossibilityCalculator possibilityCalculator = new PossibilityCalculator();
        Possibilities possibilityResults = possibilityCalculator.getPossibility(3, 38);
        assertEquals(27, possibilityResults.getPossibilityList().size());
        assertThaContainsResults(possibilityResults, possibilities3);
    }


    @Test
    public void testPossibility_9(){
        PossibilityCalculator possibilityCalculator = new PossibilityCalculator();
        Possibilities possibility = possibilityCalculator.getPossibility(2, 38);
        assertEquals(9, possibility.getPossibilityList().size());
        assertThaContainsResults(possibility, possibilities2);
    }


    @Test
    public void testPossibility_3(){
        PossibilityCalculator possibilityCalculator = new PossibilityCalculator();
        Possibilities possibility = possibilityCalculator.getPossibility(1, 38);
        assertEquals(3, possibility.getPossibilityList().size());
        assertThaContainsResults(possibility, possibilities1);

    }

    private void assertThaContainsResults(Possibilities possibilityResults, Possibilities possibilities) {
        boolean isCorrect = true;
        for(String[] possibility : possibilities.getPossibilityList()){

            if(!containArray(possibility, possibilityResults.getPossibilityList())){
                isCorrect = false;
                break;
            }

        }

        assertTrue(isCorrect);
    }


    private boolean containArray(String[] results, ArrayList<String[]> container){
        for(String[] currResult : container){

            boolean compleateContined = true;
            for(int i=0; i < currResult.length; i++){

                if(!currResult[i].equals(results[i])){
                    compleateContined = false;
                    break;
                }
            }

            if(compleateContined){
                return true;
            }
        }

        return false;
    }
}