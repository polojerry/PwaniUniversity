package com.polotechnologies.polo.pwaniuniversity.Data;

public class TeachingTimeTable {
    String sSemester;
    String sYear;
    String sTimeTableUrl;

    public TeachingTimeTable(String sSemester, String sYear, String sTimeTableUrl) {
        this.sSemester = sSemester;
        this.sYear = sYear;
        this.sTimeTableUrl = sTimeTableUrl;
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
