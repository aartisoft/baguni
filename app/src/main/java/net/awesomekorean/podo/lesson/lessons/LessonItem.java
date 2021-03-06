package net.awesomekorean.podo.lesson.lessons;

public interface LessonItem {

    String getLessonId();

    int getLessonProgress();

    void setLessonProgress(int progress);

    String getLessonTitle();

    String getLessonSubTitle();

    int getLessonImage();

    boolean getIsSpecial();

    boolean getIsLock();

    void setIsLocked(boolean b);
}
