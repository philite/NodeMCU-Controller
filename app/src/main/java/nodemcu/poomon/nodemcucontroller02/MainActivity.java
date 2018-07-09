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

public class MainActivity extends AppCompatActivity {

    GPIO pin0 = new GPIO(0);
    GPIO pin1 = new GPIO(1);
    GPIO pin2 = new GPIO(2);
    GPIO pin3 = new GPIO(3);
    GPIO pin4 = new GPIO(4);
    GPIO pin5 = new GPIO(5);
    GPIO pin6 = new GPIO(6);
    GPIO pin7 = new GPIO(7);

    String toggleURL, stateURL;
    String nameD0, nameD1, nameD2, nameD3, nameD4, nameD5, nameD6, nameD7, nameServer;
    boolean actualState;
    int exceptionDialog = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAppOpen();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

    }

    public void requestHTTP(String url, final TextView textView, final ToggleButton toggleButton){

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
                                overrideToggle(toggleButton);
                                textView.setText("Status: ON");
                                exceptionDialog = 0;
                            } else {
                                actualState = false;
                                overrideToggle(toggleButton);
                                textView.setText("Status: OFF");
                                exceptionDialog = 0;
                            }
                        }
                        catch (NullPointerException Null){
                            if (exceptionDialog < 1) {
                                exceptionDialog();
                                exceptionDialog += 1;
                            }
                        }
                    }
                });
    }

    private void exceptionDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("คำเตือน Warning");
        alertDialog.setMessage("กรุณารอสักครู่ server อาจจะยังไม่ตอบสนอง หรือลองตรวจดูว่า server ทำงานถูกต้องอยู่หรือไม่ หรือที่อยู่ server ไม่ถูกต้อง");
        alertDialog.show();
    }

    public void setServer(View view){
        final TextView textServer = (TextView) findViewById(R.id.textServer);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยน URL ของ server");
        dialogbox.setMessage("ปกติจะอยู่ที่ 192.168.1.16");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameServer = input.getText().toString();
                if (nameServer.length() > 0){
                    pin0.setUrlServer(nameServer);
                    pin1.setUrlServer(nameServer);
                    pin2.setUrlServer(nameServer);
                    pin3.setUrlServer(nameServer);
                    pin4.setUrlServer(nameServer);
                    pin5.setUrlServer(nameServer);
                    pin6.setUrlServer(nameServer);
                    pin7.setUrlServer(nameServer);
                    textServer.setText(nameServer);
                    onAppOpen();
                }
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

    public void setNameD0(View view){
        final TextView textNameD0 = (TextView) findViewById(R.id.textNameD0);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 1 (Pin D0)");
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

    public void setNameD1(View view){
        final TextView textNameD1 = (TextView) findViewById(R.id.textNameD1);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 2 (Pin D1)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD1 = input.getText().toString();
                textNameD1.setText(nameD1);
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

    public void setNameD2(View view){
        final TextView textNameD2 = (TextView) findViewById(R.id.textNameD2);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 3 (Pin D2)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD2 = input.getText().toString();
                textNameD2.setText(nameD2);
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

    public void setNameD3(View view){
        final TextView textNameD3 = (TextView) findViewById(R.id.textNameD3);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 4 (Pin D3)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD3 = input.getText().toString();
                textNameD3.setText(nameD3);
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

    public void setNameD4(View view){
        final TextView textNameD4 = (TextView) findViewById(R.id.textNameD4);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 5 (Pin D4)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD4 = input.getText().toString();
                textNameD4.setText(nameD4);
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

    public void setNameD5(View view){
        final TextView textNameD5 = (TextView) findViewById(R.id.textNameD5);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 6 (Pin D5)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD5 = input.getText().toString();
                textNameD5.setText(nameD5);
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

    public void setNameD6(View view){
        final TextView textNameD6 = (TextView) findViewById(R.id.textNameD6);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 7 (Pin D6)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD6 = input.getText().toString();
                textNameD6.setText(nameD6);
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

    public void setNameD7(View view){
        final TextView textNameD7 = (TextView) findViewById(R.id.textNameD7);
        AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
        dialogbox.setTitle("เปลี่ยนชื่ออุปกรณ์ที่ 8 (Pin D7)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogbox.setView(input);
        dialogbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameD7 = input.getText().toString();
                textNameD7.setText(nameD7);
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

    public void onAppOpen(){
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        final TextView textStatusD1 = (TextView) findViewById(R.id.textStatusD1);
        final TextView textStatusD2 = (TextView) findViewById(R.id.textStatusD2);
        final TextView textStatusD3 = (TextView) findViewById(R.id.textStatusD3);
        final TextView textStatusD4 = (TextView) findViewById(R.id.textStatusD4);
        final TextView textStatusD5 = (TextView) findViewById(R.id.textStatusD5);
        final TextView textStatusD6 = (TextView) findViewById(R.id.textStatusD6);
        final TextView textStatusD7 = (TextView) findViewById(R.id.textStatusD7);

        final ToggleButton toggleD0 = (ToggleButton) findViewById(R.id.toggleD0);
        final ToggleButton toggleD1 = (ToggleButton) findViewById(R.id.toggleD1);
        final ToggleButton toggleD2 = (ToggleButton) findViewById(R.id.toggleD2);
        final ToggleButton toggleD3 = (ToggleButton) findViewById(R.id.toggleD3);
        final ToggleButton toggleD4 = (ToggleButton) findViewById(R.id.toggleD4);
        final ToggleButton toggleD5 = (ToggleButton) findViewById(R.id.toggleD5);
        final ToggleButton toggleD6 = (ToggleButton) findViewById(R.id.toggleD6);
        final ToggleButton toggleD7 = (ToggleButton) findViewById(R.id.toggleD7);

        requestHTTP(pin0.getStateURL(), textStatusD0, toggleD0);
        requestHTTP(pin1.getStateURL(), textStatusD1, toggleD1);
        requestHTTP(pin2.getStateURL(), textStatusD2, toggleD2);
        requestHTTP(pin3.getStateURL(), textStatusD3, toggleD3);
        requestHTTP(pin4.getStateURL(), textStatusD4, toggleD4);
        requestHTTP(pin5.getStateURL(), textStatusD5, toggleD5);
        requestHTTP(pin6.getStateURL(), textStatusD6, toggleD6);
        requestHTTP(pin7.getStateURL(), textStatusD7, toggleD7);
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
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD0, toggleD0);
            }
        });
    }

    public void onToggleD1(View view) {
        final TextView textStatusD1 = (TextView) findViewById(R.id.textStatusD1);
        final ToggleButton toggleD1 = (ToggleButton) findViewById(R.id.toggleD1);
        toggleD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD1.isChecked()) {
                    toggleURL = pin1.getOnURL();
                } else {
                    toggleURL = pin1.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD1, toggleD1);
            }
        });
    }

    public void onToggleD2(View view) {
        final TextView textStatusD2 = (TextView) findViewById(R.id.textStatusD2);
        final ToggleButton toggleD2 = (ToggleButton) findViewById(R.id.toggleD2);
        toggleD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD2.isChecked()) {
                    toggleURL = pin2.getOnURL();
                } else {
                    toggleURL = pin2.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD2, toggleD2);
            }
        });
    }

    public void onToggleD3(View view) {
        final TextView textStatusD3 = (TextView) findViewById(R.id.textStatusD3);
        final ToggleButton toggleD3 = (ToggleButton) findViewById(R.id.toggleD3);
        toggleD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD3.isChecked()) {
                    toggleURL = pin3.getOnURL();
                } else {
                    toggleURL = pin3.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD3, toggleD3);
            }
        });
    }

    public void onToggleD4(View view) {
        final TextView textStatusD4 = (TextView) findViewById(R.id.textStatusD4);
        final ToggleButton toggleD4 = (ToggleButton) findViewById(R.id.toggleD4);
        toggleD4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD4.isChecked()) {
                    toggleURL = pin4.getOnURL();
                } else {
                    toggleURL = pin4.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD4, toggleD4);
            }
        });
    }

    public void onToggleD5(View view) {
        final TextView textStatusD5 = (TextView) findViewById(R.id.textStatusD5);
        final ToggleButton toggleD5 = (ToggleButton) findViewById(R.id.toggleD5);
        toggleD5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD5.isChecked()) {
                    toggleURL = pin5.getOnURL();
                } else {
                    toggleURL = pin5.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD5, toggleD5);
            }
        });
    }

    public void onToggleD6(View view) {
        final TextView textStatusD6 = (TextView) findViewById(R.id.textStatusD6);
        final ToggleButton toggleD6 = (ToggleButton) findViewById(R.id.toggleD6);
        toggleD6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD6.isChecked()) {
                    toggleURL = pin6.getOnURL();
                } else {
                    toggleURL = pin6.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD6, toggleD6);
            }
        });
    }

    public void onToggleD7(View view) {
        final TextView textStatusD7 = (TextView) findViewById(R.id.textStatusD7);
        final ToggleButton toggleD7 = (ToggleButton) findViewById(R.id.toggleD7);
        toggleD7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // maybe add if of override or control with actualState here or add buttonChecked and make it here
                if (toggleD7.isChecked()) {
                    toggleURL = pin7.getOnURL();
                } else {
                    toggleURL = pin7.getOffURL();
                }
                exceptionDialog = 0;
                requestHTTP(toggleURL, textStatusD7, toggleD7);
            }
        });
    }

    public void overrideToggle(ToggleButton toggleButton){
        if (actualState == false && toggleButton.isChecked()){
            toggleButton.setChecked(true);
        }
        else if(actualState == true && !toggleButton.isChecked()) {
            toggleButton.setChecked(false);
        }
    }

    public void fetch(View view){
        exceptionDialog = 0;
        final TextView textStatusD0 = (TextView) findViewById(R.id.textStatusD0);
        final TextView textStatusD1 = (TextView) findViewById(R.id.textStatusD1);
        final TextView textStatusD2 = (TextView) findViewById(R.id.textStatusD2);
        final TextView textStatusD3 = (TextView) findViewById(R.id.textStatusD3);
        final TextView textStatusD4 = (TextView) findViewById(R.id.textStatusD4);
        final TextView textStatusD5 = (TextView) findViewById(R.id.textStatusD5);
        final TextView textStatusD6 = (TextView) findViewById(R.id.textStatusD6);
        final TextView textStatusD7 = (TextView) findViewById(R.id.textStatusD7);

        final ToggleButton toggleD0 = (ToggleButton) findViewById(R.id.toggleD0);
        final ToggleButton toggleD1 = (ToggleButton) findViewById(R.id.toggleD1);
        final ToggleButton toggleD2 = (ToggleButton) findViewById(R.id.toggleD2);
        final ToggleButton toggleD3 = (ToggleButton) findViewById(R.id.toggleD3);
        final ToggleButton toggleD4 = (ToggleButton) findViewById(R.id.toggleD4);
        final ToggleButton toggleD5 = (ToggleButton) findViewById(R.id.toggleD5);
        final ToggleButton toggleD6 = (ToggleButton) findViewById(R.id.toggleD6);
        final ToggleButton toggleD7 = (ToggleButton) findViewById(R.id.toggleD7);

        requestHTTP(pin0.getStateURL(), textStatusD0, toggleD0);
        requestHTTP(pin1.getStateURL(), textStatusD1, toggleD1);
        requestHTTP(pin2.getStateURL(), textStatusD2, toggleD2);
        requestHTTP(pin3.getStateURL(), textStatusD3, toggleD3);
        requestHTTP(pin4.getStateURL(), textStatusD4, toggleD4);
        requestHTTP(pin5.getStateURL(), textStatusD5, toggleD5);
        requestHTTP(pin6.getStateURL(), textStatusD6, toggleD6);
        requestHTTP(pin7.getStateURL(), textStatusD7, toggleD7);

    }


}
