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
        this.textPaint.setColor(-1);
        this.colors[0] = -16776961;
        this.colors[1] = -65536;
        this.colors[2] = -16711936;
        this.colors[3] = -256;
        this.colors[4] = -16711681;
        this.colors[5] = -65281;
        this.colors[6] = -12303292;
        this.colors[7] = -1;
        this.colors[8] = -3355444;
        this.colors[9] = -7829368;
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            this.touchPaints[i] = new Paint();
            this.touchPaints[i].setColor(this.colors[i]);
            this.touchPaints[i].setAntiAlias(true);
        }
    }

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
                    drawCrosshairsAndText((int) event.getX(i), (int) event.getY(i), this.touchPaints[id], i, id, c);
                }
                for (i = 0; i < pointerCount; i++) {
                    drawCircle((int) event.getX(i), (int) event.getY(i), this.touchPaints[event.getPointerId(i)], c);
                }
            }
            getHolder().unlockCanvasAndPost(c);
        }
        return true;
    }

    private void drawCrosshairsAndText(int x, int y, Paint paint, int ptr, int id, Canvas c) {
        c.drawLine(0.0f, (float) y, (float) this.width, (float) y, paint);
        c.drawLine((float) x, 0.0f, (float) x, (float) this.height, paint);
        int textY = (int) (((float) ((ptr * MAX_TOUCHPOINTS) + 15)) * this.scale);
        c.drawText("x" + ptr + "=" + x, 10.0f * this.scale, (float) textY, this.textPaint);
        c.drawText("y" + ptr + "=" + y, 100.0f * this.scale, (float) textY, this.textPaint);
        c.drawText("id" + ptr + "=" + id, ((float) this.width) - (55.0f * this.scale), (float) textY, this.textPaint);
    }

    private void drawCircle(int x, int y, Paint paint, Canvas c) {
        paint.setAlpha(125);
        c.drawCircle((float) x, (float) y, 40.0f * this.scale, paint);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
        if (width > height) {
            this.scale = ((float) width) / 480.0f;
        } else {
            this.scale = ((float) height) / 480.0f;
        }
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(20.0f * this.scale);
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            c.drawColor(-16777216);
            c.drawText(START_TEXT, ((float) (width / 2)) - (this.textPaint.measureText(START_TEXT) / 2.0f), (float) (height / 2), this.textPaint);
            getHolder().unlockCanvasAndPost(c);
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
