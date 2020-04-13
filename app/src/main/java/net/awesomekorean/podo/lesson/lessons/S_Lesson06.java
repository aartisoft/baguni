package net.awesomekorean.podo.lesson.lessons;

import net.awesomekorean.podo.R;

public class S_Lesson06 extends S_LessonInit implements LessonItem, LessonSpecial {

    private String lessonId = "SL_06";
    private String lessonTitle = "Confusing expression";
    private int lessonImage = R.drawable.confusingexpression;

    private int contents = R.string.SL_06_CONTENTS;

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

