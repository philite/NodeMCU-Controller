package nodemcu.poomon.nodemcucontroller02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class MainActivity extends AppCompatActivity {

    GPIO pin0 = new GPIO(0);

    String toggleURL, stateURL, messageForUser, rawMessage;
    boolean actualState, buttonChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAppOpenUpdate();
    }

    public void requestHTTP(String url){
        Ion.with(this)
                .load(url)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        // maybe split this to a new method to make it modular
                        rawMessage = result.getResult();
                        statusMessage(rawMessage);
                    }
                });
    }

    public void onAppOpenUpdate(){
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        requestHTTP(pin0.getStateURL());
    }

    public String statusMessage(String rawMessage){
        if (rawMessage.indexOf("1") != -1){
            this.overrideToggleD0(true);
            return "Status: ON";
        }
        else {
            this.overrideToggleD0(false);
            return "Status: OFF";
        }
    }

    public void onToggleD0(View view) {
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        final ToggleButton toggleD0 = (ToggleButton) findViewById(R.id.toggleD0);
        toggleD0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleD0.isChecked()) {
                    buttonChecked = true;
                    toggleURL = pin0.getOnURL();
                } else {
                    buttonChecked = false;
                    toggleURL = pin0.getOffURL();
                }
                requestHTTP(toggleURL);
            }
        });
    }

    public void overrideToggleD0(boolean actualState, ToggleButton tgBTN, boolean buttonChecked){
        // if real: ON, button = TURN OFF
        if (actualState == true && buttonChecked){
            tgBTN.setChecked(false);
        }
        else if(actualState == false && buttonChecked) {
            tgBTN.setChecked(true);
        }
        // if real: OFF, button = TURN ON
    }

    public void fetch0(View view){
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        stateURL = pin0.getStateURL();
        requestHTTP(stateURL);
    }


}
