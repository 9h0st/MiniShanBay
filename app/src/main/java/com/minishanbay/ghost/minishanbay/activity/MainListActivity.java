package com.minishanbay.ghost.minishanbay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.minishanbay.ghost.minishanbay.R;
import com.minishanbay.ghost.minishanbay.entity.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ghost on 2016/3/24.
 */
public class MainListActivity extends AppCompatActivity {
    private ExpandableListView listView = null;
    private List<String> mparent = null;
    Map<String,List<String>> map = null;

    private List<Lesson> lessons = new ArrayList<Lesson>();

    public void initData() {

        mparent = new ArrayList<String>();
        mparent.add("Lesson1");
        mparent.add("Lesson2");
        mparent.add("Lesson3");
        mparent.add("Lesson4");

        map = new HashMap<String, List<String>>();
        List<String> list1 = new ArrayList<String>();
        list1.add("Finding fossil man");
        list1.add("发现化石人");
        list1.add("New words and expressions");
        map.put("Lesson1",list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("Spare that spider");
        list2.add("不要伤害蜘蛛");
        list2.add("New words and expressions");
        map.put("Lesson2",list2);

        List<String> list3 = new ArrayList<String>();
        list3.add("Matterhorn man");
        list3.add("马特霍恩山区人");
        list3.add("New words and expressions");
        map.put("Lesson3",list3);

        List<String> list4 = new ArrayList<String>();
        list4.add("Seeing hands");
        list4.add("能看见东西的手");
        list4.add("New words and expressions");
        map.put("Lesson4",list4);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listlayout);

        listView = (ExpandableListView) this.findViewById(R.id.expandableListView);
        initData();
        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

            @Override
            public int getGroupCount() {
                return mparent.size();
            }

            //获取当前父item下的子item的个数
            @Override
            public int getChildrenCount(int groupPosition) {
                String key = mparent.get(groupPosition);
                return map.get(key).size();
            }

            //获得当前父Item的数据
            @Override
            public Object getGroup(int groupPosition) {
                return mparent.get(groupPosition);
            }

            //得到子Item需要关联的数据
            @Override
            public Object getChild(int groupPosition, int childPosition) {
                String key = mparent.get(groupPosition);
                return map.get(key).get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }


            @Override
            public boolean hasStableIds() {
                return true;
            }

            //设置父Item组件
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) MainListActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.parent_layout, null);
                }
                TextView tv = (TextView) convertView
                        .findViewById(R.id.parent_item);
                tv.setText(MainListActivity.this.mparent.get(groupPosition));
                return convertView;
            }

            //设置子Item组件
            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                String key = MainListActivity.this.mparent.get(groupPosition);
                String info = map.get(key).get(childPosition);
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) MainListActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.children_layout, null);
                }
                TextView tv = (TextView) convertView
                        .findViewById(R.id.children_item);
                tv.setText(info);
                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent();
                if(childPosition < 2) {
                    intent.setClass(MainListActivity.this, ScrollingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("lesson_title", mparent.get(groupPosition));
                    bundle.putSerializable("lesson_childtitle",map.get(mparent.get(groupPosition)).get(childPosition));
                    bundle.putSerializable("lesson_id", groupPosition);
                    bundle.putSerializable("lesson_type", childPosition);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else if(childPosition == 2){
                    intent.setClass(MainListActivity.this,WordListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("lesson_childtitle",map.get(mparent.get(groupPosition)).get(childPosition));
                    bundle.putSerializable("lesson_title",mparent.get(groupPosition));
                    bundle.putSerializable("lesson_id",groupPosition);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }
        });


    }

}

