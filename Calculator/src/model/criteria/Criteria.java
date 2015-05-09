package model.criteria;

import model.ClassificaEntry;

import java.util.HashMap;

/**
 * @author vcaprio
 */
public abstract class Criteria {

    public abstract boolean isValid(HashMap<String, ClassificaEntry> classifica);

}
