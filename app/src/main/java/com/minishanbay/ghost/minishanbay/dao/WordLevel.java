package com.minishanbay.ghost.minishanbay.dao;

import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import com.minishanbay.ghost.minishanbay.R;
import com.minishanbay.ghost.minishanbay.entity.Word;
import org.apache.http.util.EncodingUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ghost on 2016/3/26.
 */
public class WordLevel {

    public static List<Word> getAllWordLevel(Resources resources) throws IOException {
        List<Word> words = new ArrayList<>();
        String result;
        String resString[];
        InputStream inputStream = resources.openRawResource(R.raw.nce4_words);
        int length = inputStream.available();
        byte[] buffer = new byte[length];
        inputStream.read(buffer);
        result = new String(buffer,"UTF-8");
        resString = result.split("\n");

        for (String tmp: resString){
            String column[] = tmp.split("\\s+");
            int level = -1;
            if(column != null && column.length == 2){
                level = Integer.valueOf(column[column.length - 1]);
                for (int i = 0; i < column.length - 1;i++){
                    words.add(new Word(column[i],level));
                }
            }
        }
        return words;

    }

    public List<Word> getVocanbulary(Resources resources,String id) throws IOException {
        List<Word> words = new ArrayList<>();
        String result = "";
        String resString[] = null;

        String volcanbulary_file = "lesson" + id + "_word.txt";
        InputStream inputStream = resources.getAssets().open(volcanbulary_file);
        int length = inputStream.available();
        byte[] buffer = new byte[length];
        inputStream.read(buffer);
        result = new String(buffer, 0, buffer.length, "UTF-8");
        resString = result.split("\n");
        for (String tmp : resString) {
            String column[] = tmp.split(" ");
            String explanation;
            if (column != null && column.length >= 2) {
                explanation = column[1];
                for(int i = 2;i<column.length;i++){
                    explanation += column[i];
                }
                words.add(new Word(column[0],explanation));
            }
        }
        return words;
    }

}
