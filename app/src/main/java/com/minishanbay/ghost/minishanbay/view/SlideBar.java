package com.minishanbay.ghost.minishanbay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by Ghost on 2016/3/24.
 */
public class SlideBar extends View{
    private Paint paint = new Paint();
    private onTouchNumListener listener;
    //是否画出背景
//    private boolean showBg = false;
    //选中的项
    private int choose = -1;
    //准备好的0-5数组
    public static String[] num = {"0","1","2","3","4","5"};

    private TextView slide_text;

    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //绘制数字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() * 2 / 3;
        int width = getWidth();
        int singleHeight = height/num.length;
//        if(showBg){
//            //画出背景
//            canvas.drawColor(Color.parseColor("#808080"));
//        }
        for(int i = 0;i < num.length;i++){
            paint.setColor(Color.GRAY);
            //设置字体格式
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(dip2px(this.getContext(),10));
            //如果这一项被选中，则换一种颜色绘制
            if(i == choose){
                paint.setColor(Color.parseColor("#209e85"));
                paint.setFakeBoldText(true);
                paint.setTextSize(dip2px(this.getContext(),20));
            }
            //要绘制的数字的x,y坐标
            float posX = width/2 - paint.measureText(num[i])/2;
            float posY = i*singleHeight + singleHeight/2 + height/5 + 210;
            //绘制数字
            canvas.drawText(num[i],posX,posY,paint);
            //重置画笔
            paint.reset();
        }
    }

    //处理SlideBar的状态
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        //算出点击的数字的索引
        final int index = (int) ((3*(y  - (210+getHeight())/5)/(2*getHeight())*num.length));
        //保持上次点击的数字索引到oldChoose
        int oldchoose = choose;
        Log.i("index",index+"");
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                invalidate();
                if(slide_text != null ) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    slide_text.setVisibility(View.INVISIBLE);
                    //showBg = true;
                }
                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                showBg = false;
//                break;
            default:
                if(oldchoose != index){
                    if(index >= 0 && index < num.length){
                        if(slide_text != null){
                            slide_text.setText(num[index]);
                            slide_text.setVisibility(View.VISIBLE);
                        }
                        if(listener != null){
                            listener.onTouchNumChanged(num[index]);
                        }
                        choose = index;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }
    /**
     * 回调方法，注册监听器
     */
    public void setOnTouchNumChangeListener(onTouchNumListener listener){
        this.listener = listener;
    }

    public void setSlide_text(TextView slide_text) {
        this.slide_text = slide_text;
    }

    /**
     * slidebar的监听接口
     */
    public interface onTouchNumListener{
        public void onTouchNumChanged(String s);
    }
    //像素转化
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
