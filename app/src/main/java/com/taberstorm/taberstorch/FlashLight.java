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


        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        isOn = false;
        power = (Button) findViewById(R.id.toggleBut);
        text = (TextView) findViewById(R.id.powerState);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOn == false)
                {
                    isOn = true;
                    power.setBackgroundResource(R.drawable.onbut);
                }
                else
                {
                    isOn = false;
                    power.setBackgroundResource(R.drawable.offbut);
                }
                turnOnOff(isOn);
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
            text.setText(R.string.power_on);

        }
        if(!on)
        {
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            text.setText(R.string.power_off);
        }
        camera.setParameters(camParams);
        camera.startPreview();
    }
}
