package com.daralmathour.altaefhessan.AyatInfo;

/**
 * Created by codelab on 6/2/2018.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    Paint paint;
    Path path;
    int x_start,y_start,x_end,y_end;

    public MyView(Context context,int x_start,int y_start,int x_end,int y_end) {
        super(context);
        this.x_start= y_end;
        this.y_start= y_start;
        this.x_end= x_end;
        this.y_end= y_end;
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.parseColor("#BF238c2a"));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        canvas.drawRect(x_start, y_start, x_end, y_end, paint);
        //drawRect(left, top, right, bottom, paint)

    }

}
