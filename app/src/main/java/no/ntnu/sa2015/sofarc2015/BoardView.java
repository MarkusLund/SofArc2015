package no.ntnu.sa2015.sofarc2015;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markuslund92 on 10.04.15.
 */

public class BoardView extends View{
    Paint paint = new Paint();
    ArrayList<List<String>> board;
    int tileWidth;

    public BoardView(Context context, ArrayList<List<String>> board) {
        super(context);
        this.board = board;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.i("Width: ", "" + width);
        Log.i("Height: ", "" + height);
        tileWidth = width/board.size();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.e("BoardView", "onDraw called");

        drawBoard(canvas);


    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                String tile = board.get(i).get(j);
                if (tile.equals("B") || tile.equals("1") || tile.equals("b")) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.BLUE);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                } else if (tile.equals("R") || tile.equals("2") || tile.equals("r")) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                } else if (tile.equals("G") || tile.equals("4") || tile.equals("g")) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.GREEN);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                } else if (tile.equals("Y") || tile.equals("3") || tile.equals("y")) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.YELLOW);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                }
                if (tile.equals("#") || tile.equals("1") || tile.equals("2") || tile.equals("3") || tile.equals("4") || tile.equals("g") || tile.equals("y") || tile.equals("r") || tile.equals("b")){
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(2);
                    canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                }
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(2);
                canvas.drawRect(0, 0, tileWidth * board.get(0).size(), tileWidth * board.size(), paint);
            }
        }
    }
}
