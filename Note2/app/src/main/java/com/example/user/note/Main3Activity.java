package com.example.user.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import model.Notes;
import presenter.DataBaseHelper;
import presenter.MyDatabase;
import presenter.NotesAdapter;

public class Main3Activity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    ListView listView;
    LayoutInflater layoutInflater;
    ArrayList<Notes> notesList;
    MyDatabase myDatabase;
    EditText editText;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //toolbar设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //声明listView
        listView = (ListView) findViewById(R.id.list_view);
        //声明悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        layoutInflater = getLayoutInflater();
        //声明搜索框
        editText = (EditText) findViewById(R.id.search);
        //声明搜索按钮
        button = (ImageButton) findViewById(R.id.search_button);
        button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Intent intent = getIntent();
        String s = intent.getStringExtra("extra_data");
        Log.v("Tag",s);
        editText.setText(s);

        myDatabase = new MyDatabase(this);
        notesList = myDatabase.getSearch(s);
        NotesAdapter adapter = new NotesAdapter(layoutInflater,notesList);
        listView.setAdapter(adapter);

        //列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){//点击一次事件
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("id",notesList.get(position).getId());
                startActivity(intent);
                Main3Activity.this.finish();
            }
        });
        //列表长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position,long id) {
                new AlertDialog.Builder(Main3Activity.this) //弹出一个对话框
                        //.setTitle("确定要删除此便签？")
                        .setMessage("确定要删除此便签？")
                        .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDatabase.toDelete(notesList.get(position).getId());
                                NotesAdapter myAdapter = new NotesAdapter(layoutInflater,notesList);
                                listView.setAdapter(myAdapter);
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .create()
                        .show();
                return true;
            }
        });

        //悬浮按钮点击事件(返回主界面)

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main3Activity.this,MainActivity.class);
                startActivity(intent);
                Main3Activity.this.finish();

                /*
                Snackbar.make(view, "A blank note has been created ", Snackbar.LENGTH_LONG).setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Note E has been built",Toast.LENGTH_SHORT).show();
                            }
                        }).show();*/
            }
        });
        //搜索按钮点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s;
                s = editText.getText().toString();
                Intent intent = new Intent(Main3Activity.this,Main3Activity.class);
                intent.putExtra("extra_data",s);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();//要去掉这句，否则会结束当前Activity，无法起到屏蔽的作用
        //处理自己的逻辑
    }
}
