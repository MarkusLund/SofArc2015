package no.ntnu.sa2015.sofarc2015;

/**
 * Created by catalin on 13/04/15.
 */

public enum PlayerType {
    ON("ON"),
    OFF("OFF"),
    CPU("CPU");

    private final String state;

    private PlayerType(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }
}
