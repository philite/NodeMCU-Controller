package nodemcu.poomon.nodemcucontroller02;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    GPIO pin0 = new GPIO(0);
    String toggleURL, stateURL;
    String nameD0;
    boolean actualState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAppOpenUpdate();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }


    public void requestHTTP(String url, final TextView textView){
        final ToggleButton toggleD0 = (ToggleButton) findViewById(R.id.toggleD0);
        Ion.with(this)
                .load(url)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        // NullPointerException because Ion throws the exception when server can't be reached, in this case the server isn't properly start.
                        // Needs more research about this Ion exception
                        try {
                            if (result.getResult().indexOf("1") != -1) {
                                actualState = true;
                                overrideToggleD0(toggleD0);
                                textView.setText("Status: ON");
                            } else {
                                actualState = false;
                                overrideToggleD0(toggleD0);
                                textView.setText("Status: OFF");
                            }
                        }
                        catch (NullPointerException Null){
                            exceptionDialog();
                        }
                    }
                });
    }

    private void exceptionDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Maybe the server didn't start correctly, please restart NodeMCU");
        alertDialog.show();
    }

    public void setNameD0(View view){
        final TextView textNameD0 = (TextView) findViewById(R.id.nameD0);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("Change Pin D0 Name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD0 = input.getText().toString();
                textNameD0.setText(nameD0);
            }
        });
        dialogbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogbox.show();
    }

    public void onAppOpenUpdate(){
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        requestHTTP(pin0.getStateURL(), textStatusD0);
    }

    public void onToggleD0(View view) {
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        final ToggleButton toggleD0 = (ToggleButton) findViewById(R.id.toggleD0);
        toggleD0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD0.isChecked()) {
                    toggleURL = pin0.getOnURL();
                } else {
                    toggleURL = pin0.getOffURL();
                }
                requestHTTP(toggleURL, textStatusD0);
            }
        });
    }

    public void overrideToggleD0(ToggleButton tgBTN){
        // if real: ON, button = TURN OFF
        if (actualState == false && tgBTN.isChecked()){
            tgBTN.setChecked(false);
        }
        else if(actualState == true && !tgBTN.isChecked()) {
            tgBTN.setChecked(true);
        }
        // if real: OFF, button = TURN ON
    }

    public void fetch(View view){
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        stateURL = pin0.getStateURL();
        requestHTTP(stateURL, textStatusD0);
    }


}
