package com.polotechnologies.polo.pwaniuniversity.Data;

public class ExamTimeTable {
    String sComment;
    String sSemester;
    String sYear;
    String sTimeTableUrl;

    public ExamTimeTable(String sComment, String sSemester, String sYear, String sTimeTableUrl) {
        this.sComment = sComment;
        this.sSemester = sSemester;
        this.sYear = sYear;
        this.sTimeTableUrl = sTimeTableUrl;
    }

    public String getsComment() {
        return sComment;
    }

    public String getsSemester() {
        return sSemester;
    }

    public String getsYear() {
        return sYear;
    }

    public String getsTimeTableUrl() {
        return sTimeTableUrl;
    }
}
