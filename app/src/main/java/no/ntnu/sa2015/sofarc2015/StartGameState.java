package no.ntnu.sa2015.sofarc2015;

/**
 * Created by catalin on 14/04/15.
 */
public class StartGameState {
    private static StartGameState instance = null;

    private PlayerState p1;
    private PlayerState p2;
    private PlayerState p3;
    private PlayerState p4;

    private StartGameState(){
        p1 = new PlayerState(PlayerType.ON, PlayerColour.BLUE, 1);
        p2 = new PlayerState(PlayerType.OFF, PlayerColour.GREEN, 2);
        p3 = new PlayerState(PlayerType.CPU, PlayerColour.RED, 3);
        p4 = new PlayerState(PlayerType.ON, PlayerColour.YELLOW, 4);
    }

    public static StartGameState getInstance() {
        if (instance == null ) {
            synchronized (StartGameState.class) {
                if (instance == null) {
                    instance = new StartGameState();
                }
            }
        }

        return instance;
    }

    public PlayerState getP1() {
        return p1;
    }

    public PlayerState getP2() {
        return p2;
    }

    public PlayerState getP3() {
        return p3;
    }

    public PlayerState getP4() {
        return p4;
    }
}
