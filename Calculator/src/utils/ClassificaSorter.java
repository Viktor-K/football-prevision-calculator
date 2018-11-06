package utils;

import model.ClassificaEntry;

import java.util.*;

/**
 * @author vcaprio
 */
public class ClassificaSorter {

    public List<ClassificaEntry> getClassificaOrdinata(final Map<String, ClassificaEntry> classifica){

        List<ClassificaEntry> classificaSorted = new ArrayList<>(classifica.values());

        Collections.sort(classificaSorted, new Comparator<ClassificaEntry>() {
            @Override
            public int compare(ClassificaEntry entry1, ClassificaEntry entry2) {
                if (entry1.getPunteggio() > entry2.getPunteggio()) {
                    return -1;
                }

                if (entry1.getPunteggio() < entry2.getPunteggio()) {
                    return 1;
                }
                return 0;
            }
        });

        return classificaSorted;

    }

}
