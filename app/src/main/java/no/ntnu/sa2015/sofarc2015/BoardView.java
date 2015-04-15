package no.ntnu.sa2015.sofarc2015;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markuslund92 on 10.04.15.
 */

public class BoardView extends View{
    private Paint paint = new Paint();
    private ArrayList<List<String>> board;
    private int tileWidth, screenWidth, screenHeight;
    private Map<String, Point> pieceCoordinates;


    private int diceRoll = 0;
    private String chosenPiece = "none"; // Set to prevent nullPointerException


    public BoardView(Context context, ArrayList<List<String>> board, int screenWidth, int screenHeight, HashMap<String, Point> pieceCoordinates) {
        super(context);
        this.board = board;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.tileWidth = this.screenWidth/board.size();
        this.pieceCoordinates = pieceCoordinates;

    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.i("BoardView", "onDraw called");
        drawBoard(canvas);
        drawPieces(canvas);
        if (this.diceRoll != 0){
            drawDice(canvas, this.diceRoll);
        }
    }

    private void drawDice(Canvas canvas, int dice) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                String tile = board.get(i).get(j);
                switch (tile) {
                    case "d1":
                        if (dice != 1) {
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                    case "d3":
                        if (dice == 4 || dice == 5 || dice == 6){
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                    case "d4":
                        if (dice == 6){
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                    case "d5":
                        if (dice == 1 || dice == 3 || dice == 5){
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                    case "d6":
                        if (dice == 6){
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                    case "d7":
                        if (dice == 4 || dice == 5 || dice == 6){
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                    case "d9":
                        if (dice != 1) {
                            canvas.drawCircle(j*tileWidth+tileWidth/2, i*tileWidth+tileWidth/2, tileWidth / 3, paint);
                        }
                        break;
                }
            }
        }
    }


    private void drawPieces(Canvas canvas) {
        Log.i("BoardView", "drawPieces");
        for (Map.Entry<String, Point> entry : pieceCoordinates.entrySet())
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

        if (piece.equals(chosenPiece)){
            paint.setColor(Color.GRAY);
        }

        canvas.drawCircle(pieceCoordinates.get(piece).x*tileWidth+tileWidth/2,pieceCoordinates.get(piece).y*tileWidth+tileWidth/2,tileWidth/3,paint);

        // Draws stroke around piece
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(pieceCoordinates.get(piece).x * tileWidth + tileWidth / 2, pieceCoordinates.get(piece).y * tileWidth + tileWidth / 2, tileWidth / 3, paint);
    }

    private void drawBoard(Canvas canvas) {
        Log.i("BoardView", "drawBoard");
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
    public float getBoardHeight(){
        return tileWidth*board.size();
    }


    public List<Point> generatePath() {
        List<Point> path = new ArrayList<>();

        path.add(new Point(0,6));
        path.add(new Point(1,6)); //blue start
        path.add(new Point(2,6));
        path.add(new Point(3,6));
        path.add(new Point(4,6));
        path.add(new Point(5,6));

        path.add(new Point(6,5));
        path.add(new Point(6,4));
        path.add(new Point(6,3));
        path.add(new Point(6,2));
        path.add(new Point(6,1));
        path.add(new Point(6,0));

        path.add(new Point(7,0)); //top middle

        path.add(new Point(8,0));
        path.add(new Point(8,1)); //red start
        path.add(new Point(8,2));
        path.add(new Point(8,3));
        path.add(new Point(8,4));
        path.add(new Point(8,5));

        path.add(new Point(9,6));
        path.add(new Point(10,6));
        path.add(new Point(11,6));
        path.add(new Point(12,6));
        path.add(new Point(13,6));
        path.add(new Point(14,6));

        path.add(new Point(14,7)); //right middle

        path.add(new Point(14,8));
        path.add(new Point(13,8)); //green start
        path.add(new Point(12,8));
        path.add(new Point(11,8));
        path.add(new Point(10,8));
        path.add(new Point(9,8));

        path.add(new Point(8,9));
        path.add(new Point(8,10));
        path.add(new Point(8,11));
        path.add(new Point(8,12));
        path.add(new Point(8,13));
        path.add(new Point(8,14));

        path.add(new Point(7,14)); //bottom middle

        path.add(new Point(6,14));
        path.add(new Point(6,13)); //yellow start
        path.add(new Point(6,12));
        path.add(new Point(6,11));
        path.add(new Point(6,10));
        path.add(new Point(6,9));

        path.add(new Point(5,8));
        path.add(new Point(4,8));
        path.add(new Point(3,8));
        path.add(new Point(2,8));
        path.add(new Point(1,8));
        path.add(new Point(0,8));

        path.add(new Point(0,7)); //left middle

        return path;
    }


    public List<Point> generateYellowFinishPath() {
        List<Point> path = new ArrayList<>();

        path.add(new Point(6,13)); //yellow start
        path.add(new Point(7,13));
        path.add(new Point(7,12));
        path.add(new Point(7,11));
        path.add(new Point(7,10));
        path.add(new Point(7,9));

        return path;
    }

    public List<Point> generateBlueFinishPath() {
        List<Point> path = new ArrayList<>();

        path.add(new Point(1,6)); //blue start
        path.add(new Point(1,7));
        path.add(new Point(2,7));
        path.add(new Point(3,7));
        path.add(new Point(4,7));
        path.add(new Point(5,7));

        return path;
    }

    public List<Point> generateRedFinishPath() {
        List<Point> path = new ArrayList<>();

        path.add(new Point(8,1)); //red start
        path.add(new Point(7,1));
        path.add(new Point(7,2));
        path.add(new Point(7,3));
        path.add(new Point(7,4));
        path.add(new Point(7,5));

        return path;
    }

    public List<Point> generateGreenFinishPath() {
        List<Point> path = new ArrayList<>();

        path.add(new Point(13,8)); //green start
        path.add(new Point(13,7));
        path.add(new Point(12,7));
        path.add(new Point(11,7));
        path.add(new Point(10,7));
        path.add(new Point(9,7));

        return path;
    }

    public String getChosenPiece() {
        return chosenPiece;
    }

    public void setChosenPiece(String choice) {
        this.chosenPiece = choice;
        this.invalidate(); //calls the onDraw method
    }

    public int getDiceView() {
        return diceRoll;
    }

    public void setDiceView(int diceRoll) {
        this.diceRoll = diceRoll;
        this.invalidate();
    }

    public void setPieceCoordinates(Map<String, Point> pieceCoordinates) {
        this.pieceCoordinates = pieceCoordinates;
        this.invalidate();
    }

    public Map<String, Point> getPieceCoordinates() {
        return pieceCoordinates;
    }
}
