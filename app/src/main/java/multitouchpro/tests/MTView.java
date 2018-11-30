package multitouchpro.tests;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MTView extends SurfaceView implements Callback {
    private static final int MAX_TOUCHPOINTS = 20;
    private static final String START_TEXT = "Touch Anywhere To Test";
    private int[] colors = new int[MAX_TOUCHPOINTS];
    private int height;
    int jjj = 10;
    private float scale = 1.0f;
    private Paint textPaint = new Paint();
    private Paint[] touchPaints = new Paint[MAX_TOUCHPOINTS];
    private int width;

    public MTView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        init();
    }

    private void init() {
        textPaint.setColor(-1);
        colors[0] = -16776961;
        colors[1] = -65536;
        colors[2] = -16711936;
        colors[3] = -256;
        colors[4] = -16711681;
        colors[5] = -65281;
        colors[6] = -12303292;
        colors[7] = -1;
        colors[8] = -3355444;
        colors[9] = -7829368;
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            touchPaints[i] = new Paint();
            touchPaints[i].setColor(colors[i]);
            touchPaints[i].setAntiAlias(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        if (pointerCount > MAX_TOUCHPOINTS) {
            pointerCount = MAX_TOUCHPOINTS;
        }
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            c.drawColor(-16777216);
            if (event.getAction() != 1) {
                int i;
                for (i = 0; i < pointerCount; i++) {
                    int id = event.getPointerId(i);
                    drawCrosshairsAndText((int) event.getX(i), (int) event.getY(i), touchPaints[id], i, id, c);
                }
                for (i = 0; i < pointerCount; i++) {
                    drawCircle((int) event.getX(i), (int) event.getY(i), touchPaints[event.getPointerId(i)], c);
                }
            }
            getHolder().unlockCanvasAndPost(c);
        }
        return true;
    }

    private void drawCrosshairsAndText(int x, int y, Paint paint, int ptr, int id, Canvas c) {
        c.drawLine(0.0f, (float) y, (float) width, (float) y, paint);
        c.drawLine((float) x, 0.0f, (float) x, (float) height, paint);
        int textY = (int) (((float) ((ptr * MAX_TOUCHPOINTS) + 15)) * scale);
        c.drawText("x" + ptr + "=" + x, 10.0f * scale, (float) textY, textPaint);
        c.drawText("y" + ptr + "=" + y, 100.0f * scale, (float) textY, textPaint);
        c.drawText("id" + ptr + "=" + id, ((float) width) - (55.0f * scale), (float) textY, textPaint);
    }

    private void drawCircle(int x, int y, Paint paint, Canvas c) {
        paint.setAlpha(125);
        c.drawCircle((float) x, (float) y, 40.0f * scale, paint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        width = width;
        height = height;
        if (width > height) {
            scale = ((float) width) / 480.0f;
        } else {
            scale = ((float) height) / 480.0f;
        }
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20.0f * scale);
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            c.drawColor(-16777216);
            c.drawText(START_TEXT, ((float) (width / 2)) - (textPaint.measureText(START_TEXT) / 2.0f), (float) (height / 2), textPaint);
            getHolder().unlockCanvasAndPost(c);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
