package com.minishanbay.ghost.minishanbay.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import com.minishanbay.ghost.minishanbay.dao.WordLevel;
import com.minishanbay.ghost.minishanbay.entity.Word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ghost on 2016/3/28.
 */
public class TextSpan {

    public static String getTarget(Resources resources, String lesson_id){
        WordLevel wordLevel = new WordLevel();
        List<Word> words = new ArrayList<>();
        try {
            words = wordLevel.getVocanbulary(resources,lesson_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String target = words.get(0).getWord();
        for(Word iterator:words){
            target = target + "|" + iterator.getWord();
        }
        return target;
    }
    public static String getTarget(int level, List<Word> words){
        String target = words.get(0).getWord();
        for (Word iterator:words){
            if(iterator.getLevel() <= level){
                target = target + "|" +iterator.getWord();
            }
        }
        return target;
    }

    public static SpannableStringBuilder highLight(String text, String target, Context context){
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){

            span = new BackgroundColorSpan(Color.parseColor("#209e85"));
            //spannable.setSpan(span,matcher.start(),matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //spannable.setSpan(new ForegroundColorSpan(Color.WHITE),matcher.start(),matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RounderBackgroundSpan(context),matcher.start(),matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }



}
