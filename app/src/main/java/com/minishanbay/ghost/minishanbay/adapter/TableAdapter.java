package com.minishanbay.ghost.minishanbay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minishanbay.ghost.minishanbay.R;
import com.minishanbay.ghost.minishanbay.entity.Word;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Ghost on 2016/3/27.
 */
public class TableAdapter extends BaseAdapter{
    private List<Word> list;
    private LayoutInflater layoutInflater;

    public TableAdapter(Context context,List<Word> list){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = (Word) this.getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.tablehead,null);
            viewHolder.text_word = (TextView) convertView.findViewById(R.id.text_word);
            viewHolder.text_explanation = (TextView) convertView.findViewById(R.id.text_explanation);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text_word.setText(word.getWord());
        viewHolder.text_explanation.setText(word.getExplanation());
        return convertView;
    }

    public static class ViewHolder{
        public TextView text_word;
        public TextView text_explanation;
    }

}
