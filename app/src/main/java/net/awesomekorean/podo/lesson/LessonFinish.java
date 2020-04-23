package net.awesomekorean.podo.lesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import net.awesomekorean.podo.AdsLoad;
import net.awesomekorean.podo.PlaySoundPool;
import net.awesomekorean.podo.R;
import net.awesomekorean.podo.SharedPreferencesInfo;
import net.awesomekorean.podo.UserInformation;

public class LessonFinish extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout selectResult;

    ImageView box1;
    ImageView box2;
    ImageView box3;

    ImageView finishPodo;
    TextView tvPoint;
    ImageView imageCoin;

    Button btnGetPoint;

    int reward;

    boolean isFromProfile;

    PlaySoundPool playSoundPool;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_finish);

        context = getApplicationContext();

        selectResult = findViewById(R.id.selectResult);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        box3 = findViewById(R.id.box3);
        finishPodo = findViewById(R.id.finishPodo);
        tvPoint = findViewById(R.id.tvPoint);
        imageCoin = findViewById(R.id.imageCoin);
        btnGetPoint = findViewById(R.id.btnGetPoint);
        box1.setOnClickListener(this);
        box2.setOnClickListener(this);
        box3.setOnClickListener(this);
        btnGetPoint.setOnClickListener(this);

        playSoundPool = new PlaySoundPool(context);
        playSoundPool.playSoundYay();

        Animation aniScale = AnimationUtils.loadAnimation(context, R.anim.scale_1000);
        finishPodo.startAnimation(aniScale);
        isFromProfile = getIntent().getBooleanExtra("isReward", false);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnGetPoint :

                // 포인트 합산하기
                UserInformation userInformation = SharedPreferencesInfo.getUserInfo(context);
                int oldPoints = userInformation.getPoints();
                int newPoints = oldPoints + reward;
                userInformation.setPoints(newPoints);

                if(isFromProfile) {
                    Intent intent = new Intent();
                    intent.putExtra("newPoint", newPoints);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    FirebaseCrashlytics.getInstance().log("lessonId : " + LessonAdapterChild.lessonItem.getLessonId());
                    String lessonId = LessonAdapterChild.lessonItem.getLessonId();


                    // analytics 로그 이벤트 얻기
                    FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
                    Bundle bundle = new Bundle();
                    bundle.putString("lessonId", lessonId);
                    firebaseAnalytics.logEvent("lesson_finish", bundle);


                    // 레슨완료 정보 업데이트 하기
                    userInformation.updateCompleteList(context, lessonId, 100, false);

                    // 애드몹 광고 보여주기
                    if(!isFromProfile) {

                        AdsLoad.getInstance().playAds(this);
                    }
                }
                break;


            default:
                // 포인트 랜덤으로 가져오기
                reward = RandomRewards.randomRewards();
                tvPoint.setText("+ " + String.valueOf(reward));
                selectResult.setVisibility(View.VISIBLE);
                playSoundPool.playSoundLesson(2);

                Animation aniSelectResult = AnimationUtils.loadAnimation(context, R.anim.move_up);
                selectResult.startAnimation(aniSelectResult);
                aniSelectResult.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation aniPoint = AnimationUtils.loadAnimation(context, R.anim.move_up_small);
                        tvPoint.startAnimation(aniPoint);
                        imageCoin.startAnimation(aniPoint);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
        }
    }
}
