package com.minishanbay.ghost.minishanbay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minishanbay.ghost.minishanbay.R;
import com.minishanbay.ghost.minishanbay.adapter.TableAdapter;
import com.minishanbay.ghost.minishanbay.dao.LessonInfo;
import com.minishanbay.ghost.minishanbay.dao.WordLevel;
import com.minishanbay.ghost.minishanbay.entity.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ghost on 2016/3/24.
 */
public class WordListActivity extends AppCompatActivity{
    private WordLevel wordLevel;
    private ListView tableListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablelayout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras().isEmpty()? new Bundle():intent.getExtras();
        int id = bundle.getInt("lesson_id");
        String title =  bundle.get("lesson_title") == null ? "Lesson Fault": (String) bundle.get("lesson_title");
        this.setTitle(title);
        Log.i("title",title);
        Log.i("id",id+"");
        wordLevel = new WordLevel();
        ViewGroup tablehead = (ViewGroup) findViewById(R.id.list_title);
        tablehead.setBackgroundColor(Color.parseColor("#209e85"));
        List<Word> list = new ArrayList<>();
        try {
            list = wordLevel.getVocanbulary(getApplicationContext().getResources(),String.valueOf(id+1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.i("list",list.toString());
        tableListView = (ListView) findViewById(R.id.list);
        TableAdapter adapter = new TableAdapter(this,list);
        tableListView.setAdapter(adapter);
    }

}
