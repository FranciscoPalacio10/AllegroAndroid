package com.example.allegrod;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class clickleable extends ClickableSpan implements View.OnTouchListener {
        private TextView mTarget;
        private int mDefaultColor;
        private int mPressedColor;

        public void wrap(@NonNull TextView view){
            this.mTarget = view;
        }


        public void enableHighlightMode(@ColorInt int defaultColor, @ColorInt int pressedColor){
            this.mDefaultColor = defaultColor;
            this.mPressedColor = pressedColor;
            this.mTarget.setOnTouchListener(this);
            setDefaultTextColor();
        }

        private void setDefaultTextColor(){

            if(mTarget.getText() instanceof  Spannable) {
                Spannable editable = (Spannable) mTarget.getText();
                editable.setSpan(new BackgroundColorSpan(mDefaultColor), 0, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        private void setPressedTextColor(){
            if(mTarget.getText() instanceof  Spannable) {

                Spannable editable = (Spannable) mTarget.getText();
                BackgroundColorSpan[] spans = null;

                spans = editable.getSpans(0, editable.length(), BackgroundColorSpan.class);

                for (int i = 0; i < spans.length; i++)
                    editable.removeSpan(spans[i]);

                editable.setSpan(new BackgroundColorSpan(mPressedColor), 0, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }


        @Override
        public void onClick(@NonNull View widget) { /* No default implementation */ }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    setPressedTextColor();
                    break;
                case MotionEvent.ACTION_UP:
                    setDefaultTextColor();
                    onClick(mTarget);
                    break;
            }

            return true;
        }
    }

