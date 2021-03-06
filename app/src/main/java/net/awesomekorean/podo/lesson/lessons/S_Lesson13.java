package net.awesomekorean.podo.lesson.lessons;

import net.awesomekorean.podo.R;

import java.io.Serializable;

public class S_Lesson13 extends S_LessonInit implements LessonItem, LessonSpecial, Serializable {

    private String lessonId = "SL_13";
    private String lessonTitle = "confusing expression";
    private String lessonSubTitle = "'here' and 'there'";
    private int lessonImage = R.drawable.confusing_expression;

    private int contents = R.string.SL_13_CONTENTS;

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

