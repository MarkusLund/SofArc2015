package no.ntnu.sa2015.sofarc2015;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.util.Linkify;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by DagInge on 4/14/2015.
 */
public class HelpView extends View{
    private AlertDialog dialog;

    public HelpView(Context context, int screenWidth, int screenHeight){
        super(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.help_title);

        LinearLayout linLayout = new LinearLayout(context);
        linLayout.setPadding(screenWidth/30, screenHeight/50, screenWidth/30, 0);

        TextView helpText = new TextView(context);
        helpText.setText(R.string.help_text);
        Linkify.addLinks(helpText, Linkify.WEB_URLS);
        linLayout.addView(helpText);

        builder.setView(linLayout);


        builder.setNegativeButton(R.string.help_close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        dialog = builder.create();


    }

    public void show(){
        dialog.show();
    }
}
