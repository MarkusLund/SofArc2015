package no.ntnu.sa2015.sofarc2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameActivity extends Activity{

    private BoardView boardView;
    private ArrayList<List<String>> board = null;
    private int screenWidth, screenHeight;
    private Map<String, Point> pieceCoordinates;
    private List<String> pieceNames;
    private Map<String, Point> blueCoordinates, redCoordinates, greenCoordinates, yellowCoordinates;
    private List<Point> pathCoordinates;
    private List<Point> bluePathCoordinates, redPathCoordinates, greenPathCoordinates, yellowPathCoordinates;
    private Dice dice;

    private char currentPlayer;
    private Map<String, Point> movedPieceCoordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // creating LinearLayout
        LinearLayout linLayout = new LinearLayout(this);
        // specifying vertical orientation
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // set LinearLayout as a root element of the screen
        setContentView(linLayout, linLayoutParam);

        board = readFile("map.txt");
        this.dice = new Dice();

        getScreenSizes();

        pieceCoordinates = new HashMap<>();
        generateStartPositions();

        pieceNames = Arrays.asList("b1", "b2", "b3", "b4", "r1", "r2", "r3", "r4", "g1", "g2", "g3", "g4", "y1", "y2", "y3", "y4");
        blueCoordinates = new HashMap<>();
        redCoordinates = new HashMap<>();
        greenCoordinates = new HashMap<>();
        yellowCoordinates = new HashMap<>();
        addColorCoordinates();
        currentPlayer = 'b'; // Sets the current player
        boardView = new BoardView(this, board, screenWidth, screenHeight, (HashMap<String, Point>) pieceCoordinates);
        boardView.setBackgroundColor(Color.WHITE);
        pathCoordinates = boardView.generatePath();
        bluePathCoordinates = boardView.generateBlueFinishPath();
        redPathCoordinates = boardView.generateRedFinishPath();
        greenPathCoordinates = boardView.generateGreenFinishPath();
        yellowPathCoordinates = boardView.generateYellowFinishPath();


        linLayoutParam.height = (int) boardView.getBoardHeight();
        linLayout.addView(boardView, linLayoutParam);


        Button helpButton = new Button(this);
        helpButton.setText("Help");
        helpButton.setTextSize(20);
        Button changeButton = new Button(this);
        changeButton.setText("Change");
        changeButton.setTextSize(20);
        Button chooseButton = new Button(this);
        chooseButton.setText("Choose");
        chooseButton.setTextSize(20);

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHelp();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeButtonAction();
            }
        });

        chooseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseButtonAction();
            }
        });

        boardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Updates dice.roll value to new int, and updates the BoardView with the new dice value
                boardView.setDiceView(dice.rollDice());
            }
        });

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(screenWidth/2, LinearLayout.LayoutParams.MATCH_PARENT, 1);



        horizontalLayout.addView(changeButton, buttonParams);
        horizontalLayout.addView(chooseButton, buttonParams);

        LinearLayout.LayoutParams buttonLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ((screenHeight-boardView.getBoardHeight())*0.40), 1);
        linLayout.addView(horizontalLayout, buttonLayout);


        LinearLayout.LayoutParams helpLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        linLayout.addView(helpButton, helpLayout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<List<String>> readFile(String path){
        ArrayList<List<String>> board = new ArrayList<List<String>>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(path)));
            String line = reader.readLine();
            while (line != null){
                board.add(new ArrayList<String>(Arrays.asList(line.split(" "))));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader == null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return board;
    }

    private void getScreenSizes() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    private void generateStartPositions() {

        //      Blue start positions
        pieceCoordinates.put("b1", new Point(2,2));
        pieceCoordinates.put("b2", new Point(2,3));
        pieceCoordinates.put("b3", new Point(3,2));
        pieceCoordinates.put("b4", new Point(3,3));

        //      Green start positions
        pieceCoordinates.put("g1", new Point(11,11));
        pieceCoordinates.put("g2", new Point(11,12));
        pieceCoordinates.put("g3", new Point(12,11));
        pieceCoordinates.put("g4", new Point(12,12));

        //     Red start positions
        pieceCoordinates.put("r1", new Point(11,2));
        pieceCoordinates.put("r2", new Point(11,3));
        pieceCoordinates.put("r3", new Point(12,2));
        pieceCoordinates.put("r4", new Point(12,3));

        //      Yellow start positions
        pieceCoordinates.put("y1", new Point(2,11));
        pieceCoordinates.put("y2", new Point(2,12));
        pieceCoordinates.put("y3", new Point(3,11));
        pieceCoordinates.put("y4", new Point(3,12));
    }

    private void addColorCoordinates() {
        for (int i = 0; i < pieceNames.size(); i++) {
            String pieceName = pieceNames.get(i);
            switch (pieceName.charAt(0)) {
                case 'b':
                    blueCoordinates.put(pieceName, pieceCoordinates.get(pieceName));
                    break;
                case 'r':
                    redCoordinates.put(pieceName, pieceCoordinates.get(pieceName));
                    break;
                case 'g':
                    greenCoordinates.put(pieceName, pieceCoordinates.get(pieceName));
                    break;
                case 'y':
                    yellowCoordinates.put(pieceName, pieceCoordinates.get(pieceName));
                    break;
            }
        }
    }

    private void changeButtonAction() {
        String currentPiece = boardView.getChosenPiece();
        switch (currentPiece.charAt(0)) {
            case 'b':
                List<String> bList = new ArrayList<>(blueCoordinates.keySet());
                Collections.sort(bList);
                boardView.setChosenPiece(bList.get((bList.indexOf(currentPiece) + 1)%(bList.size())));
                break;
            case 'r':
                List<String> rList = new ArrayList<>(redCoordinates.keySet());
                Collections.sort(rList);
                boardView.setChosenPiece(rList.get((rList.indexOf(currentPiece) + 1)%(rList.size())));
                break;
            case 'g':
                List<String> gList = new ArrayList<>(greenCoordinates.keySet());
                Collections.sort(gList);
                boardView.setChosenPiece(gList.get((gList.indexOf(currentPiece) + 1)%(gList.size())));
                break;
            case 'y':
                List<String> yList = new ArrayList<>(yellowCoordinates.keySet());
                Collections.sort(yList);
                boardView.setChosenPiece(yList.get((yList.indexOf(currentPiece) + 1)%(yList.size())));
                break;
            default:
                switch (currentPlayer) {
                    case 'b':
                        boardView.setChosenPiece(blueCoordinates.entrySet().iterator().next().getKey());
                        break;
                    case 'r':
                        boardView.setChosenPiece(redCoordinates.entrySet().iterator().next().getKey());
                        break;
                    case 'g':
                        boardView.setChosenPiece(greenCoordinates.entrySet().iterator().next().getKey());
                        break;
                    case 'y':
                        boardView.setChosenPiece(yellowCoordinates.entrySet().iterator().next().getKey());
                        break;
                }
                break;

        }
    }

    private void chooseButtonAction(){
        if (dice.getRoll() == 0){
            boardView.setDiceView(dice.rollDice());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.end_turn_title);
//        builder.setMessage(R.string.end_turn_text);

        builder.setPositiveButton(R.string.end_turn_finish, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                movePiece();
            }
        });

        builder.setNeutralButton(R.string.end_turn_undo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // should first make move, then undo it when this button is pressed
            }
        });

        AlertDialog dialog = builder.create();
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.y = (int) boardView.getBoardHeight();   //y position

        dialog.show();


    }

    private void startMove() {
        Map<String, Point> pieceCoordinates = boardView.getPieceCoordinates();
        switch (currentPlayer) {
            case 'b':
                pieceCoordinates.put(boardView.getChosenPiece(), new Point(1, 6));
                boardView.setPieceCoordinates(pieceCoordinates);
                break;
            case 'r':
                pieceCoordinates.put(boardView.getChosenPiece(), new Point(8, 1));
                boardView.setPieceCoordinates(pieceCoordinates);
                break;
            case 'g':
                pieceCoordinates.put(boardView.getChosenPiece(), new Point(13, 8));
                boardView.setPieceCoordinates(pieceCoordinates);
                break;
            case 'y':
                pieceCoordinates.put(boardView.getChosenPiece(), new Point(6, 13));
                boardView.setPieceCoordinates(pieceCoordinates);
                break;
        }

    }

    private void movePiece(){

        movedPieceCoordinates = boardView.getPieceCoordinates();
        int diceRoll = dice.getRoll();
        String chosenPiece = boardView.getChosenPiece();
        int oldPathIndexOfPiece = getPathIndex(movedPieceCoordinates.get(chosenPiece));

        if (pathCoordinates.contains(movedPieceCoordinates.get(chosenPiece))){ // if the piece is on the Path
            for (int i = 0; i < diceRoll; i++) {
                oldPathIndexOfPiece++;
                if (oldPathIndexOfPiece == 52 && chosenPiece.charAt(0) == 'b') { // if at blue finish, enter blue safezone
                    movedPieceCoordinates.put(chosenPiece, bluePathCoordinates.get(0));
                    boardView.setPieceCoordinates(movedPieceCoordinates);
                    break;
                }
                if (oldPathIndexOfPiece == 13 && chosenPiece.charAt(0) == 'r') {
                    movedPieceCoordinates.put(chosenPiece, redPathCoordinates.get(0));
                    boardView.setPieceCoordinates(movedPieceCoordinates);
                    break;
                }
                if (oldPathIndexOfPiece == 26 && chosenPiece.charAt(0) == 'g') {
                    movedPieceCoordinates.put(chosenPiece, greenPathCoordinates.get(0));
                    boardView.setPieceCoordinates(movedPieceCoordinates);
                    break;
                }
                if (oldPathIndexOfPiece == 39 && chosenPiece.charAt(0) == 'y') {
                    movedPieceCoordinates.put(chosenPiece, yellowPathCoordinates.get(0));
                    boardView.setPieceCoordinates(movedPieceCoordinates);
                    break;
                }
                else {
                    movedPieceCoordinates.put(chosenPiece, pathCoordinates.get(oldPathIndexOfPiece%52));
                    boardView.setPieceCoordinates(movedPieceCoordinates);
                }
            }
        }
        else if (bluePathCoordinates.contains(movedPieceCoordinates.get(chosenPiece))){
            movedPieceCoordinates.put(chosenPiece, bluePathCoordinates.get(1));
            boardView.setPieceCoordinates(movedPieceCoordinates);
        }
        else if (redPathCoordinates.contains(movedPieceCoordinates.get(chosenPiece))){
            movedPieceCoordinates.put(chosenPiece, redPathCoordinates.get(1));
            boardView.setPieceCoordinates(movedPieceCoordinates);
        }
        else if (greenPathCoordinates.contains(movedPieceCoordinates.get(chosenPiece))){
            movedPieceCoordinates.put(chosenPiece, greenPathCoordinates.get(1));
            boardView.setPieceCoordinates(movedPieceCoordinates);
        }
        else if (yellowPathCoordinates.contains(movedPieceCoordinates.get(chosenPiece))){
            movedPieceCoordinates.put(chosenPiece, yellowPathCoordinates.get(1));
            boardView.setPieceCoordinates(movedPieceCoordinates);
        }

        else{// if in home area, move to start
            startMove();
        }


   }

    private Point nextFinishCoord(Point oldCoordinate, int stepsLeft) {
        switch (currentPlayer) {
            case 'b':

        }
        return new Point (4, 7);
    }

    private int getPathIndex(Point point){
        return pathCoordinates.lastIndexOf(point);
    }


    private void switchBetweenColors() {
        ArrayList<Character> player = new ArrayList<Character>();
        player.add('b');
        player.add('r');
        player.add('g');
        player.add('y');

        currentPlayer = player.get((int) (Math.random()*4));

    }

    private void showHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help_title);

        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setPadding(screenWidth/30, screenHeight/50, screenWidth/30, 0);

        TextView helpText = new TextView(this);
        helpText.setText(R.string.help_text);
        Linkify.addLinks(helpText, Linkify.WEB_URLS);
        linLayout.addView(helpText);

        builder.setView(linLayout);


        builder.setNegativeButton(R.string.help_close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
