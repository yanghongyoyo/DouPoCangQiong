package com.example.administrator.doupocangqiong;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.media.audiofx.EnvironmentalReverb;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class ReadActivity extends Activity {

    private String content;
    private TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//禁止屏幕息屏
        setContentView(R.layout.activity_read);
        Intent intent=getIntent();
        content=intent.getStringExtra("content");
        myText= (TextView) findViewById(R.id.MyText);
        setTxt();
    }


    private void setTxt() {
        myText.setText("     "+content);
        //当用户长按的时候，可以调节字体大小和屏幕明暗
        myText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //弹出对话框
                openDialog();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //按菜单按钮也可以打开设置对话框
        openDialog();
        return true;
    }

    private void openDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("设置");
        LayoutInflater inflater=LayoutInflater.from(ReadActivity.this);
        View view=inflater.inflate(R.layout.dialog,null);

        SeekBar txtSeekBar= (SeekBar) view.findViewById(R.id.txtSeekBar);
        txtSeekBar.setOnSeekBarChangeListener(new MySeekBarListener());

        SeekBar lightSeekBar= (SeekBar) view.findViewById(R.id.lightSeekBar);
        lightSeekBar.setOnSeekBarChangeListener(new MySeekBarListener());

        dialog.setView(view);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();

    }

    class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar,  int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.txtSeekBar:{
                    setTxtVal(progress);
                }break;
                case R.id.lightSeekBar:{
                    setLightVal(progress);
                }break;
                default:break;
            }
        }
        /**
         * 设置亮度值
         */
        private void setLightVal(int progress) {

            //改变系统亮度
            Window window=ReadActivity.this.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            if (progress == -1) {
                lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
            } else {
                lp.screenBrightness = (progress <= 0 ? 1 : progress) / 255f;
            }
            window.setAttributes(lp);
        }

        /**
         * 设置字体大小
         */
        private void setTxtVal(final int progress) {
            //在UI线程中调节字体大小
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myText.setTextSize((progress+60)/4);
                }
            });
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
