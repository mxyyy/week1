package com.bwie.week1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;



public class WaveView extends View {


    private static final String TAG = "WaveView";
    private float fai = 0;


    private PaintFlagsDrawFilter drawFilter;
    private Paint paint1;
    private Paint paint2;
    private Path path1;
    private Path path2;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    // 波形浮动的监听
    public interface OnWaveChangeListener {
        void onChanged(float y);
    }

    private OnWaveChangeListener listener;

    public void setOnWaveChangeListener(OnWaveChangeListener listener) {
        this.listener = listener;
    }

   private void  init(){
       paint1 = new Paint();
       paint1.setColor(Color.WHITE);
       paint1.setAntiAlias(true);
       paint1.setStyle(Paint.Style.FILL);
       paint1.setStrokeWidth(5);

       paint2 = new Paint();
       paint2.setColor(Color.WHITE);
       paint2.setAntiAlias(true);
       paint2.setStyle(Paint.Style.FILL);
       paint2.setStrokeWidth(5);
       paint2.setAlpha(60);

       path1 = new Path();
       path2 = new Path();

       drawFilter = new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
   }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double Ω = 2 * Math.PI / getMeasuredWidth();

        canvas.setDrawFilter(drawFilter);

        fai -= 0.1f;

        int A = getMeasuredHeight() / 2;

        path1.reset();
        path2.reset();
        //起始点在左下角
        path1.moveTo(getLeft(),getBottom());
        path2.moveTo(getLeft(),getBottom());


       // 从最左侧开始，画到最右侧，每20px画一条线
        for (int x = 0; x <= getMeasuredWidth(); x += 20) {

//            float y = Asin(Ωx+φ)+k
            float y1 = A * (float) Math.sin(Ω * x + fai) + A;
            float y2 = -A * (float) Math.sin(Ω * x + fai) + A;

            if (x > getMeasuredWidth() / 2 - 10 && x < getMeasuredWidth() / 2 + 10) {
                listener.onChanged(y2);
            }

//            Log.i(TAG, "onDraw: (" + x + ", " + y + ")");
            path1.lineTo(x, y1);
            path2.lineTo(x, y2);

        }

        // 终止点在右下角
        path1.lineTo(getWidth(), getBottom());
        path2.lineTo(getWidth(), getBottom());


        canvas.drawPath(path1, paint1);
        canvas.drawPath(path2, paint2);


        postInvalidateDelayed(50);

    }
}
