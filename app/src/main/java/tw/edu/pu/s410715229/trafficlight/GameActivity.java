package tw.edu.pu.s410715229.trafficlight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements DialogInterface.OnCancelListener {

    GameSurfaceView GameSV;
    Handler handler;
    Handler lightTimer;
    Toast toast;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //設定全螢幕顯示
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //設定螢幕為橫式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        setContentView(R.layout.activity_game);

        GameSV = (GameSurfaceView) findViewById(R.id.GameSV);
        //設定初始測試之燈號秒數
        GameSV.SetLightSec(6, 2, 3);

        handler = new Handler();
        lightTimer = new Handler();

        lightTimer.postDelayed(lightTransfer, 2000);
        toast = Toast.makeText(this, "還敢闖紅燈啊?!", Toast.LENGTH_LONG);


        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("闖紅燈哦 三寶 ");
        alertDialog.setIcon(R.drawable.ic_launcher_background);
        alertDialog.setPositiveButton("結束系統", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }

        });
        alertDialog.setNegativeButton("再玩一次 ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                GameSV.step = 1;
                GameSV.SetLightSec(6, 2, 3);
                lightTimer.post(lightTransfer);

            }

        });
    }

    //利用手指觸控，控制小男孩走路
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            GameSV.BoyMoving = true;
            handler.post(runnable);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            GameSV.BoyMoving = false;
            handler.removeCallbacks(runnable);  //銷毀執行緒
        }
        return true;
    }

    //處理小男孩走路
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = GameSV.getHolder().lockCanvas();
            GameSV.drawSomething(canvas);
            GameSV.getHolder().unlockCanvasAndPost(canvas);

            if (GameSV.nowLight == 3) {
                lightTimer.removeCallbacks(lightTransfer);//撤銷執行續
                toast.show();

                alertDialog.setMessage("您此次成績是" + String.valueOf(GameSV.step) + "分，您不可以闖紅燈啦");
                alertDialog.show();
            }

            handler.postDelayed(runnable, 50);
        }
    };

    Runnable lightTransfer = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = GameSV.getHolder().lockCanvas();
            GameSV.updateLight(canvas);
            GameSV.getHolder().unlockCanvasAndPost(canvas);
            lightTimer.postDelayed(lightTransfer, 1000);
        }
    };

    @Override
    public void onCancel(DialogInterface dialog) {

    }
}