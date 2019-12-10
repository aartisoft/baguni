package net.awesomekorean.podo.writing;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import net.awesomekorean.podo.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WritingRepository {

    private String DB_NAME = "writing_db";
    private WritingDb db;

    public WritingRepository(Context context) {
        db = Room.databaseBuilder(context, WritingDb.class, DB_NAME).build();
    }

    public void insert(final WritingEntity entity) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.writingDao().insert(entity);
                return null;
            }
        }.execute();
    }

    public void update(final WritingEntity entity) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.writingDao().update(entity);
                return null;
            }
        }.execute();
    }

    public void updateBeforeRequestCorrection(final String guid, final String teacherName) {

        new AsyncTask<Void, Void, WritingEntity>() {
            @Override
            protected WritingEntity doInBackground(Void... voids) {
                String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
                WritingEntity entity = db.writingDao().getByGuid(guid);
                entity.setUserEmail(MainActivity.userEmail);
                entity.setUserName(MainActivity.userName);
                entity.setTeacherName(teacherName);
                entity.setIsCorrected(1);
                entity.setDateRequest(date);
                db.writingDao().update(entity);
                return null;
            }
        }.execute();
    }



    public void updateStudentFeedbackByGuid(final String guid, final String feedback) {

        new AsyncTask<Void, Void, WritingEntity>() {
            @Override
            protected WritingEntity doInBackground(Void... voids) {
                WritingEntity entity = db.writingDao().getByGuid(guid);
                entity.setStudentFeedback(feedback);
                db.writingDao().update(entity);
                return null;
            }
        }.execute();
    }

    public void editByGuid(final String guid, final String article, final String letters) {

        new AsyncTask<Void, Void, WritingEntity>() {
            @Override
            protected WritingEntity doInBackground(Void... voids) {
                WritingEntity entity = db.writingDao().getByGuid(guid);
                entity.setArticle(article);
                entity.setLetters(letters);
                db.writingDao().update(entity);
                return null;
            }
        }.execute();
    }


    public void deleteByGuid(final String guid) {

        new AsyncTask<Void, Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.writingDao().delete(db.writingDao().getByGuid(guid));
                return null;
            }
        }.execute();
    }

    public LiveData<List<WritingEntity>> getAll() {
        return db.writingDao().getAll();
    }


}