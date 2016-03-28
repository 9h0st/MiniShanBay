package com.minishanbay.ghost.minishanbay.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.athkalia.emphasis.EmphasisTextView;
import com.minishanbay.ghost.minishanbay.R;
import com.minishanbay.ghost.minishanbay.dao.LessonInfo;
import com.minishanbay.ghost.minishanbay.dao.WordLevel;
import com.minishanbay.ghost.minishanbay.entity.Lesson;
import com.minishanbay.ghost.minishanbay.view.SlideBar;
import com.minishanbay.ghost.minishanbay.view.TextSpan;

import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {

    private SlideBar slideBar;
    private TextView float_num;
    private EmphasisTextView content_scrolling;
    private LessonInfo lessonInfo;
    private Lesson lesson;
    private View subLayout;
    private int lesson_type = 0;
    private String title;
    private boolean isHighLighted = false;
    private int lesson_id = 0;
    private int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHighLighted == false && lesson_type == 0) {
                    String target = TextSpan.getTarget(getApplicationContext().getResources(), String.valueOf(lesson_id + 1));
                    SpannableStringBuilder spannable = TextSpan.highLight(lesson.getText(), target);
                    content_scrolling.setText(spannable);
                    isHighLighted = true;
                } else if (lesson_type == 0 && isHighLighted == true) {
                    content_scrolling.setText(lesson.getText());
                    isHighLighted = false;
                }
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras().isEmpty() ? new Bundle() : intent.getExtras();
        title = bundle.get("lesson_title") == null ? "Lesson Fault" : (String) bundle.get("lesson_title");
        lesson_id = bundle.getInt("lesson_id");
        lesson_type = bundle.getInt("lesson_type");
        this.setTitle(title);

        float_num = (TextView) findViewById(R.id.float_num);
        slideBar = (SlideBar) findViewById(R.id.slidebar);
        slideBar.setSlide_text(float_num);
        slideBar.setOnTouchNumChangeListener(new SlideBar.onTouchNumListener() {
            @Override
            public void onTouchNumChanged(String s) {
                level = -1;
                try {
                    level = Integer.parseInt(s);
                    if (level >= 0 && level <= 5) {
                        //                            String target = TextSpan.getTarget(level, WordLevel.getAllWordLevel(getApplicationContext().getResources()));
//                            SpannableStringBuilder spannable = TextSpan.highLight(lesson.getText(),target);
//                            content_scrolling.setText(spannable);
                        new LessonTask().execute();
                    } else {
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return;
                }

            }
        });
        subLayout = findViewById(R.id.scroll_layout);
        lessonInfo = new LessonInfo();
        try {
            lesson = lessonInfo.getLessonInfo(String.valueOf(lesson_id + 1), getApplicationContext().getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
        content_scrolling = (EmphasisTextView) subLayout.findViewById(R.id.content_scrolling);
        Log.i("lesson_type", lesson_type + "");
        //显示课文内容 0为英文原文 1为中文翻译
        if (lesson_type == 0)
            content_scrolling.setText(lesson.getText());
        else if (lesson_type == 1)
            content_scrolling.setText(lesson.getText_chinese());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_english:
                content_scrolling.setText(lesson.getText());
                return true;
            case R.id.action_chinese:
                content_scrolling.setText(lesson.getText_chinese());
                return true;
            case R.id.action_volcanbulary:
                Intent intent = new Intent();
                intent.setClass(ScrollingActivity.this, WordListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("lesson_title", title);
                bundle.putSerializable("lesson_id", lesson_type);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void show_lesson_englishView() {
//
//    }
//
//    public void show_lesson_chineseView() {
//
//    }

    class LessonTask extends AsyncTask<Integer, Integer, SpannableStringBuilder> {

        @Override
        protected SpannableStringBuilder doInBackground(Integer... params) {
            String target = null;
            try {
                target = TextSpan.getTarget(level, WordLevel.getAllWordLevel(getApplicationContext().getResources()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            SpannableStringBuilder spannable = TextSpan.highLight(lesson.getText(), target);
            return spannable;
        }

        @Override
        protected void onPostExecute(SpannableStringBuilder s) {
            content_scrolling.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
