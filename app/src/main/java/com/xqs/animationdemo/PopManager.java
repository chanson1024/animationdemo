package com.xqs.animationdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * Created by Administrator on 2017/5/21 0021.
 */

public class PopManager {
    public static final String TAG = "PopManager";

    public static final int IN = 1;
    public static final int MOVE = 2;
    public static final int MOVE2 = 3;
    public static final int OUT = 4;

    private int state;

    private FrameLayout frameLayout;
    private Context context;

    private LinkedList<PopItem> list = new LinkedList();


    private static PopManager popManager;

    private PopManager(){

    }

    public synchronized static PopManager getInstance(){
        if(popManager==null){
            popManager = new PopManager();
        }
        return popManager;
    }

    public void init(FrameLayout frameLayout,Context context){
        this.frameLayout = frameLayout;
        this.context = context;
    }


    public void add(){

        PopItem popItem = new PopItem();
        popItem.state = IN;
        popItem.view =  LayoutInflater.from(context).inflate(R.layout.activity_wrap,null);

        list.add(popItem);

        processList();

    }

    public synchronized void processList(){

        for(PopItem popItem : list){
            if(popItem.state == IN){
                frameLayout.addView(popItem.view);
                ImageView imageView = (ImageView) popItem.view.findViewById(R.id.iv_img);
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.in);
                imageView.startAnimation(animation);

                popItem.state = MOVE;

            }else if(popItem.state == MOVE){
//                frameLayout.addView(popItem.view);
                ImageView imageView = (ImageView) popItem.view.findViewById(R.id.iv_img);
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.move);
                imageView.startAnimation(animation);

                popItem.state = MOVE2;

            }else if(popItem.state == MOVE2){
//                frameLayout.addView(popItem.view);
                ImageView imageView = (ImageView) popItem.view.findViewById(R.id.iv_img);
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.move2);
                imageView.startAnimation(animation);

                popItem.state = OUT;

            }else if(popItem.state == OUT){
//                frameLayout.addView(popItem.view);
                ImageView imageView = (ImageView) popItem.view.findViewById(R.id.iv_img);
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.out);
                imageView.startAnimation(animation);
                animation.setAnimationListener(listener);

            }
        }





    }

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                PopItem popItem = (PopItem)iterator.next();
                if(popItem.state==OUT){

                    iterator.remove();
                    break;
                }
            }

            Log.w(TAG,"list count-->"+list.size());
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

}
