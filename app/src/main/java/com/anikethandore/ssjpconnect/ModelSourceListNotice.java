package com.anikethandore.ssjpconnect;

public class ModelSourceListNotice {

    String title, desc, branch, year, date ,fileurl,timestamp;

   public ModelSourceListNotice(){

   }

    public ModelSourceListNotice(String title, String desc, String branch, String year, String date, String fileurl, String timestamp) {
        this.title = title;
        this.desc = desc;
        this.branch = branch;
        this.year = year;
        this.date = date;
        this.fileurl = fileurl;
        this.timestamp = timestamp;
    }

    //getter setter

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String decs) {
        this.desc = decs;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
