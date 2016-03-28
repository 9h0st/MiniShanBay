package com.minishanbay.ghost.minishanbay.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.minishanbay.ghost.minishanbay.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ghost on 2016/3/28.
 */
public class ClickSpan {
    private static boolean isTouched = false;
    private static int location_start;
    private static int location_end;
//
//    public static Context context = null;

    public static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return indices.toArray(new Integer[0]);
    }


    public static void getEachWord(TextView textView) {
        Log.i("main", "getEachWord .....");
        Spannable spans = (Spannable) textView.getText();
        Integer[] indices = getIndices(spans.toString().trim() + " ", ' ');
        // 单词开始点
        int start = 0;
        // 单词结束点
        int end = 0;
        for (int i = 0; i < indices.length; i++) {
            end = (i < indices.length ? indices[i] : spans.length());
//			Log.i(tag, "start:" + start + " end:" + end);
            ClickableSpan clickSpan = getClickableSpan(spans, start, end,
                    spans.toString().length());
            // to cater last/only word
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
         textView.setHighlightColor(Color.TRANSPARENT);
//        return spans;
    }

    public static Spannable getEachWord(String text, String target) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        Integer[] indices = getIndices(text.trim() + " ", ' ');
        // 单词开始点
        int start = 0;
        // 单词结束点
        int end = 0;
        for (int i = 0; i < indices.length; i++) {
            end = (i < indices.length ? indices[i] : spannable.length());
//			Log.i(tag, "start:" + start + " end:" + end);
            ClickableSpan clickSpan = getClickableSpan((SpannableStringBuilder) spannable, start, end,
                    text.length());
            // to cater last/only word
            spannable.setSpan(clickSpan, start, end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            spannable.setSpan(new RounderBackgroundSpan(), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    private static ClickableSpan getClickableSpan(final Spannable spans, final int start,
                                                  final int end, final int total) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                if (start != location_start && start != 1 && end != total) {

                    spans.setSpan(new RounderBackgroundSpan(true), location_start, location_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new RounderBackgroundSpan(), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                    isTouched = true;
                    location_start = start;
                    location_end = end;
                } else if (isTouched) {
                    Log.i("else", "inin");
                    spans.setSpan(new RounderBackgroundSpan(true), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    isTouched = false;
                }

            }


            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

}