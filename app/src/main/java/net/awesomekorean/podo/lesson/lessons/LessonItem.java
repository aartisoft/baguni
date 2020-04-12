package net.awesomekorean.podo.lesson.lessons;

public interface LessonItem {

    String getLessonId();

    int getLessonProgress();

    void setLessonProgress(int progress);

    String getTitle();

    String getSubTitle();

    int getLessonImage();

    boolean getIsSpecial();

    boolean getIsLock();

    boolean getIsCompleted();

    void setIsCompleted(boolean b);
    void setIsLocked(boolean b);

}