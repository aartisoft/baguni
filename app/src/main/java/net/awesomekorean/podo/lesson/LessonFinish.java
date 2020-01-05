package net.awesomekorean.podo.lesson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import net.awesomekorean.podo.MainActivity;
import net.awesomekorean.podo.R;
import net.awesomekorean.podo.SharedPreferencesUserInfo;
import net.awesomekorean.podo.UserInformation;

public class LessonFinish extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LinearLayout selectBox;
    ConstraintLayout selectResult;

    ImageView box1;
    ImageView box2;
    ImageView box3;

    TextView tvPoints;

    Button btnGetPoint;

    int reward;

    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_finish);

        selectBox = findViewById(R.id.selectBox);
        selectResult = findViewById(R.id.selectResult);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        box3 = findViewById(R.id.box3);
        tvPoints = findViewById(R.id.tvPoints);
        btnGetPoint = findViewById(R.id.btnGetPoint);
        box1.setOnClickListener(this);
        box2.setOnClickListener(this);
        box3.setOnClickListener(this);
        btnGetPoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnGetPoint :
                // DB 에 포인트 합산하기
                UserInformation userInformation = SharedPreferencesUserInfo.getUserInfo(getApplicationContext());
                int oldPoints = userInformation.getPoints();
                int newPoints = oldPoints + reward;
                userInformation.setPoints(newPoints);

                // 레슨완료 정보 업데이트 하기
                for(Integer lessonUnit : userInformation.getLessonComplete()) {
                    if(lessonUnit == MainLesson.lessonUnit) {
                        isFirst = false;
                    }
                }

                if(isFirst) {
                    userInformation.addLessonComplete(MainLesson.lessonUnit);
                    System.out.println("레슨을 완료했습니다.");
                } else {
                    System.out.println("이미 완료된 레슨입니다.");
                }

                SharedPreferencesUserInfo.setUserInfo(getApplicationContext(), userInformation);
                db.collection(getString(R.string.DB_USERS)).document(MainActivity.userEmail).collection(getString(R.string.DB_INFORMATION)).document(getString(R.string.DB_INFORMATION)).set(userInformation);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                // 포인트 랜덤으로 가져오기
                reward = RandomRewards.randomRewards();
                tvPoints.setText(String.valueOf(reward));
                selectBox.setVisibility(View.GONE);
                selectResult.setVisibility(View.VISIBLE);
                break;
        }
    }
}
