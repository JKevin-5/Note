package com.example.user.note;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import model.Notes;
import presenter.MyDatabase;

public class Main2Activity extends AppCompatActivity {

    EditText note;
    EditText title;
    TextView time_content;
    MyDatabase myDatabase;
    Notes notes;
    int id;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm");//编辑便签的时间，格式化
        Date date = new Date(System.currentTimeMillis());

        title=(EditText)findViewById(R.id.title);
        note = (EditText)findViewById(R.id.note);
        time_content = (TextView)findViewById(R.id.time_content);
        time_content.setText(simpleDateFormat.format(date));
        //使得文本框可以实现滚动
        note.setMovementMethod(ScrollingMovementMethod.getInstance());

        //toolbar设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //显示HomeAsUp返回键
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDatabase = new MyDatabase(this);
        Intent intent = this.getIntent();
        id = intent.getIntExtra("id",0);
        Log.v("Tag",id+"");
        if(id != 0){
            notes = myDatabase.getTiandCon(id);
            title.setText(notes.getTitle());
            note.setText(notes.getContent());
        }

        //悬浮按钮点击事件
        fab = (FloatingActionButton) findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                        isSave();
                        Log.v("Tag","save");

            }
        });
    }

    @Override
    public void onBackPressed(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm");//编辑便签的时间，格式化
        Date date = new Date(System.currentTimeMillis());
        String note_time = simpleDateFormat.format(date);
        String note_title = title.getText().toString();
        String note_content = note.getText().toString();
        if(id!=0){
            notes = new Notes(note_title,id,note_content,note_time);
            myDatabase.toUpdate(notes);
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }
        //新建便签
        else {
            notes = new Notes(note_title,note_content,note_time);
            myDatabase.toInsert(notes);
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }
    }

    public void isSave(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm");//编辑便签的时间，格式化
        Date date = new Date(System.currentTimeMillis());
        String note_time = simpleDateFormat.format(date);
        String note_title = title.getText().toString();
        String note_content = note.getText().toString();

        if (id!=0){
            notes = new Notes(note_title,id,note_content,note_time);
            myDatabase.toUpdate(notes);
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }
        //新建便签
        else {
            notes = new Notes(note_title,note_content,note_time);
            myDatabase.toInsert(notes);
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            Main2Activity.this.finish();
        }
    }

    /*public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //对于up按钮的相应如下
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
