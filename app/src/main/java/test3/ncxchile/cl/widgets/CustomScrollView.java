package test3.ncxchile.cl.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;


/**
 * Created by android-developer on 16-10-2014.
 */

public class CustomScrollView extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;

    ScrollArrow scrollArrowBottom;
    ScrollArrow scrollArrowTop;

    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setScrollArrows(ScrollArrow scrollArrowBottom, ScrollArrow scrollArrowTop){
        this.scrollArrowBottom=scrollArrowBottom;
        this.scrollArrowTop=scrollArrowTop;
        this.scrollArrowTop.setVisibility(View.INVISIBLE);
        this.scrollArrowBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            System.out.println("ME APRETARON");
            fullScroll(View.FOCUS_DOWN);
            }
        });
        this.scrollArrowTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ME APRETARON");
                fullScroll(View.FOCUS_UP);
            }
        });
    }

    public interface OnScrollViewListener {
        void onScrollChanged( CustomScrollView v, int l, int t, int oldl, int oldt );
    }

    public void setOnScrollViewListener(OnScrollViewListener l) {
        this.mOnScrollViewListener = l;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        mOnScrollViewListener.onScrollChanged( this, l, t, oldl, oldt );
        super.onScrollChanged( l, t, oldl, oldt );
    }

    public void init() {
        setOnScrollViewListener(new OnScrollViewListener() {
            @Override
            public void onScrollChanged(CustomScrollView v, int l, int t, int oldl, int oldt) {
                System.out.println("l=" + l + " t=" + t);
                View view = (View) getChildAt(getChildCount() - 1);
                int diff = (view.getBottom() - (getHeight() + getScrollY() + view.getTop()));// Calculate the scrolldiff

                if (diff == 0 && t > 0) { // if diff is zero, then the bottom has been reached
                    System.out.println("MyScrollView: Bottom has been reached");
                    scrollArrowBottom.setAnimation(fadeOut());
                    scrollArrowBottom.setVisibility(View.INVISIBLE);
                    scrollArrowTop.setAnimation(fadeIn());
                    scrollArrowTop.setVisibility(View.VISIBLE);
                }
                if (t == 0) {
                    System.out.println("No está en el tope, se debe redibujar la imagen");
                    scrollArrowBottom.setAnimation(fadeIn());
                    scrollArrowBottom.setVisibility(View.VISIBLE);
                    scrollArrowTop.setAnimation(fadeOut());
                    scrollArrowTop.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    Animation fadeIn(){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(500);
        return fadeIn;
    }

    Animation fadeOut(){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(100);
        fadeOut.setDuration(100);
        return fadeOut;
    }
}