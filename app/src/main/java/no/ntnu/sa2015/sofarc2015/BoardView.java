package no.ntnu.sa2015.sofarc2015;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markuslund92 on 10.04.15.
 */

public class BoardView extends View{
    Paint paint = new Paint();
    ArrayList<List<String>> board;
    int tileWidth, screenWidth, screenHeight;
    Map<String, int[]> pieceCoordinates;

    public BoardView(Context context, ArrayList<List<String>> board, int screenWidth, int screenHeight, HashMap<String, int[]> pieceCoordinates) {
        super(context);
        this.board = board;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.tileWidth = this.screenWidth/board.size();
        this.pieceCoordinates = pieceCoordinates;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.e("BoardView", "onDraw called");

        drawBoard(canvas);
        drawPieces(canvas);
    }

    private void drawPieces(Canvas canvas) {
        for (Map.Entry<String, int[]> entry : pieceCoordinates.entrySet())
        {
            drawPiece(canvas, entry.getKey());
        }
    }

    private void drawPiece(Canvas canvas, String piece) {
        paint.setStyle(Paint.Style.FILL);
        if (piece.charAt(0) == 'b') {
            paint.setColor(Color.BLUE);
        }else if (piece.charAt(0) == 'y') {
            paint.setColor(Color.YELLOW);
        }else if (piece.charAt(0) == 'g') {
            paint.setColor(Color.GREEN);
        }else if (piece.charAt(0) == 'r') {
            paint.setColor(Color.RED);
        }

        canvas.drawCircle(pieceCoordinates.get(piece)[0]*tileWidth+tileWidth/2,pieceCoordinates.get(piece)[1]*tileWidth+tileWidth/2,tileWidth/3,paint);

        // Draws stroke around piece
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(pieceCoordinates.get(piece)[0] * tileWidth + tileWidth / 2, pieceCoordinates.get(piece)[1] * tileWidth + tileWidth / 2, tileWidth / 3, paint);

    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                String tile = board.get(i).get(j);
                switch (tile) {
                    case "B":
                    case "1":
                    case "b":
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.BLUE);
                        paint.setStrokeWidth(3);
                        canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                        break;
                    case "R":
                    case "2":
                    case "r":
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.RED);
                        paint.setStrokeWidth(3);
                        canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                        break;
                    case "G":
                    case "4":
                    case "g":
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.GREEN);
                        paint.setStrokeWidth(3);
                        canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                        break;
                    case "Y":
                    case "3":
                    case "y":
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.YELLOW);
                        paint.setStrokeWidth(3);
                        canvas.drawRect(j * tileWidth, i * tileWidth, j * tileWidth + tileWidth, i * tileWidth + tileWidth, paint);
                        break;
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
