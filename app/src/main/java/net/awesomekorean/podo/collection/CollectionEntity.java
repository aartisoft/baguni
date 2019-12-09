package net.awesomekorean.podo.collection;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import net.awesomekorean.podo.MainActivity;

import java.util.UUID;

@Entity(tableName = "COLLECTION")
public class CollectionEntity {

    @PrimaryKey
    @NonNull
    private String guid;
    private String userEmail;
    private String front;
    private String back;
    private String audio;
    private String dateNew;
    private String dateEdit;
    private int deleted = 0; // 0=false, 1=true

    @Ignore
    public CollectionEntity() {

    }

    public CollectionEntity(String front, String back) {

        this.userEmail = MainActivity.userEmail;
        this.front = front;
        this.back = back;

        // GUID 생성하기
        this.guid = UUID.randomUUID().toString();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getDateNew() {
        return dateNew;
    }

    public void setDateNew(String dateNew) {
        this.dateNew = dateNew;
    }

    public String getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(String dateEdit) {
        this.dateEdit = dateEdit;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
