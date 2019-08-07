package net.awesomekorean.baguni.reading;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import net.awesomekorean.baguni.MainReading;
import net.awesomekorean.baguni.R;

public class ReadingFrame extends AppCompatActivity implements Button.OnClickListener {

    TextView title; // reading 타이틀
    TextView article; // reading 본문

    TextView popUpTextView;  // 단어 클릭 시 팝업 텍스트뷰

    ImageButton imageButton;

    Reading reading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_frame);

        title = findViewById(R.id.title);
        article = findViewById(R.id.article);
        popUpTextView = findViewById(R.id.popUpTextView);
        imageButton = findViewById(R.id.imageBtn);

        article.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                imageButton.setRight((int) motionEvent.getX());
                imageButton.setBottom((int) motionEvent.getY());

                return false;
            }
        });


        switch (MainReading.readingUnit) {

            case 0:
                reading = new Reading0();
                readyForReading();
        }
    }

    public void readyForReading() {  // 글 생성

        title.setText(reading.getTitle());


        SpannableStringBuilder span = new SpannableStringBuilder(reading.getArticle());

        for(int i=0; i<reading.getStart().length; i++) {

            final int finalI = i;
            span.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {  // 하이라이트 클릭 이벤트

                    popUpTextView.setText(reading.getPopUpText()[finalI]);

                    if(popUpTextView.getVisibility()==View.GONE) {
                        popUpTextView.setVisibility(View.VISIBLE);

                    } else {
                        popUpTextView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {   // 하이라이트 디자인 설정
                    ds.setColor(Color.rgb(243,110,84));
                    ds.setUnderlineText(true);
                    ds.setFakeBoldText(true);
                }

            }, reading.getStart()[i], reading.getEnd()[i], 0);  // 하이라이트 위치 설정

            article.setTag(i);
            article.setText(span);
            article.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.article :

                popUpTextView.setVisibility(View.GONE);
        }
    }
}