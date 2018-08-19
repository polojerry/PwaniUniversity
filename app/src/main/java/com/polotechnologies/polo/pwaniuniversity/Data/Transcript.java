package com.polotechnologies.polo.pwaniuniversity.Data;

public class Transcript {
    public static String mAcademicYear;
    public static String mTranscript;

    public Transcript(String mAcademicYear, String mTranscript) {
        this.mAcademicYear = mAcademicYear;
        this.mTranscript = mTranscript;
    }

    public static String getmAcademicYear() {
        return mAcademicYear;
    }

    public static String getmTranscript() {
        return mTranscript;
    }
}
