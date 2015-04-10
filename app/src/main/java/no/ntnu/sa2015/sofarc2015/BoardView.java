package no.ntnu.sa2015.sofarc2015;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by markuslund92 on 10.04.15.
 */

public class BoardView extends View{
    Paint paint = new Paint();

    public BoardView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        try {
            InputStream in = getContext().getAssets().open("map.txt");
            Log.i("Halla", in.toString());
            System.out.println(in.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.RED);
        canvas.drawRect(33, 33, 77, 60, paint );

    }
}
