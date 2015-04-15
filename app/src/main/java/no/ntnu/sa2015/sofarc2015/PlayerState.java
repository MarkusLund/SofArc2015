package no.ntnu.sa2015.sofarc2015;

/**
 * Created by catalin on 13/04/15.
 */
public class PlayerState {
    private int playerId;
    private PlayerType type;
    private PlayerColour colour;


    public PlayerState(PlayerType type, PlayerColour colour, int playerId) {
        this.type = type;
        this.colour = colour;
        this.playerId = playerId;
    }

    public void setNextState() {
        if (type == PlayerType.ON) {
            type = PlayerType.CPU;
        } else if (type == PlayerType.CPU) {
            type = PlayerType.OFF;
        } else {
            type = PlayerType.ON;
        }
    }

    public void setNextColour() {
        if (colour== PlayerColour.YELLOW){
            colour= PlayerColour.BLUE;
        } else if (colour==PlayerColour.BLUE) {
            colour= PlayerColour.GREEN;
        } else if(colour==PlayerColour.GREEN){
            colour=PlayerColour.RED;
        } else {
            colour=PlayerColour.YELLOW;
        }
    }

    public String getStateString() {
        return type.getState();
    }

    public int getColourCode(){
        return colour.getColourCode();
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public PlayerType getType() {
        return type;
    }
}
