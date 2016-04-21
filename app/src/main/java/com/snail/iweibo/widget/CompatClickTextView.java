package com.snail.iweibo.widget;
import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.snail.iweibo.widget.theme.ThemeTextView;

/**
 * CompatClickTextView
 * <p/>
 * Created by alexwan on 16/4/21.
 */
public class CompatClickTextView extends ThemeTextView {
    private boolean isConsumeUrlClick = true;
    private boolean linkHit; // 是否命中URL连接

    public CompatClickTextView(Context context) {
        super(context);
    }

    public CompatClickTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompatClickTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        linkHit = false;
        boolean res = super.onTouchEvent(event);
        if (isConsumeUrlClick) {
            return linkHit;
        }
        return res;
    }

    public static class CompatLinkMovementMethod extends LinkMovementMethod {

        private static CompatLinkMovementMethod sInstance;

        public static MovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new CompatLinkMovementMethod();

            return sInstance;
        }
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                    } else {
                        Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                    }
                    if (widget instanceof CompatClickTextView) {
                        ((CompatClickTextView) widget).setLinkHit(true);
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }

            return super.onTouchEvent(widget, buffer, event);
        }
    }

    public void setLinkHit(boolean linkHit) {
        this.linkHit = linkHit;
    }

    public void setConsumeUrlClick(boolean consumeUrlClick) {
        isConsumeUrlClick = consumeUrlClick;
    }
}
