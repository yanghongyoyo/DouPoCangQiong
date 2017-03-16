package com.example.administrator.doupocangqiong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterActivity extends Activity {

    private ListView mChapListView;
    private List chapList;
    private SimpleAdapter mSimpleAdapter;

    private Toolbar myToolBar;

    //传输过来点击了哪一部
    private int cPosition;
    private String [] chapter;//章节分割数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        cPosition=getIntent().getIntExtra("position",0);
        mChapListView= (ListView) findViewById(R.id.chapterList);

        myToolBar= (Toolbar) findViewById(R.id.myToolBar);
        myToolBar.setTitleTextColor(getResources().getColor(R.color.titleColor));
        myToolBar.setTitle("斗破苍穹");
        myToolBar.setNavigationIcon(R.drawable.goback);
        myToolBar.setBackgroundColor(Color.parseColor("#2b2b2b"));
        myToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取点击章节所有内容
        String text=myGetText(cPosition);
        //分割各个章节
        chapter=text.split("\\^\\^");
        setLiteView();
    }

    /**
     * 设置章节ListView
     */
    private void setLiteView() {
        chapList=new ArrayList();
        for(int i=1;i<chapter.length+1;i++){
            Map map=new HashMap();
            map.put("CName","第"+i+"节");
            chapList.add(map);
        }
        mSimpleAdapter=new SimpleAdapter(this,chapList,R.layout.chapter_items,new String[]{"CName"},new int[]{R.id.cName});
        mChapListView.setAdapter(mSimpleAdapter);
        mChapListView.setOnItemClickListener(new MyOnItemClickListener());
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent();
            intent.putExtra("content",chapter[position]);//把章节内容传输过去
            intent.setClass(ChapterActivity.this,ReadActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 加载哪一部电子书
     */
    private String myGetText(int position) {
        String s;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader bf;
        StringBuffer sb=new StringBuffer("");
        try {
            switch (position){
                case 0:
                    is=getResources().openRawResource(R.raw.d1);
                    break;
                case 1:
                    is=getResources().openRawResource(R.raw.d2);
                    break;
                case 2:
                    is=getResources().openRawResource(R.raw.d3);
                    break;
                case 3:
                    is=getResources().openRawResource(R.raw.d4);
                    break;
                case 4:
                    is=getResources().openRawResource(R.raw.d5);
                    break;
                case 5:
                    is=getResources().openRawResource(R.raw.d6);
                    break;
                case 6:
                    is=getResources().openRawResource(R.raw.d7);
                    break;
                case 7:
                    is=getResources().openRawResource(R.raw.d8);
                    break;
                case 8:
                    is=getResources().openRawResource(R.raw.d9);
                    break;
                case 9:
                    is=getResources().openRawResource(R.raw.d10);
                    break;
                case 10:
                    is=getResources().openRawResource(R.raw.d11);
                    break;
                case 11:
                    is=getResources().openRawResource(R.raw.d12);
                    break;
                case 12:
                    is=getResources().openRawResource(R.raw.d13);
                    break;
                case 13:
                    is=getResources().openRawResource(R.raw.d14);
                    break;
                case 14:
                    is=getResources().openRawResource(R.raw.d15);
                    break;
                case 15:
                    is=getResources().openRawResource(R.raw.d16);
                    break;
                case 16:
                    is=getResources().openRawResource(R.raw.d17);
                    break;
                default:
                    break;
            }
            isr=new InputStreamReader(is,"gbk");
            bf =new BufferedReader(isr);

            try {
                while ((s=bf.readLine())!=null){
                    sb.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().toString(),"处理流异常！"+e.toString());
        }finally {
            try {
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(getClass().toString(),"关闭流异常！"+e.toString());
            }
        }


        return sb.toString();
    }
}
