package com.polotechnologies.polo.pwaniuniversity.Data;

public class ExamResult {
    String mAcademicYear;
    String mStudyStage;
    String mExamResult;

    public ExamResult(String mAcademicYear, String mStudyStage, String mExamResult) {
        this.mAcademicYear = mAcademicYear;
        this.mStudyStage = mStudyStage;
        this.mExamResult = mExamResult;
    }

    public String getmAcademicYear() {
        return mAcademicYear;
    }

    public String getmStudyStage() {
        return mStudyStage;
    }

    public String getmExamResult() {
        return mExamResult;
    }
}
