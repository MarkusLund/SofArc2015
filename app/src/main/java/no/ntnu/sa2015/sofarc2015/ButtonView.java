package no.ntnu.sa2015.sofarc2015;

import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * Created by DagInge on 4/12/2015.
 */
public class ButtonView extends View{
    int screenWidth, screenHeight;

    public ButtonView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Button btn = new Button(context);
        btn.setY(screenHeight-(screenHeight/20));

    }
}
