package no.ntnu.sa2015.sofarc2015;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameActivity extends Activity{

    BoardView boardView;
    ButtonView buttonView;
    ArrayList<List<String>> board = null;
    int screenWidth, screenHeight;
    Map<String, int[]> pieceCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        getScreenSizes();
        pieceCoordinates = new HashMap<>();
        generateStartPositions();
        boardView = new BoardView(this, board, screenWidth, screenHeight, (HashMap<String, int[]>) pieceCoordinates);
        boardView.setBackgroundColor(Color.WHITE);
        linLayout.addView(boardView, linLayoutParam);

        buttonView = new ButtonView(this, screenWidth, screenHeight);
        linLayout.addView(buttonView, linLayoutParam);
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
        int[] cor1 = {2,2};
        int[] cor2 = {2,3};
        int[] cor3 = {3,2};
        int[] cor4 = {3,3};
        pieceCoordinates.put("b1", cor1);
        pieceCoordinates.put("b2", cor2);
        pieceCoordinates.put("b3", cor3);
        pieceCoordinates.put("b4", cor4);

        //      Green start positions
        int[] cor5 = {11,11};
        int[] cor6 = {11,12};
        int[] cor7 = {12,11};
        int[] cor8 = {12,12};
        pieceCoordinates.put("g1", cor5);
        pieceCoordinates.put("g2", cor6);
        pieceCoordinates.put("g3", cor7);
        pieceCoordinates.put("g4", cor8);

        //     Red start positions
        int[] cor9 = {11,2};
        int[] cor10 = {11,3};
        int[] cor11 = {12,2};
        int[] cor12 = {12,3};
        pieceCoordinates.put("r1", cor9);
        pieceCoordinates.put("r2", cor10);
        pieceCoordinates.put("r3", cor11);
        pieceCoordinates.put("r4", cor12);

        //      Yellow start positions
        int[] cor13 = {2,11};
        int[] cor14 = {2,12};
        int[] cor15 = {3,11};
        int[] cor16 = {3,12};
        pieceCoordinates.put("y1", cor13);
        pieceCoordinates.put("y2", cor14);
        pieceCoordinates.put("y3", cor15);
        pieceCoordinates.put("y4", cor16);
    }
}
