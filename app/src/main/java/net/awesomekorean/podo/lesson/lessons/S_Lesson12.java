package net.awesomekorean.podo.lesson.lessons;

import net.awesomekorean.podo.R;

import java.io.Serializable;

public class S_Lesson12 extends S_LessonInit implements LessonItem, LessonSpecial, Serializable {

    private String lessonId = "SL_12";
    private String lessonTitle = "confusing expression";
    private String lessonSubTitle = "'아/어서' vs '으니까'";
    private int lessonImage = R.drawable.confusing_expression;

    private int contents = R.string.SL_12_CONTENTS;

    @Override
    public String getLessonSubTitle() {
        return lessonSubTitle;
    }

    @Override
    public String getLessonId() {
        return lessonId;
    }

    @Override
    public String getLessonTitle() {
        return lessonTitle;
    }

    @Override
    public int getLessonImage() {
        return lessonImage;
    }

    @Override
    public int getContents() {
        return contents;
    }
}

