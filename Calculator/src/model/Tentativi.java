package model;

/**
 * @author vcaprio
 */
public class Tentativi {
    int count;

    public Tentativi(int count){
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Tentativi increment(){
        this.count ++;
        return this;
    }
}
