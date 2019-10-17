package net.awesomekorean.baguni.lesson;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import net.awesomekorean.baguni.MainLesson;
import net.awesomekorean.baguni.R;
import net.awesomekorean.baguni.collection.CollectionRepository;

public class LessonWord extends Fragment implements Button.OnClickListener {

    View view;

    Lesson lesson;

    ImageButton btnAudio;
    Button btnCollect;

    TextView collectResult;

    static TextView textViewWordFront;
    static TextView textViewWordBack;

    public static int lessonCount;
    public static int lessonWordLength = 0;
    public static int lessonSentenceLength = 0;

    public static String[] wordFront;
    public static String[] wordBack;
    public static String[] sentenceFront;
    public static String[] sentenceBack;
    public static String[] sentenceExplain;
    public static String[] sentenceClause;



    public static LessonWord newInstance() {
        return new LessonWord();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.lesson_word, container, false);

        LessonFrame.swipePage = getString(R.string.LESSONWORD);
        lessonCount = 0; // 레슨진도초기화 -> 저장하고 exit 했을 때는 lessonCount 를 DB에 저장해야함

        btnAudio = view.findViewById(R.id.btnAudio);
        btnCollect = view.findViewById(R.id.btnCollect);
        collectResult = view.findViewById(R.id.collectResult);
        btnAudio.setOnClickListener(this);
        btnCollect.setOnClickListener(this);

        textViewWordFront = view.findViewById(R.id.textViewWordFront);
        textViewWordBack = view.findViewById(R.id.textViewWordBack);

        switch (MainLesson.lessonUnit) {

            case 1:
                lesson = new Lesson1();
                readyForLesson(R.array.L1_WORD, R.array.L1_SENTENCE, R.array.L1_SENTENCEEXPLAIN);
                break;

        }

        return view;
    }

    public void readyForLesson(int word, int sentence, int sentenceEx) {

        lesson.setWordBack(getResources().getStringArray(word));
        lesson.setSentenceBack(getResources().getStringArray(sentence));
        lesson.setSentenceExplain(getResources().getStringArray(sentenceEx));

        wordFront = lesson.getWordFront();
        wordBack = lesson.getWordBack();

        sentenceFront = lesson.getSentenceFront();
        sentenceBack = lesson.getSentenceBack();
        sentenceExplain = lesson.getSentenceExplain();
        sentenceClause = lesson.getSentenceClause();


        lessonWordLength = lesson.getWordFront().length;
        lessonSentenceLength = lesson.getSentenceFront().length;

        LessonFrame.totalPageNo = lessonWordLength*4 + lessonSentenceLength +2;

        displayWord();
    }


    public static void displayWord() {
        LessonFrame.progressCount();
        textViewWordFront.setText(wordFront[lessonCount]);
        textViewWordBack.setText(wordBack[lessonCount]);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAudio :
                break;

            case R.id.btnCollect :
                String front = wordFront[lessonCount];
                String back = wordBack[lessonCount];

                CollectionRepository repository = new CollectionRepository(getContext());
                repository.insert(front, back);

                collectResult.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collectResult.setVisibility(View.GONE);
                    }
                }, 1000);

                break;

        }

    }

}
