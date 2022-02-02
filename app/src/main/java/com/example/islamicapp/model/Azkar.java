package com.example.islamicapp.model;

public class Azkar {
        public String start;
        public String zekr;
        public int repeat;
        public String bless;

    public Azkar(String start, String zekr, int repeat, String bless) {
        this.start = start;
        this.zekr = zekr;
        this.repeat = repeat;
        this.bless = bless;
    }

    public String getZekr() {
        return zekr;
    }

    public void setZekr(String zekr) {
        this.zekr = zekr;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getBless() {
        return bless;
    }

    public void setBless(String bless) {
        this.bless = bless;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
