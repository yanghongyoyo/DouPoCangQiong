package com.example.administrator.doupocangqiong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private String[] cover;
    private GridView mGridView;
    private SimpleAdapter mSimpleAdapter;
    private List dataList;

    private Context mContext;

    private Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=MainActivity.this;

        myToolBar= (Toolbar) findViewById(R.id.myToolBar);
        myToolBar.setTitleTextColor(getResources().getColor(R.color.titleColor));
        myToolBar.setTitle("斗破苍穹");
        myToolBar.setBackgroundColor(Color.parseColor("#2b2b2b"));
    }



    @Override
    protected void onStart() {
        super.onStart();
        cover=new String[]{"第一卷","第二卷","第三卷","第四卷","第五卷","第六卷","第七卷","第八卷","第九卷","第十卷","第十一卷","第十二卷","第十三卷","第十四卷","第十五卷","第十六卷","第十七卷"};

        dataList=new ArrayList();
        for(String s:cover){
            Map map=new HashMap();
            map.put("book",R.drawable.book);
            map.put("name",s);
            dataList.add(map);
        }
        mGridView= (GridView) findViewById(R.id.gridView);
        mSimpleAdapter=new SimpleAdapter(this,dataList,R.layout.cover_items,new String[]{"book","name"},new int[]{R.id.bookImg,R.id.bookName});
        mGridView.setAdapter(mSimpleAdapter);
        mGridView.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent();
            intent.putExtra("position",position);
            intent.setClass(MainActivity.this,ChapterActivity.class);
            startActivity(intent);
        }
    }

}
