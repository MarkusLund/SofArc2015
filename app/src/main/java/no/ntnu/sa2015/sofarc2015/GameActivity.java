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

    private BoardView boardView;
    private ArrayList<List<String>> board = null;
    private int screenWidth, screenHeight;
    private Map<String, Point> pieceCoordinates;

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
        boardView = new BoardView(this, board, screenWidth, screenHeight, (HashMap<String, Point>) pieceCoordinates);
        boardView.setBackgroundColor(Color.WHITE);


        linLayoutParam.height = (int) boardView.getBoardHeight();
        linLayout.addView(boardView, linLayoutParam);


        Button rollButton = new Button(this);
        rollButton.setText("Roll");

        Button changeButton = new Button(this);
        changeButton.setText("Change");

        Button chooseButton = new Button(this);
        chooseButton.setText("Choose");

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(screenWidth/3, LinearLayout.LayoutParams.MATCH_PARENT, 1);



        horizontalLayout.addView(rollButton, buttonParams);
        horizontalLayout.addView(changeButton, buttonParams);
        horizontalLayout.addView(chooseButton, buttonParams);

        LinearLayout.LayoutParams buttonLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linLayout.addView(horizontalLayout, buttonLayout);

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
}
