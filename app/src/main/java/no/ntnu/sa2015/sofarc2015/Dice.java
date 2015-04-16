package no.ntnu.sa2015.sofarc2015;

/**
 * Created by markuslund92 on 14.04.15.
 */
public class Dice {

    private int roll;

    public Dice(){
        this.roll = 0;
    }

    public int getRoll(){
        return this.roll;
    }

    public int rollDice(){
        return this.roll = (int) (Math.random()*6+1);
    }

    public void resetDice(){
        this.roll = 0;
    }
}
