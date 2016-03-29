package com.minishanbay.ghost.minishanbay.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.minishanbay.ghost.minishanbay.R;
import com.minishanbay.ghost.minishanbay.dao.LessonInfo;
import com.minishanbay.ghost.minishanbay.dao.WordLevel;
import com.minishanbay.ghost.minishanbay.entity.Lesson;
import com.minishanbay.ghost.minishanbay.view.ClickSpan;
import com.minishanbay.ghost.minishanbay.view.SlideBar;
import com.minishanbay.ghost.minishanbay.view.TextJustification;
import com.minishanbay.ghost.minishanbay.view.TextSpan;

import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {

    private SlideBar slideBar;
    private TextView float_num;
    private TextView content_scrolling;
    private LessonInfo lessonInfo;
    private Lesson lesson;
    private View subLayout;
    private int lesson_type = 0;
    private String title;
    private boolean isHighLighted = false;
    private int lesson_id = 0;
    private int level = 0;
    private TextView ChildTitle;
    static Point size;
    static float density;
    private String childtitle_text;
    private int measuredWidth;
    private boolean isAdd = false;
    private boolean isSlideTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置fab按钮的动作
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHighLighted == false && lesson_type == 0) {
                    String target = TextSpan.getTarget(getApplicationContext().getResources(), String.valueOf(lesson_id + 1));
                    SpannableStringBuilder spannable = TextSpan.highLight(content_scrolling, target);
                    content_scrolling.setText(spannable);
                    isHighLighted = true;
                } else if (lesson_type == 0 && isHighLighted == true) {
//                    SpannableStringBuilder ssb = new SpannableStringBuilder(content_scrolling.getText().toString());
//                    content_scrolling.setText(ssb, TextView.BufferType.SPANNABLE);
//                    ClickSpan.getEachWord(content_scrolling);
//                    content_scrolling.setMovementMethod(LinkMovementMethod.getInstance());
                    new WordSpanTask().execute();
                    isHighLighted = false;
                }
            }
        });

        //获取传递过来的课程信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras().isEmpty() ? new Bundle() : intent.getExtras();
        title = bundle.get("lesson_title") == null ? "Lesson Fault" : (String) bundle.get("lesson_title");
        lesson_id = bundle.getInt("lesson_id");
        lesson_type = bundle.getInt("lesson_type");
        childtitle_text = bundle.getString("lesson_childtitle");

        //设置文章标题
        this.setTitle(title);
        ChildTitle = (TextView) findViewById(R.id.childtitle);
        ChildTitle.setText(childtitle_text);
        float_num = (TextView) findViewById(R.id.float_num);

        //设置Slide-Bar
        slideBar = (SlideBar) findViewById(R.id.slidebar);
        slideBar.setSlide_text(float_num);
        slideBar.setOnTouchNumChangeListener(new SlideBar.onTouchNumListener() {
            @Override
            public void onTouchNumChanged(String s) {
                if (lesson_type == 0) {
                    level = -1;
                    isSlideTouched = true;
                    try {
                        level = Integer.parseInt(s);
                        if (level >= 0 && level <= 5) {
                            new LessonTask().execute();
                        } else {
                            return;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }

        });


        //从文件夹中获取课程信息
        subLayout = findViewById(R.id.scroll_layout);
        lessonInfo = new LessonInfo();
        try {
            lesson = lessonInfo.getLessonInfo(String.valueOf(lesson_id + 1),
                    getApplicationContext().getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
        content_scrolling = (TextView) subLayout.findViewById(R.id.content_scrolling);
//        content_scrolling.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);

        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        DisplayMetrics dm = new DisplayMetrics();
//        Log.i("waitting","waiting1");
        display.getMetrics(dm);
//        density = dm.density;
//        int width = dm.widthPixels;

        display.getSize(size);
        //添加布局监听
        ViewTreeObserver vto = content_scrolling.getViewTreeObserver();

        //显示课文内容 0为英文原文 1为中文翻译
        if (lesson_type == 0) {
            lesson.setTitle(childtitle_text);
            content_scrolling.setText(lesson.getText());

            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (isAdd == false) {
                        measuredWidth = content_scrolling.getMeasuredWidth();
                        //TextJustification.justify(content_scrolling,content_scrolling.getMeasuredWidth());
                        Log.i("width", measuredWidth + "");
                        new WordSpanTask().execute();
                        isAdd = true;
                    }
                }
            });

//            new WordSpanTask().execute();
//            Log.i("justified_text",content_scrolling.getText().toString());
//            SpannableStringBuilder ssb = new SpannableStringBuilder(content_scrolling.getText());
//
//            content_scrolling.setText(ssb, TextView.BufferType.SPANNABLE);
//            ClickSpan.getEachWord(content_scrolling);
//            content_scrolling.setMovementMethod(LinkMovementMethod.getInstance());

        } else if (lesson_type == 1) {
            lesson.setTitle_chinese(childtitle_text);
            content_scrolling.setText(lesson.getText_chinese());
        }


        content_scrolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSlideTouched == true) {
                    new WordSpanTask().execute();
                    isSlideTouched = false;
                }
            }
        });
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

        //选择同一文章的不同类别
        switch (id) {
            case R.id.action_english:
                lesson_type = 0;
                content_scrolling.setText(lesson.getText());
//                ChildTitle.setText(lesson.getTitle());
                measuredWidth = content_scrolling.getMeasuredWidth();
                new WordSpanTask().execute();
//                ViewTreeObserver vto = content_scrolling.getViewTreeObserver();
//                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        if (isAdd == false) {
//                            measuredWidth = content_scrolling.getMeasuredWidth();
//                            //TextJustification.justify(content_scrolling,content_scrolling.getMeasuredWidth());
//                            Log.i("width", measuredWidth + "");
//                            new WordSpanTask().execute();
//                            isAdd = true;
//                        }
//                    }
//                });
                return true;
            case R.id.action_chinese:
                lesson_type = 1;
//                ChildTitle.setText(lesson.getTitle_chinese());
                content_scrolling.setText(lesson.getText_chinese());
//                new WordSpanTask().execute();
                return true;
            case R.id.action_volcanbulary:
                Intent intent = new Intent();
                intent.setClass(ScrollingActivity.this, WordListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("lesson_title", title);
                bundle.putSerializable("lesson_id", lesson_type);
                bundle.putSerializable("lesson_childtitle", childtitle_text);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    class LessonTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            String target = null;
            try {
                target = TextSpan.getTarget(level, WordLevel.getAllWordLevel(getApplicationContext().getResources()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return target;
        }

        @Override
        protected void onPostExecute(String target) {
            TextJustification.justify(content_scrolling, measuredWidth);
            SpannableStringBuilder spannable = TextSpan.highLight(content_scrolling, target);
            content_scrolling.setText(spannable);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    class WordSpanTask extends AsyncTask<Void, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isDone) {
            if (isDone) {
//                Log.i("justifynow",""+isDone);
//                Log.i("measureWidth0", measuredWidth+"");
                TextJustification.justify(content_scrolling, measuredWidth);
//                Log.i("measureWidth", measuredWidth+"");
                SpannableStringBuilder ssb = new SpannableStringBuilder(content_scrolling.getText());
                content_scrolling.setText(ssb, TextView.BufferType.SPANNABLE);
                ClickSpan.getEachWord(content_scrolling);
                content_scrolling.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

}
