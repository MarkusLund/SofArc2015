package no.ntnu.sa2015.sofarc2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends Activity{

    private BoardView boardView;
    private HelpView helpView;
    private ArrayList<List<String>> board = null;
    private int screenWidth, screenHeight;
    private List<String> pieceNames;
    private Map<String, Point> blueCoordinates, redCoordinates, greenCoordinates, yellowCoordinates;
    private List<Point> pathCoordinates;
    private List<Point> bluePathCoordinates, redPathCoordinates, greenPathCoordinates, yellowPathCoordinates;
    private Dice dice;

    private Point oldCoordinates = null;
    private Map<String, Point> homeCoordinates;
    private char currentPlayer; //SAVE
    private char[] players = {'b','r','g','y'};
    private int index = 0;

    private HashMap<String, Point> pieceCoordinates; //SAVE
    private int nofSoundFiles;
    private int[] soundFiles;
    private String chosenPieceToMove;
    private boolean hasRolled;

    private StartGameState state = StartGameState.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            pieceCoordinates = (HashMap<String, Point>) savedInstanceState.getSerializable("Coordinates");
            currentPlayer = savedInstanceState.getChar("currentPlayer");
        } else {
            //Creating the initial hashmap with all the piece coordinates piece : Point
            pieceCoordinates = generateStartPositions();
            currentPlayer = 'b'; // Sets the current player
        }



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

        generateSoundFiles();
        getScreenSizes();

        //Creating the initial hashmap with all the piece coordinates piece : Point
        homeCoordinates = generateStartPositions();

        pieceNames = Arrays.asList("b1", "b2", "b3", "b4", "r1", "r2", "r3", "r4", "g1", "g2", "g3", "g4", "y1", "y2", "y3", "y4");
        blueCoordinates = new HashMap<>();
        redCoordinates = new HashMap<>();
        greenCoordinates = new HashMap<>();
        yellowCoordinates = new HashMap<>();
        addColorCoordinates();

        boardView = new BoardView(this, board, screenWidth, screenHeight, pieceCoordinates);
        boardView.setBackgroundColor(Color.WHITE);
        pathCoordinates = boardView.generatePath();
        bluePathCoordinates = boardView.generateBlueFinishPath();
        redPathCoordinates = boardView.generateRedFinishPath();
        greenPathCoordinates = boardView.generateGreenFinishPath();
        yellowPathCoordinates = boardView.generateYellowFinishPath();


        linLayoutParam.height = (int) boardView.getBoardHeight();
        linLayout.addView(boardView, linLayoutParam);


        Button helpButton = new Button(this);
        helpButton.setText(R.string.helpButton_text);
        helpButton.setTextSize(20);

        helpView = new HelpView(this, screenWidth, screenHeight);


        Button changeButton = new Button(this);
        changeButton.setText(R.string.changeButton_text);
        changeButton.setTextSize(20);
        Button chooseButton = new Button(this);
        chooseButton.setText(R.string.chooseButton_text);
        chooseButton.setTextSize(20);

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                helpView.show();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeButtonAction();
            }
        });

        chooseButton.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                chooseButtonAction();
            }
        });

        boardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Updates dice.roll value to new int, and updates the BoardView with the new dice value
                if (!hasRolled) {
                    boardView.setDiceView(dice.rollDice());
                    hasRolled = true;
                }
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

    private void playDontGetMadBroSound() {
        MediaPlayer mp;
        mp = MediaPlayer.create(this, soundFiles[new Random().nextInt(nofSoundFiles)]);
        mp.start();
    }

    private void generateSoundFiles(){
        nofSoundFiles = 10;
        soundFiles = new int[nofSoundFiles];
        soundFiles[0] = R.raw.danish;
        soundFiles[1] = R.raw.english;
        soundFiles[2] = R.raw.italian;
        soundFiles[3] = R.raw.japanese;
        soundFiles[4] = R.raw.norsk;
        soundFiles[5] = R.raw.norwegian;
        soundFiles[6] = R.raw.swedish;
        soundFiles[7] = R.raw.russian;
        soundFiles[8] = R.raw.chinese;
        soundFiles[9] = R.raw.french;
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

    private HashMap<String, Point> generateStartPositions() {

        HashMap<String, Point> apieceCoordinates = new HashMap<>();

        //      Blue start positions
        apieceCoordinates.put("b1", new Point(2,2));
        apieceCoordinates.put("b2", new Point(2,3));
        apieceCoordinates.put("b3", new Point(3,2));
        apieceCoordinates.put("b4", new Point(3,3));

        //      Green start positions
        apieceCoordinates.put("g1", new Point(11,11));
        apieceCoordinates.put("g2", new Point(11,12));
        apieceCoordinates.put("g3", new Point(12,11));
        apieceCoordinates.put("g4", new Point(12,12));

        //     Red start positions
        apieceCoordinates.put("r1", new Point(11,2));
        apieceCoordinates.put("r2", new Point(11,3));
        apieceCoordinates.put("r3", new Point(12,2));
        apieceCoordinates.put("r4", new Point(12,3));

        //      Yellow start positions
        apieceCoordinates.put("y1", new Point(2,11));
        apieceCoordinates.put("y2", new Point(2,12));
        apieceCoordinates.put("y3", new Point(3,11));
        apieceCoordinates.put("y4", new Point(3,12));

        return apieceCoordinates;
    }

    private void addColorCoordinates() { // adds color pieces for choosing current piece functionality
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

    private void changeButtonAction() { // implements changing of chosen piece functionality
        String nextPiece = null;
        if (chosenPieceToMove == null) { // if no piece has been selected, choose first piece
            switch (currentPlayer) {
                case 'b':
                    nextPiece = blueCoordinates.entrySet().iterator().next().getKey();
                    break;
                case 'r':
                    nextPiece = redCoordinates.entrySet().iterator().next().getKey();
                    break;
                case 'g':
                    nextPiece = greenCoordinates.entrySet().iterator().next().getKey();
                    break;
                case 'y':
                    nextPiece = yellowCoordinates.entrySet().iterator().next().getKey();
                    break;
            }
        }
        else { // if one piece has been selected, chose next possible piece in list
            String currentPiece = chosenPieceToMove;
            List<String> listCoordinates = new ArrayList<>();

            switch (currentPiece.charAt(0)) {
                case 'b':
                    listCoordinates = new ArrayList<>(blueCoordinates.keySet());
                    break;
                case 'r':
                    listCoordinates = new ArrayList<>(redCoordinates.keySet());
                    break;
                case 'g':
                    listCoordinates = new ArrayList<>(greenCoordinates.keySet());
                    break;
                case 'y':
                    listCoordinates = new ArrayList<>(yellowCoordinates.keySet());
                    break;
            }

            Collections.sort(listCoordinates); // sorts list, b1, b2, b3, b4
            nextPiece = listCoordinates.get((listCoordinates.indexOf(currentPiece) + 1) % (listCoordinates.size()));
        }
        boardView.setChosenPiece(nextPiece);
        chosenPieceToMove = nextPiece;
    }

    private void chooseButtonAction(){
        if (dice.getRoll() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.chooseButton_needRoll)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (chosenPieceToMove == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.chooseButton_needChoice)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(dice.getRoll() != 6 && homeCoordinates.containsValue(pieceCoordinates.get(chosenPieceToMove))){
            // Alerts players that move will result in skip
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.exit_home_title);
            builder.setMessage(R.string.exit_home_text);
            builder.setCancelable(false);

            builder.setPositiveButton(R.string.exit_home_no_move, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // no move is possible, end turn
                    endTurn();
                }
            });

            builder.setNeutralButton(R.string.exit_home_change_move, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // change piece to move
                }
            });

            AlertDialog dialog = builder.create();
            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

            wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            wmlp.y = (int) boardView.getBoardHeight();   //y position

            dialog.show();
        }
