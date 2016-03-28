package com.minishanbay.ghost.minishanbay.dao;

import android.content.res.Resources;
import android.util.Log;

import com.minishanbay.ghost.minishanbay.entity.Lesson;
import org.apache.http.util.EncodingUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ghost on 2016/3/26.
 */
public class LessonInfo {
    public Lesson getLessonInfo(String id, Resources resources) throws IOException {
        Lesson lesson = new Lesson();
        lesson.setId(id);

        String lessonfile_text = "lesson"+id+".txt";
        String lessonfile_chinese = "lesson"+id+"_chinese.txt";

        InputStream inputStream = resources.getAssets().open(lessonfile_text);
        int length = inputStream.available();
        byte[] buffer = new byte[length];
        inputStream.read(buffer);
        String english_text = new String(buffer,"UTF-8");
        lesson.setText(english_text);


        inputStream.close();
        InputStream inputStream2 = resources.getAssets().open(lessonfile_chinese);
        length = inputStream2.available();
        byte[] buffer2 = new byte[length];
        inputStream2.read(buffer2);
        String chinese_text = new String(buffer2,"UTF-8");
        lesson.setText_chinese(chinese_text);
        inputStream2.close();
        return lesson;
    }
}
