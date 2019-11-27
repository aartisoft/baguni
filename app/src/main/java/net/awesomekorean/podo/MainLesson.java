package net.awesomekorean.podo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.awesomekorean.podo.lesson.LessonAdapter;
import net.awesomekorean.podo.lesson.LessonFrame;
import net.awesomekorean.podo.lesson.LessonItems;

import java.util.ArrayList;

public class MainLesson extends Fragment {

    int lessonUnit = 0;

    Context context;

    View view;

    Intent intent;

    public static MainLesson newInstance() {
        return new MainLesson();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_lesson, container, false);

        ArrayList<LessonItems> list = new ArrayList<>();

        LessonItems item = new LessonItems();
        item.setTitle("Sample title");
        item.setSubTitle("Sample subtitle");
        item.setLessonImage(R.drawable.hangul);
        item.setIsCompleted(true);
        item.setIsLock(true);
        item.setIsCompleted(true);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LessonAdapter adapter = new LessonAdapter(list);
        adapter.setOnItemClickListener(new LessonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                lessonUnit = pos;

                switch (pos) {

                    case 0 :
                        break;

                    default :
                        intent = new Intent(context, LessonFrame.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        recyclerView.setAdapter(adapter);

        context = getContext();

        return view;
    }
}


