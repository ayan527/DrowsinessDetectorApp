package com.example.drowsinessdetectorapp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FaceGraphic extends GraphicOverlay.Graphic {
    private static float EAR_THRESHOLD = 0.7f;
    private static final int LANDMARK_CONST = 99;
    private static final int REQ_EYE_FPS = 30;
    private static final int REQ_MOUTH_FPS = 20;
    private int eyeFrameCount = 0;
    private int mouthFrameCount = 0;

    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;
    private Paint mIdAlert;

    private volatile Face mFace;
    private Context mContext;
    private boolean leftClosed, rightClosed, mouthOpened;

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

        //Checking for Eyes open & Close
        if (contains(face.getLandmarks(),4) != LANDMARK_CONST && contains(face.getLandmarks(),10) != LANDMARK_CONST) {
            canvas.drawText("right eye: " + String.format("%.2f", rightEyesOpenProb), left + 10.0f, bottom + ID_Y_OFFSET, mIdPaint);
            canvas.drawText("left eye: " + String.format("%.2f", leftEyesOpenProb), right + 4*ID_X_OFFSET, bottom + ID_Y_OFFSET, mIdPaint);

            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                EAR_THRESHOLD = 0.8f;
            } else if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                EAR_THRESHOLD = 0.7f;
            }

            if (leftClosed && face.getIsLeftEyeOpenProbability() > EAR_THRESHOLD) {
                leftClosed = false;
            } else if (!leftClosed && face.getIsLeftEyeOpenProbability() < EAR_THRESHOLD) {
                leftClosed = true;
            }
            if (rightClosed && face.getIsRightEyeOpenProbability() > EAR_THRESHOLD) {
                rightClosed = false;
            } else if (!rightClosed && face.getIsRightEyeOpenProbability() < EAR_THRESHOLD) {
                rightClosed = true;
            }
            if (leftClosed && rightClosed) {
                eyeFrameCount += 1;
                if (eyeFrameCount >= REQ_EYE_FPS) {
                    canvas.drawText("ALERT: SLEEPY", right + 4 * ID_X_OFFSET, top - 10.0f, mIdAlert);
                    EventBus.getDefault().post(new AllEyesClosedEvent());
                }
            } else if (!leftClosed && !rightClosed) {
                if (eyeFrameCount >= REQ_EYE_FPS) {
                    canvas.drawText("", right + 4 * ID_X_OFFSET, top - 10.0f, mIdAlert);
                    EventBus.getDefault().post(new AllEyesOpenedEvent());
                }
                eyeFrameCount = 0;
            }
        }

        //Checking for Landmarks - Right, Left & Bottom Mouth
        if ((contains(face.getLandmarks(), 11) != LANDMARK_CONST)
                && (contains(face.getLandmarks(), 5) != LANDMARK_CONST)
                && (contains(face.getLandmarks(), 0) != LANDMARK_CONST)) {
            Log.i("FaceGraphic","Landmarks Present");
            //Coordinate : Bottom Mouth
            int cBottomMouthX = (int) translateX(face.getLandmarks().get(contains(face.getLandmarks(), 0)).getPosition().x);
            int cBottomMouthY = (int) translateY(face.getLandmarks().get(contains(face.getLandmarks(), 0)).getPosition().y);
            canvas.drawCircle(cBottomMouthX, cBottomMouthY, 10, mIdPaint);
            //Coordinate : Left Mouth
            //int cLeftMouthX = (int) translateX(face.getLandmarks().get(contains(face.getLandmarks(), 5)).getPosition().x);
            int cLeftMouthY = (int) translateY(face.getLandmarks().get(contains(face.getLandmarks(), 5)).getPosition().y);
            //canvas.drawCircle(cLeftMouthX, cLeftMouthY, 10, mIdPaint);
            //Coordinate : Right Mouth
            //int cRightMouthX = (int) translateX(face.getLandmarks().get(contains(face.getLandmarks(), 11)).getPosition().x);
            int cRightMouthY = (int) translateY(face.getLandmarks().get(contains(face.getLandmarks(), 11)).getPosition().y);
            //canvas.drawCircle(cRightMouthX, cRightMouthY, 10, mIdPaint);

            //float centerPointX = (cLeftMouthX + cRightMouthX) / 2;
            float centerPointY = ((cLeftMouthY + cRightMouthY) / 2) - 20;
            //canvas.drawCircle(centerPointX, centerPointY, 10, mIdPaint);

            //float differenceX = centerPointX - cBottomMouthX;
            float differenceY = centerPointY - cBottomMouthY;
            canvas.drawText("Mouth Open: " + String.format("%.2f", differenceY), cBottomMouthX + ID_X_OFFSET, cBottomMouthY + ID_Y_OFFSET, mIdPaint);

            //Checking Mouth Open / Close
            if (!mouthOpened && differenceY < (-95)) {
                mouthOpened = true;
            } else if(mouthOpened && differenceY >= (-95)) {
                mouthOpened = false;
            }
            if (mouthOpened) {
                mouthFrameCount += 1;
                if(mouthFrameCount >= REQ_MOUTH_FPS) {
                    canvas.drawText("ALERT: Yawning", right + 4 * ID_X_OFFSET, top - 10.0f, mIdAlert);
                    EventBus.getDefault().post(new MouthOpenedEvent());
                }
            } else if (!mouthOpened) {
                if (mouthFrameCount >= REQ_MOUTH_FPS) {
                    canvas.drawText("", right + 4*ID_X_OFFSET, top - 10.0f, mIdAlert);
                    EventBus.getDefault().post(new MouthClosedEvent());
                }
                mouthFrameCount = 0;
            }

        } else  {
            Log.i("FaceGraphic","Landmarks Not Present");
        }
    }

    int contains(List<Landmark> list, int name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == name) {
                return i;
            }
        }
        return LANDMARK_CONST;
    }

    void updateFace(Context context,Face face) {
        mContext = context;
        mFace = face;
        postInvalidate();
    }

}