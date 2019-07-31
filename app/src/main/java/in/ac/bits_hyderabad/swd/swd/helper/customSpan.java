package in.ac.bits_hyderabad.swd.swd.helper;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
public class customSpan implements LineBackgroundSpan{

        public static final float DEFAULT_RADIUS = 3;

        private final float radius;
        private final int color;

        public customSpan() {
            this.radius = DEFAULT_RADIUS;
            this.color = 0;
        }

        public customSpan(int color) {
            this.radius = DEFAULT_RADIUS;
            this.color = color;
        }

        public customSpan(float radius) {
            this.radius = radius;
            this.color = 0;
        }
        public customSpan(float radius, int color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void drawBackground(
                Canvas canvas, Paint paint,
                int left, int right, int top, int baseline, int bottom,
                CharSequence charSequence,
                int start, int end, int lineNum
        ) {
            int oldColor = paint.getColor();
            if (color != 0) {
                paint.setColor(color);
            }
            canvas.drawCircle((left+right)/2,(top+bottom)/2,radius,paint);
            paint.setColor(oldColor);
        }
    }

