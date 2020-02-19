package net.awesomekorean.podo.lesson.lessonHangul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.awesomekorean.podo.R;
import net.awesomekorean.podo.PlayAudioMediaPlayer;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LessonHangul extends AppCompatActivity implements Button.OnClickListener {

    FirebaseStorage storage = FirebaseStorage.getInstance();

    Hangul thisHangul;

    LinearLayout layoutHangul;
    TextView textViewHangul;
    TextView textViewHangulExplain;
    ConstraintLayout layoutIntro; // 인트로 버튼 눌렀을 때 뷰
    TextView textViewIntro; // 인트로 텍스트
    ImageView btnClose; // 인트로뷰 닫기 버튼

    ImageView imageViewHangul;

    String[] hangul;
    String[] hangulExplain;
    String hangulIntro;
    String conVowBat;

    int currentHangul = 0;
    int writingBtnClicked = 0;
    int hintBtnClicked = 0;

    ImageView btnAudio;
    LinearLayout btnWriting;
    LinearLayout btnHint;
    ImageView iconWriting;
    ImageView iconHint;
    Button btnIntro;
    ImageView btnBack;

    PlayAudioMediaPlayer playAudioMediaPlayer =  new PlayAudioMediaPlayer();

    Context context;

    int resIDWriting;
    int resIDHint;

    Map<Integer, byte[]> audiosHangul = new HashMap<>();
    boolean isFirstAudio = true;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_hangul);

        context = getApplicationContext();

        layoutHangul = findViewById(R.id.layoutHangul);
        textViewHangul = findViewById(R.id.textViewHangul);
        textViewHangulExplain = findViewById(R.id.textViewHangulExplain);
        layoutIntro = findViewById(R.id.layoutIntro);
        textViewIntro = findViewById(R.id.textViewIntro);
        btnClose = findViewById(R.id.btnClose);
        btnAudio = findViewById(R.id.btnAudio);
        btnWriting = findViewById(R.id.btnWriting);
        btnHint = findViewById(R.id.btnHint);
        iconWriting = findViewById(R.id.iconWriting);
        iconHint = findViewById(R.id.iconHint);
        btnIntro = findViewById(R.id.btnIntro);
        btnBack = findViewById(R.id.btnBack);
        btnAudio.setOnClickListener(this);
        btnWriting.setOnClickListener(this);
        btnHint.setOnClickListener(this);
        btnIntro.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        intent = getIntent();
        String hangulName = intent.getExtras().getString("conVowBat");

        switch (hangulName) {

            case "consonant" :

                thisHangul = new LessonHangulConsonant();
                getThisHangul("con", hangulName);
                break;

            case "vowel" :

                thisHangul = new LessonHangulVowel();
                getThisHangul("vow", hangulName);
                break;

            case "batchim" :

                thisHangul = new LessonHangulBatchim();
                getThisHangul("bat", hangulName);
                break;
        }

        layoutHangul.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                if(currentHangul == 0) {
                    currentHangul = hangul.length-1;
                } else {
                    currentHangul--;
                }
                setHangul();
                setHintIcons();
                if(imageViewHangul != null) {
                    visible(VISIBLE, GONE);
                }
            }

            public void onSwipeLeft() {
                if(currentHangul == hangul.length-1) {
                    currentHangul = 0;
                } else {
                    currentHangul++;
                }
                setHangul();
                setHintIcons();
                if(imageViewHangul != null) {
                    visible(VISIBLE, GONE);
                }
            }
        });

        setHintIcons();
    }

    public void getThisHangul(String comVowBat, String hangulName) {
        conVowBat = comVowBat;
        hangul = thisHangul.getHangul();
        hangulExplain = thisHangul.getHangulExplain();
        hangulIntro = thisHangul.getHangulIntro();
        textViewIntro.setText(hangulIntro);
        textViewIntro.setMovementMethod(new ScrollingMovementMethod());

        // 한글오디오 불러오기
        int length = thisHangul.getHangul().length;
        String[] hangulAudio = new String[length];
        String folder = "hangul/" + conVowBat;

        for(int i=0; i<length; i++) {
            final Integer audioIndexHangul = i;
            hangulAudio[i] = hangulName + "_" + i + ".mp3";
            StorageReference storageRef = storage.getReference().child(folder).child(hangulAudio[i]);
            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    System.out.println("오디오를 로드했습니다.");
                    audiosHangul.put(audioIndexHangul, bytes);
                    if(audioIndexHangul == 0) {
                        setHangul();
                        isFirstAudio = false;
                    }
                }
            });
        }
    }


    public void setHangul() {
        textViewHangul.setText(hangul[currentHangul]);
        textViewHangulExplain.setText(hangulExplain[currentHangul]);
        if(audiosHangul.get(currentHangul) != null && audiosHangul.get(currentHangul).length > 0) {
            playAudioMediaPlayer.playAudioInByte(audiosHangul.get(currentHangul));
        }
    }


    // writing 이랑 hint 아이콘 설정하기
    private void setHintIcons() {
        String packName = getApplicationContext().getPackageName();
        String resNameWriting = "@drawable/w" + conVowBat + currentHangul;
        resIDWriting = getResources().getIdentifier(resNameWriting, "drawable", packName);

        System.out.println("NAME:"+resNameWriting);

        if(resIDWriting == 0) {
            iconWriting.setImageResource(R.drawable.hangul_writing_grey);
            btnWriting.setEnabled(false);
        } else {
            iconWriting.setImageResource(R.drawable.hangul_writing);
            btnWriting.setEnabled(true);
        }


        String resNameHint = "@drawable/h" + conVowBat + currentHangul;
        resIDHint = getResources().getIdentifier(resNameHint, "drawable", packName);

        if(resIDHint == 0) {
            iconHint.setImageResource(R.drawable.hint_grey);
            btnHint.setEnabled(false);
        } else {
            iconHint.setImageResource(R.drawable.hint);
            btnHint.setEnabled(true);
        }
    }

    private void visible(int textView, int imageView) {
        imageViewHangul.setVisibility(imageView);
        textViewHangul.setVisibility(textView);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnAudio :
                playAudioMediaPlayer.playAudioInByte(audiosHangul.get(currentHangul));
                break;

            case R.id.btnWriting :
                if(writingBtnClicked == 0) {
                    imageViewHangul = findViewById(R.id.imageViewHangul);
                    imageViewHangul.setImageResource(resIDWriting);

                    visible(GONE, VISIBLE);
                    writingBtnClicked = 1;
                    hintBtnClicked = 0;

                } else {
                    visible(VISIBLE, GONE);
                    writingBtnClicked = 0;
                }
                break;

            case R.id.btnHint :
                if(hintBtnClicked == 0) {
                    imageViewHangul = findViewById(R.id.imageViewHangul);
                    imageViewHangul.setImageResource(resIDHint);

                    visible(GONE, VISIBLE);
                    hintBtnClicked = 1;
                    writingBtnClicked = 0;

                } else {
                    visible(VISIBLE, GONE);
                    hintBtnClicked = 0;
                }
                break;

            case R.id.btnIntro :
                layoutIntro.setVisibility(VISIBLE);
                break;

            case R.id.btnClose :
                layoutIntro.setVisibility(GONE);
                break;

            case R.id.btnBack :
                finish();
                break;
        }
    }
}