//        else if (willStack()){ // willStack method a bit buggy, have therfore turned it of.
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(R.string.chooseButton_noStacking)
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //do things
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
        else {

            movePiece(); // move is done before

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.end_turn_title);
            builder.setCancelable(false);

            builder.setPositiveButton(R.string.end_turn_finish, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    movePiece();

                    endTurn();
                }
            });

            builder.setNeutralButton(R.string.end_turn_undo, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // should first make move, then undo it when this button is pressed
                    undoMove();
                }
            });


            AlertDialog dialog = builder.create();
            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

            wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            wmlp.y = (int) boardView.getBoardHeight();   //y position

            dialog.show();
        }


    }

    private void endTurn() {
        //check if winner
        switch (currentPlayer) {
            case ('b'):
                if (blueCoordinates.size() == 0) {
                    Log.e("winner", currentPlayer +"");
                    // display winscreen, return to menu
                    finish();
                }
                break;
            case ('r'):
                if (redCoordinates.size() == 0) {
                    Log.e("winner", currentPlayer +"");
                    finish();
                }
                break;
            case ('g'):
                if (greenCoordinates.size() == 0) {
                    Log.e("winner", currentPlayer +"");
                    finish();
                }
                break;
            case ('y'):
                if (yellowCoordinates.size() == 0) {
                    Log.e("winner", currentPlayer +"");
                    finish();
                }
                break;
        }

        //check if a piece must be sent home, not needed if in home area and move results in win
        if (pieceCoordinates.containsKey(chosenPieceToMove)){
        sendPieceHome();
        }

        //change current player
        switchBetweenColors();

        //set chosen piece to null
        chosenPieceToMove = null;
        boardView.setChosenPiece(null);

        //reset dice
        dice.resetDice();
        boardView.setDiceView(dice.getRoll());

        //prevents player from rolling again
        hasRolled = false;



    }

    private void exitHomeMove() { // moves a piece from home to start position on board
        switch (currentPlayer) {
            case 'b':
                pieceCoordinates.put(chosenPieceToMove, new Point(1, 6));
                break;
            case 'r':
                pieceCoordinates.put(chosenPieceToMove, new Point(8, 1));
                break;
            case 'g':
                pieceCoordinates.put(chosenPieceToMove, new Point(13, 8));
                break;
            case 'y':
                pieceCoordinates.put(chosenPieceToMove, new Point(6, 13));
                break;
        }
        boardView.setPieceCoordinates(pieceCoordinates);
    }

    private void movePiece(){ // moves a piece x number of steps

        int diceRoll = dice.getRoll();
        int oldPathIndexOfPiece = getPathIndex(pieceCoordinates.get(chosenPieceToMove)); // gets index of current piece on path
        oldCoordinates = pieceCoordinates.get(chosenPieceToMove); // oldCoordinates for undoMove
        if (pathCoordinates.contains(pieceCoordinates.get(chosenPieceToMove))){ // if the piece is on the Path
            for (int i = 0; i < diceRoll; i++) {
                oldPathIndexOfPiece++; // moves the piece forward one step at a time
                if (oldPathIndexOfPiece == 52 && chosenPieceToMove.charAt(0) == 'b') { // if at blue finish, enter blue safezone
                    nextFinishCoord(diceRoll-i); // start running nextFinishCoord function with number of steps left
                    break;
                }
                if (oldPathIndexOfPiece == 13 && chosenPieceToMove.charAt(0) == 'r') {
                    nextFinishCoord(diceRoll-i);
                    break;
                }
                if (oldPathIndexOfPiece == 26 && chosenPieceToMove.charAt(0) == 'g') {
                    nextFinishCoord(diceRoll - i);
                    break;
                }
                if (oldPathIndexOfPiece == 39 && chosenPieceToMove.charAt(0) == 'y') {
                    nextFinishCoord(diceRoll - i);
                    break;
                }
                else { // if not at a colors finish position, continue down the path
                    pieceCoordinates.put(chosenPieceToMove, pathCoordinates.get(oldPathIndexOfPiece%52));
                    boardView.setPieceCoordinates(pieceCoordinates);
                }
                // implements wait/sleep
                stepSleepTime(1000);

            }
        } // if the piece already is on the finishpath, run nexFinishCoord with x number of steps
        else if (bluePathCoordinates.contains(pieceCoordinates.get(chosenPieceToMove))){
            nextFinishCoord(diceRoll);
            }
        else if (redPathCoordinates.contains(pieceCoordinates.get(chosenPieceToMove))){
            nextFinishCoord(diceRoll);
        }
        else if (greenPathCoordinates.contains(pieceCoordinates.get(chosenPieceToMove))){
            nextFinishCoord(diceRoll);
        }
        else if (yellowPathCoordinates.contains(pieceCoordinates.get(chosenPieceToMove))){
            nextFinishCoord(diceRoll);
        }

        else{// if in home area, move to start
            if (dice.getRoll() == 6) {  // only allow roling a six the option of exiting home

                exitHomeMove();
            }
        }
   }

    private void sendPieceHome() { // if current piece lands on opponent, opponent is sent home
        for (Map.Entry<String,Point> entry : pieceCoordinates.entrySet()) {
            String checkedPiece = entry.getKey();
            Point checkedPieceValue = entry.getValue();

            // if the two pieces are different players, one is sent home
            if(!chosenPieceToMove.equals(checkedPiece) && chosenPieceToMove.charAt(0) != checkedPiece.charAt(0) && pieceCoordinates.get(chosenPieceToMove).equals(checkedPieceValue)) {
                pieceCoordinates.put(checkedPiece, homeCoordinates.get(checkedPiece)); // returns piece to its home position
                playDontGetMadBroSound();
            }
        }
        boardView.setPieceCoordinates(pieceCoordinates);
    }

    private void undoMove() {
        pieceCoordinates.put(chosenPieceToMove, oldCoordinates);
        boardView.setPieceCoordinates(pieceCoordinates);
    }

    private boolean willStack() { // home stackable check works, the rest... not som much....
        if(homeCoordinates.containsValue(pieceCoordinates.get(chosenPieceToMove))){ // if piece is in home
            for (Map.Entry<String , Point> entry : pieceCoordinates.entrySet()){
                switch (chosenPieceToMove.charAt(0)) {
                    case 'b':
                        if (entry.getValue().equals(new Point (1, 6)) && entry.getKey().charAt(0) == 'b'){
                            return true;
                        }
                        break;
                    case 'r':
                        if (entry.getValue().equals(new Point (8, 1)) && entry.getKey().charAt(0) == 'r'){
                            return true;
                        }
                        break;
                    case 'g':
                        if (entry.getValue().equals(new Point (13, 8)) && entry.getKey().charAt(0) == 'g'){
                            return true;
                        }
                        break;
                    case 'y':
                        if (entry.getValue().equals(new Point (6, 13)) && entry.getKey().charAt(0) == 'y'){
                            return true;
                        }
                        break;

                }
            }
        }
        else if (pieceCoordinates.containsKey(chosenPieceToMove) && pathCoordinates.contains(pieceCoordinates.get(chosenPieceToMove))){ // if chosen piece to move is on the path
            int indexPiece = pathCoordinates.indexOf(pieceCoordinates.get(chosenPieceToMove));
            int movedIndexPiece = indexPiece + dice.getRoll();
            Point newCoordinates = pathCoordinates.get(movedIndexPiece%52);
            for (Map.Entry<String, Point> entry : pieceCoordinates.entrySet()) {
                if(entry.getValue().equals(newCoordinates) && chosenPieceToMove.charAt(0) == entry.getKey().charAt(0)){
                    for (int i = indexPiece; i < movedIndexPiece; i++){
                        switch (chosenPieceToMove.charAt(0)){
                            case ('b'):
                                if(i == 52){
                                    return false;
                                }
                                break;
                            case ('r'):
                                if(i == 13){
                                    return false;
                                }
                                break;
                            case ('g'):
                                if(i == 26){
                                    return false;
                                }
                                break;
                            case ('y'):
                                if(i == 39){
                                    return false;
                                }
                                break;
                            default:
                                return true;
                        }
                    }
                }
            }
        }
        // TODO: Piece is on the finishing path, no stacking in finisharea to be implemented if we have time
        return false; // no stacking, move is doable
    }



    private void nextFinishCoord(int stepsLeft) { // moves piece x number of steps forward and back in finish path
        boolean towardsGoal = true; // if heading towards or away from goal
        switch (currentPlayer) {
            case 'b':
                int indexBlue = bluePathCoordinates.indexOf(pieceCoordinates.get(chosenPieceToMove)); //current index on finishPath
                if (indexBlue + stepsLeft == 5){ // if moves result in entering goal, remove the piece
                    pieceCoordinates.remove(chosenPieceToMove);
                    blueCoordinates.remove(chosenPieceToMove);
                    break;
                }
                for (int i = stepsLeft; i >= 1; i--) { // moves piece one step at a rime forward, and if enough moves, back again
                    indexBlue = towardsGoal ? indexBlue + 1 : indexBlue - 1; // decides what direction to move piece
                    towardsGoal = indexBlue==5 ? !towardsGoal : towardsGoal; // changes direction of piece if index is 5 ( outofbounds )
                    if (indexBlue==5){
                        indexBlue = 4;
                        i--;
                    }
                    pieceCoordinates.put(chosenPieceToMove, bluePathCoordinates.get(indexBlue));
                    // to add steps, implement wait/sleep
                    stepSleepTime(1000);
                }
                break;
            case 'r':
                int indexRed = redPathCoordinates.indexOf(pieceCoordinates.get(chosenPieceToMove));
                if (indexRed + stepsLeft == 5){
                    pieceCoordinates.remove(chosenPieceToMove);
                    redCoordinates.remove(chosenPieceToMove);
                    break;
                }
                for (int i = stepsLeft; i >= 1; i--) {
                    indexRed = towardsGoal ? indexRed + 1 : indexRed - 1;
                    towardsGoal = indexRed==5 ? !towardsGoal : towardsGoal;
                    if (indexRed==5){
                        indexRed = 4;
                        i--;
                    }
                    pieceCoordinates.put(chosenPieceToMove, redPathCoordinates.get(indexRed));
                    // to add steps, implement wait/sleep
                    stepSleepTime(1000);
                }
                break;
            case 'g':
                int indexGreen = greenPathCoordinates.indexOf(pieceCoordinates.get(chosenPieceToMove));
                if (indexGreen + stepsLeft == 5){
                    pieceCoordinates.remove(chosenPieceToMove);
                    greenCoordinates.remove(chosenPieceToMove);
                    break;
                }
                for (int i = stepsLeft; i >= 1; i--) {
                    indexGreen = towardsGoal ? indexGreen + 1 : indexGreen - 1;
                    towardsGoal = indexGreen==5 ? !towardsGoal : towardsGoal;
                    if (indexGreen==5){
                        indexGreen = 4;
                        i--;
                    }
                    pieceCoordinates.put(chosenPieceToMove, greenPathCoordinates.get(indexGreen));
                    // to add steps, implement wait/sleep
                    stepSleepTime(1000);
                }
                break;
            case 'y':
                int indexYellow = yellowPathCoordinates.indexOf(pieceCoordinates.get(chosenPieceToMove));
                if (indexYellow + stepsLeft == 5){
                    pieceCoordinates.remove(chosenPieceToMove);
                    yellowCoordinates.remove(chosenPieceToMove);
                    break;
                }
                for (int i = stepsLeft; i >= 1; i--) {
                    indexYellow = towardsGoal ? indexYellow + 1 : indexYellow - 1;
                    towardsGoal = indexYellow==5 ? !towardsGoal : towardsGoal;
                    if (indexYellow==5){
                        indexYellow = 4;
                        i--;
                    }
                    pieceCoordinates.put(chosenPieceToMove, yellowPathCoordinates.get(indexYellow));
                    // to add steps, implement wait/sleep
                    stepSleepTime(1000);
                }
                break;
        }
        boardView.setPieceCoordinates(pieceCoordinates);
    }

    private int getPathIndex(Point point){
        return pathCoordinates.lastIndexOf(point);
    }

    private void stepSleepTime(int time) { // when implemented, gives appearance of movement instead of teleportation of pieces
        //SystemClock.sleep(time);

    }

    private void switchBetweenColors() { // simple method that randomly??WUT choses what player to use
        currentPlayer = players[++index%4];
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.quit)
                    .setMessage(R.string.really_quit)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();

            return true;
        }else{
            return super.onKeyDown(keyCode,event);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putChar("currentPlayer", currentPlayer);
        outState.putSerializable("Coordinates", pieceCoordinates);
        super.onSaveInstanceState(outState);
    }

}
