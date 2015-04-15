package no.ntnu.sa2015.sofarc2015;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartApp extends ActionBarActivity {
    StartGameState gameState = StartGameState.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        // get player state buttons
        final Button p1Btn = (Button) findViewById(R.id.player_1_state_button);
        final Button p2Btn = (Button) findViewById(R.id.player_2_state_button);
        final Button p3Btn = (Button) findViewById(R.id.player_3_state_button);
        final Button p4Btn = (Button) findViewById(R.id.player_4_state_button);

        //get colour buttons
        final Button p1ColourBtn = (Button) findViewById(R.id.player_1_color_button);
        final Button p2ColourBtn = (Button) findViewById(R.id.player_2_color_button);
        final Button p3ColourBtn = (Button) findViewById(R.id.player_3_color_button);
        final Button p4ColourBtn = (Button) findViewById(R.id.player_4_color_button);

        //get start button
        final Button startBtn = (Button) findViewById(R.id.startButton);

        String playerButtonText = getResources().getString(R.string.player_button_text);

        p1Btn.setText(String.format(playerButtonText, gameState.getP1().getPlayerId(), gameState.getP1().getStateString()));
        p2Btn.setText(String.format(playerButtonText, gameState.getP2().getPlayerId(), gameState.getP2().getStateString()));
        p3Btn.setText(String.format(playerButtonText, gameState.getP3().getPlayerId(), gameState.getP3().getStateString()));
        p4Btn.setText(String.format(playerButtonText, gameState.getP4().getPlayerId(), gameState.getP4().getStateString()));


        p1Btn.setOnClickListener(new PlayerStateOnClickListener(gameState.getP1(), p1Btn, playerButtonText));
        p2Btn.setOnClickListener(new PlayerStateOnClickListener(gameState.getP2(), p2Btn, playerButtonText));
        p3Btn.setOnClickListener(new PlayerStateOnClickListener(gameState.getP3(), p3Btn, playerButtonText));
        p4Btn.setOnClickListener(new PlayerStateOnClickListener(gameState.getP4(), p4Btn, playerButtonText));

        p1ColourBtn.setBackgroundColor(gameState.getP1().getColourCode());
        p2ColourBtn.setBackgroundColor(gameState.getP2().getColourCode());
        p3ColourBtn.setBackgroundColor(gameState.getP3().getColourCode());
        p4ColourBtn.setBackgroundColor(gameState.getP4().getColourCode());

        p1ColourBtn.setOnClickListener(new PlayerColourOnClickListener(gameState.getP1(), p1ColourBtn));
        p2ColourBtn.setOnClickListener(new PlayerColourOnClickListener(gameState.getP2(), p2ColourBtn));
        p3ColourBtn.setOnClickListener(new PlayerColourOnClickListener(gameState.getP3(), p3ColourBtn));
        p4ColourBtn.setOnClickListener(new PlayerColourOnClickListener(gameState.getP4(), p4ColourBtn));

        startBtn.setOnClickListener(new StartButtonOnClickListener(this, gameState, startBtn));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_app, menu);
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
}

/**
 * Player Type button OnClick event listener
 */
class PlayerStateOnClickListener implements View.OnClickListener {

    private PlayerState state;
    private Button button;
    private String buttonText;

    public PlayerStateOnClickListener(PlayerState state, Button button, String buttonText) {
        this.state = state;
        this.button = button;
        this.buttonText = buttonText;
    }

    @Override
    public void onClick(View v) {
        state.setNextState();
        button.setText(String.format(buttonText, state.getPlayerId(),
                state.getStateString()));
    }
}

/**
 * Player Colour button onclick event listener
 */
class PlayerColourOnClickListener implements View.OnClickListener {

    private PlayerState state;
    private Button button;

    public PlayerColourOnClickListener(PlayerState state, Button button) {
        this.state = state;
        this.button = button;
    }

    @Override
    public void onClick(View v) {
        state.setNextColour();
        button.setBackgroundColor(state.getColourCode());
    }

}

class StartButtonOnClickListener implements View.OnClickListener {

    private StartGameState state;
    private Context c;
    private Button button;

    public StartButtonOnClickListener(Activity c,StartGameState gameState, Button button) {
        this.state = gameState;
        this.c = c;
        this.button = button;
    }

    @Override
    public void onClick(View v) {
        if(state.isValid()){
            Intent i = new Intent(c.getApplicationContext(), GameActivity.class);
            c.startActivity(i, ActivityOptions.makeScaleUpAnimation(v, 0, 0, button.getWidth(), button.getHeight()).toBundle());
        } else {
            new AlertDialog.Builder(c)
                    .setTitle(c.getString(R.string.invalid_state_title))
                    .setMessage(c.getString(R.string.invalid_state_text))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
