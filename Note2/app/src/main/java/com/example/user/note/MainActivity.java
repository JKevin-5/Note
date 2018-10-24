package com.example.user.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import model.Notes;
import presenter.DataBaseHelper;
import presenter.MyDatabase;
import presenter.NotesAdapter;

public class MainActivity extends AppCompatActivity {

    //private List<Notes> notesList = new ArrayList<>();
    private DataBaseHelper dbHelper;
    ListView listView;
    LayoutInflater layoutInflater;
    ArrayList<Notes> notesList;
    MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        layoutInflater = getLayoutInflater();

        myDatabase = new MyDatabase(this);
        notesList = myDatabase.getarray();
        NotesAdapter adapter = new NotesAdapter(layoutInflater,notesList);
        listView.setAdapter(adapter);



        //dbHelper = new DataBaseHelper(this,"Note.db",null,1);

        //创建数据库表格
        //dbHelper.getWritableDatabase();

        //initNotes();//初始化便签数据
        //NotesAdapter adapter = new NotesAdapter(MainActivity.this,R.layout.note_item,notesList);
        //listView.setAdapter(adapter);

        //列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){//点击一次事件
               Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
               intent.putExtra("id",notesList.get(position).getId());
               startActivity(intent);
               MainActivity.this.finish();
            }
        });
        //列表长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position,long id) {
                new AlertDialog.Builder(MainActivity.this) //弹出一个对话框
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

        //toolbar设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //悬浮按钮点击事件

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                //MainActivity.this.finish();

                /*
                Snackbar.make(view, "A blank note has been created ", Snackbar.LENGTH_LONG).setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Note E has been built",Toast.LENGTH_SHORT).show();
                            }
                        }).show();*/
            }
        });
    }


    /*
    //初始化NoteList
    public void initNotes(){
        for(int i=0;i<8;i++){
            Notes A = new Notes("A","2018");
            notesList.add(A);
            Notes B = new Notes("B","2018");
            notesList.add(B);
            Notes C = new Notes("C","2018");
            notesList.add(C);
            Notes D = new Notes("D","2018");
            notesList.add(D);

        }
    }
    */
    //菜单按键
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();//要去掉这句，否则会结束当前Activity，无法起到屏蔽的作用
        //处理自己的逻辑
    }
}
