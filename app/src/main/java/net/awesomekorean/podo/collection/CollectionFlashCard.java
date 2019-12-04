package net.awesomekorean.podo.collection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFocusRequest;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.awesomekorean.podo.R;

import java.io.IOException;

public class CollectionFlashCard extends AppCompatActivity implements Button.OnClickListener {

    ImageView btnBack;
    ImageView btnRecord;
    Button btnSave;

    LinearLayout saveResult;

    EditText editFront;
    EditText editBack;

    String code;

    String textFront;
    String textBack;
    String guid;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_flash_card);

        // 유저한테 위험권한(녹음) 허가를 요청
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this,"Authorization is required",Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this,"1",Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        100);
                Toast.makeText(this,"2",Toast.LENGTH_LONG).show();


            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"승인이 허가되어 있습니다.",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this,"아직 승인받지 않았습니다.",Toast.LENGTH_LONG).show();
                }
                return;
            }

        }


        editFront = findViewById(R.id.textFront);
        editBack = findViewById(R.id.textBack);
        saveResult = findViewById(R.id.saveResult);

        btnBack = findViewById(R.id.btnBack);
        btnRecord = findViewById(R.id.btnRecord);
        btnSave = findViewById(R.id.btnSave);
        btnBack.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        Intent intent = getIntent();

        code = intent.getExtras().getString(getString(R.string.REQUEST));

        // EDIT 때, 기존의 front, icon_back 값을 받아서 출력
        if(code.equals(getString(R.string.REQUEST_EDIT))) {

            textFront = intent.getExtras().getString(getString(R.string.EXTRA_FRONT));
            textBack = intent.getExtras().getString(getString(R.string.EXTRA_BACk));
            guid = intent.getExtras().getString(getString(R.string.EXTRA_GUID));
            editFront.setText(textFront);
            editBack.setText(textBack);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnBack :
                // ADD 일 때는 result 값을 주고, EDIT 일 때는 그냥 activity 만 종료함.
                if(code.equals(getString(R.string.REQUEST_ADD))) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;

            case R.id.btnSave :

                String front = editFront.getText().toString();
                String back = editBack.getText().toString();

                CollectionRepository repository = new CollectionRepository(this);

                // ADD 일 때, save 를 눌러도 collection 으로 전환되지 않고 계속 단어를 추가 할 수 있다
                if(code.equals(getString(R.string.REQUEST_ADD))) {
                    repository.insert(front, back);

                // EDIT 일 때
                } else {
                    repository.editByGuid(guid, front, back);
                }
                saveResult.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        saveResult.setVisibility(View.GONE);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        editFront.setText("");
                        editBack.setText("");
                        if(code.equals(getString(R.string.REQUEST_EDIT))) {
                            finish();
                        }
                    }
                }, 1000);
                break;

            case R.id.btnRecord :

                if(mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
                mediaRecorder = new MediaRecorder();

                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setOutputFile("/collection/guid.mp4");

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (Exception ex) {
                    System.out.println("EXCEPTION : " + ex);
                }
                break;
        }
    }
}
