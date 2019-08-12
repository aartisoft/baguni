package net.awesomekorean.baguni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import com.opencsv.CSVReader;

import net.awesomekorean.baguni.collection.CollectionDb;
import net.awesomekorean.baguni.collection.CollectionFlashCard;
import net.awesomekorean.baguni.collection.CollectionItems;
import net.awesomekorean.baguni.collection.CollectionListViewAdapter;
import net.awesomekorean.baguni.lesson.LessonFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainCollection extends Fragment implements Button.OnClickListener{

    View view;

    CheckBox selectAll;

    ListView listView;
    ArrayList<CollectionItems> list = new ArrayList<>();
    CollectionListViewAdapter adapter;

    Button btnWord;
    Button btnSentence;
    Button btnStudy;
    Button btnDelete;
    Button btnRecord;
    FloatingActionButton btnAdd;

    Intent intent;

    //static String[] dbKorean = {"사과", "바나나", "망고"};
    //static String[] dbEnglish = {"apple", "banana", "mango"};
    CollectionDb db = new CollectionDb();

    public static MainCollection newInstance() {
        return new MainCollection();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_collection, container, false);

        selectAll = view.findViewById(R.id.checkBoxSelectAll);
        btnWord = view.findViewById(R.id.btnWord);
        btnSentence = view.findViewById(R.id.btnSentence);
        btnStudy = view.findViewById(R.id.btnStudy);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnRecord = view.findViewById(R.id.btnRecord);
        btnAdd = view.findViewById(R.id.btnAddCollection);
        selectAll.setOnClickListener(this);
        btnWord.setOnClickListener(this);
        btnSentence.setOnClickListener(this);
        btnStudy.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        listView = view.findViewById(R.id.listViewCollection);
        getCollections();
        //list = getCollection(false);
        adapter = new CollectionListViewAdapter(getContext(), list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CollectionItems item = (CollectionItems) adapterView.getItemAtPosition(i);
                intent = new Intent(getContext(), CollectionFlashCard.class);
                intent.putExtra("Korean", item.getCollectionKorean());
                intent.putExtra("English", item.getCollectionEnglish());
                intent.putExtra("index", i);
                intent.putExtra("Mode", "edit");
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(selectAll.getVisibility()==View.INVISIBLE) {
                    selectAll.setVisibility(View.VISIBLE);
                    adapter.longClickOnOff("On");
                    btnStudy.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.VISIBLE);
                    btnRecord.setVisibility(View.VISIBLE);
                } else {
                    selectAll.setVisibility(View.INVISIBLE);
                    adapter.longClickOnOff("Off");
                    btnStudy.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);
                    btnRecord.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });


        return view;
    }

    public void getCollections() {

        InputStreamReader input = new InputStreamReader(getResources().openRawResource(R.raw.collections));
        BufferedReader fileReader = new BufferedReader(input);


        try {
            CSVReader reader = new CSVReader(fileReader);
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                String front = nextLine[0];
                String back = nextLine[1];

                CollectionItems items = new CollectionItems();
                items.setCollectionKorean(front);
                items.setCollectionEnglish(back);

                list.add(items);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file");
        } catch (IOException e) {
            System.out.println("Can't read line");
        }
    }

    private ArrayList<CollectionItems> getCollection(boolean isChecked) {

        ArrayList<CollectionItems> list = new ArrayList<>();

        for(int i=0; i<db.getCollectionKorean().length; i++) {

            CollectionItems items = new CollectionItems();
            items.setChecked(isChecked);
            items.setCollectionKorean(db.getCollectionKorean()[i]);
            items.setCollectionEnglish(db.getCollectionEnglish()[i]);
            list.add(items);
        }
        return list;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.checkBoxSelectAll :
                if(selectAll.isChecked()) {
                    adapter.checkAll(true);
                } else {
                    adapter.checkAll(false);
                }

                adapter.notifyDataSetChanged();
                break;

            case R.id.btnWord :
                break;

            case R.id.btnSentence :
                break;

            case R.id.btnStudy :
                break;

            case R.id.btnDelete :
                break;

            case R.id.btnRecord :
                break;

            case R.id.btnAddCollection :
                Intent intent = new Intent(getContext(), CollectionFlashCard.class);
                intent.putExtra("Mode", "add");
                startActivity(intent);
                break;
        }
    }
}
