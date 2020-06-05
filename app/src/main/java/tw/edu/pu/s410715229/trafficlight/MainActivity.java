package tw.edu.pu.s410715229.trafficlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText greenText;
    EditText yellowText;
    EditText redText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //設定全螢幕顯示
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //設定螢幕為橫式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        setContentView(R.layout.activity_main);

        greenText = findViewById(R.id.edt1);
        yellowText = findViewById(R.id.edt2);
        redText = findViewById(R.id.edt3);
    }

    public void StartGame(View v) {

        String g = greenText.getText().toString();
        String y = yellowText.getText().toString();
        String r = redText.getText().toString();

        if (g.equals("") || y.equals("") || r.equals("")) {
            Toast.makeText(this, "燈號的秒數不能為空白", Toast.LENGTH_LONG).show();
        } else if (g.equals("0") || y.equals("0") || r.equals("0")) {
            Toast.makeText(this, "燈號的秒數不能為0", Toast.LENGTH_LONG).show();
        } else {
            Intent it = new Intent();
            it.setClass(this, GameActivity.class);
            it.putExtra("green", Integer.parseInt(g));
            it.putExtra("yellow", Integer.parseInt(y));
            it.putExtra("red", Integer.parseInt(r));
            startActivity(it);
            finish();
        }
    }

            public void EndApp(View v) {
                finish();
            }
        }
