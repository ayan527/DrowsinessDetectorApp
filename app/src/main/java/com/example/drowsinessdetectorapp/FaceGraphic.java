package com.example.drowsinessdetectorapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.gms.vision.face.Face;

import org.greenrobot.eventbus.EventBus;

public class FaceGraphic extends GraphicOverlay.Graphic {
    //private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float EAR_THRESHOLD = 0.7f;
    private static final int REQ_FPS = 30;
    private int eyeFrameCount = 0;

    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;
    private Paint mIdAlert;

    private volatile Face mFace;

    private boolean leftClosed, rightClosed;

    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(Color.MAGENTA);

        mIdPaint = new Paint();
        mIdPaint.setColor(Color.GREEN);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mIdAlert = new Paint();
        mIdAlert.setColor(Color.RED);
        mIdAlert.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(Color.GREEN);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        //canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
        //canvas.drawText("Driver: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
        //canvas.drawText("happiness: " + String.format("%.2f", face.getIsSmilingProbability()), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
        //canvas.drawText("right eye: " + String.format("%.2f", face.getIsRightEyeOpenProbability()), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
        //canvas.drawText("left eye: " + String.format("%.2f", face.getIsLeftEyeOpenProbability()), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left, top, right, bottom, mBoxPaint);

        //Measuring Eyes Opened Probs
        float leftEyesOpenProb = face.getIsLeftEyeOpenProbability();
        float rightEyesOpenProb = face.getIsRightEyeOpenProbability();

        //Draw Texts for Driver, Left-Eye, Right-Eye
        canvas.drawText("DRIVER", left + 10.0f, top - 10.f, mIdPaint);
        canvas.drawText("right eye: " + String.format("%.2f", rightEyesOpenProb), left + 10.0f, bottom + ID_Y_OFFSET, mIdPaint);
        canvas.drawText("left eye: " + String.format("%.2f", leftEyesOpenProb), right + 4*ID_X_OFFSET, bottom + ID_Y_OFFSET, mIdPaint);

        //Checking for Eyes open & Close
        if (leftClosed && face.getIsLeftEyeOpenProbability() > EAR_THRESHOLD) {
            leftClosed = false;
        } else if (!leftClosed &&  face.getIsLeftEyeOpenProbability() < EAR_THRESHOLD){
            leftClosed = true;
        }
        if (rightClosed && face.getIsRightEyeOpenProbability() > EAR_THRESHOLD) {
            rightClosed = false;
        } else if (!rightClosed && face.getIsRightEyeOpenProbability() < EAR_THRESHOLD) {
            rightClosed = true;
        }
        if (leftClosed && rightClosed) {
            eyeFrameCount += 1;
            if (eyeFrameCount >= REQ_FPS) {
                canvas.drawText("ALERT: SLEEPY", right + 4*ID_X_OFFSET, top - 10.0f, mIdAlert);
                EventBus.getDefault().post(new AllEyesClosedEvent());
            }
        } else if (!leftClosed && !rightClosed) {
            if (eyeFrameCount >= REQ_FPS) {
                canvas.drawText("", right + 4*ID_X_OFFSET, top - 10.0f, mIdAlert);
                EventBus.getDefault().post(new AllEyesOpenedEvent());
            }
            eyeFrameCount = 0;
        }
    }

    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

}
