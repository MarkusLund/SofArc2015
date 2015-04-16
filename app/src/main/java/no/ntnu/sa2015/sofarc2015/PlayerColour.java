package no.ntnu.sa2015.sofarc2015;

import android.graphics.Color;

/**
 * Created by catalin on 13/04/15.
 */

public enum PlayerColour {
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    RED(Color.RED),
    BLUE(Color.BLUE);

    private int andoridColourCode;

    PlayerColour(int colourCode) {
        this.andoridColourCode = colourCode;
    }

    public int getColourCode() {
        return andoridColourCode;
    }

}
