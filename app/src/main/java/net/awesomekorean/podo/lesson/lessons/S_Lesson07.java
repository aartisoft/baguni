package net.awesomekorean.podo.lesson.lessons;

import net.awesomekorean.podo.R;

public class S_Lesson07 extends S_LessonInit implements LessonItem, LessonSpecial {

    private String lessonId = "SL_07";
    private String lessonTitle = "Speaking naturally";
    private int lessonImage = R.drawable.speakingnaturally2;

    private int contents = R.string.SL_07_CONTENTS;

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

