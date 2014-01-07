package com.sharetray.caissa.global;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * Created by Admin on 14/09/13.
 */
public class ESwipeListView extends SwipeListView {

    protected EOnItemClickListener eOnItemClickListener;
    /**
     * If you create a View programmatically you need send back and front identifier
     *
     * @param context        Context
     * @param swipeBackView  Back Identifier
     * @param swipeFrontView Front Identifier
     */
    public ESwipeListView(Context context, int swipeBackView, int swipeFrontView) {
        super(context, swipeBackView, swipeFrontView);
    }

    /**
     * @see android.widget.ListView#ListView(android.content.Context, android.util.AttributeSet)
     */
    public ESwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @see android.widget.ListView#ListView(android.content.Context, android.util.AttributeSet, int)
     */
    public ESwipeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * Notifies onClickFrontView
     *
     * @param position item clicked
     */
    @Override
    protected void onClickFrontView(int position) {
        super.onClickFrontView(position);
        Log.e("TRAY",position+" position");
        if(eOnItemClickListener!=null && position>-1)
            eOnItemClickListener.onSwipeItemClicked(position);

    }

    public void setOnSwipeItemClickListener(EOnItemClickListener e){
        this.eOnItemClickListener=e;
    }
}
