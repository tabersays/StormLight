package com.taberstorm.taberstorch;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class FlashLight extends Activity {
    boolean hasCam;
    Camera camera;
    Camera.Parameters camParams;
    boolean isOn;
    TextView text;
    Button power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        try
        {
            Log.d("TORCH", "Check cam");
            camera = Camera.open();
            camParams = camera.getParameters();
            camera.startPreview();
            hasCam = true;
            Toast.makeText(getApplicationContext(), "Ready!", Toast.LENGTH_SHORT).show();


        }
        catch (Throwable t)
        {
            t.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error with camera", Toast.LENGTH_LONG).show();
            hasCam = false;
        }
        isOn = false;
        power = (Button) findViewById(R.id.toggleBut);
        text = (TextView) findViewById(R.id.powerState);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasCam) {
                    if (!isOn) {
                        isOn = true;
                        power.setBackgroundResource(R.drawable.onbut);
                        text.setText(R.string.power_on);
                    } else {
                        isOn = false;
                        power.setBackgroundResource(R.drawable.offbut);
                        text.setText(R.string.power_off);
                    }
                    turnOnOff(isOn);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Camera found", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flash_light, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void turnOnOff(boolean on)
    {
        if(on)
        {
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        if(!on)
        {
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        camera.setParameters(camParams);
        camera.startPreview();
    }
}
