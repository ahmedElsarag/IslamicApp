package com.example.islamicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrayerTime {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private TimingsBean timings;
        private DateBean date;
        private MetaBean meta;

        public TimingsBean getTimings() {
            return timings;
        }

        public void setTimings(TimingsBean timings) {
            this.timings = timings;
        }

        public DateBean getDate() {
            return date;
        }

        public void setDate(DateBean date) {
            this.date = date;
        }

        public MetaBean getMeta() {
            return meta;
        }

        public void setMeta(MetaBean meta) {
            this.meta = meta;
        }

        public static class TimingsBean {
            @SerializedName("Fajr")
            private String fajr;
            @SerializedName("Sunrise")
            private String sunrise;
            @SerializedName("Dhuhr")
            private String dhuhr;
            @SerializedName("Asr")
            private String asr;
            @SerializedName("Sunset")
            private String sunset;
            @SerializedName("Maghrib")
            private String maghrib;
            @SerializedName("Isha")
            private String isha;
            @SerializedName("Imsak")
            private String imsak;
            @SerializedName("Midnight")
            private String midnight;

            public String getFajr() {
                return fajr;
            }

            public void setFajr(String fajr) {
                this.fajr = fajr;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getDhuhr() {
                return dhuhr;
            }

            public void setDhuhr(String dhuhr) {
                this.dhuhr = dhuhr;
            }

            public String getAsr() {
                return asr;
            }

            public void setAsr(String asr) {
                this.asr = asr;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public String getMaghrib() {
                return maghrib;
            }

            public void setMaghrib(String maghrib) {
                this.maghrib = maghrib;
            }

            public String getIsha() {
                return isha;
            }

            public void setIsha(String isha) {
                this.isha = isha;
            }

            public String getImsak() {
                return imsak;
            }

            public void setImsak(String imsak) {
                this.imsak = imsak;
            }

            public String getMidnight() {
                return midnight;
            }

            public void setMidnight(String midnight) {
                this.midnight = midnight;
            }
        }

        public static class DateBean {
            private String readable;
            private GregorianBean gregorian;
            private HijriBean hijri;

            public String getReadable() {
                return readable;
            }

            public void setReadable(String readable) {
                this.readable = readable;
            }

            public GregorianBean getGregorian() {
                return gregorian;
            }

            public void setGregorian(GregorianBean gregorian) {
                this.gregorian = gregorian;
            }

            public HijriBean getHijri() {
                return hijri;
            }

            public void setHijri(HijriBean hijri) {
                this.hijri = hijri;
            }

            public static class GregorianBean {
                private String date;
                private String day;
                private WeekdayBean weekday;
                private MonthBean month;
                private String year;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public WeekdayBean getWeekday() {
                    return weekday;
                }

                public void setWeekday(WeekdayBean weekday) {
                    this.weekday = weekday;
                }

                public MonthBean getMonth() {
                    return month;
                }

                public void setMonth(MonthBean month) {
                    this.month = month;
                }

                public String getYear() {
                    return year;
                }

                public void setYear(String year) {
                    this.year = year;
                }

                public static class WeekdayBean {
                    private String en;
                }

                public static class MonthBean {
                    private Integer number;
                    private String en;
                }
            }


            public static class HijriBean {
                private String date;
                private String day;
                private WeekdayBean weekday;
                private MonthBean month;
                private String year;
                private List<?> holidays;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public WeekdayBean getWeekday() {
                    return weekday;
                }

                public void setWeekday(WeekdayBean weekday) {
                    this.weekday = weekday;
                }

                public MonthBean getMonth() {
                    return month;
                }

                public void setMonth(MonthBean month) {
                    this.month = month;
                }

                public String getYear() {
                    return year;
                }

                public void setYear(String year) {
                    this.year = year;
                }

                public List<?> getHolidays() {
                    return holidays;
                }

                public void setHolidays(List<?> holidays) {
                    this.holidays = holidays;
                }

                public static class WeekdayBean {
                    private String en;
                    private String ar;

                    public String getEn() {
                        return en;
                    }

                    public void setEn(String en) {
                        this.en = en;
                    }

                    public String getAr() {
                        return ar;
                    }

                    public void setAr(String ar) {
                        this.ar = ar;
                    }
                }

                public static class MonthBean {
                    private Integer number;
                    private String en;
                    private String ar;

                    public Integer getNumber() {
                        return number;
                    }

                    public void setNumber(Integer number) {
                        this.number = number;
                    }

                    public String getEn() {
                        return en;
                    }

                    public void setEn(String en) {
                        this.en = en;
                    }

                    public String getAr() {
                        return ar;
                    }

                    public void setAr(String ar) {
                        this.ar = ar;
                    }
                }
            }
        }

        public static class MetaBean {
            private Double latitude;
            private Double longitude;
            private String timezone;

            public Double getLatitude() {
                return latitude;
            }

            public void setLatitude(Double latitude) {
                this.latitude = latitude;
            }

            public Double getLongitude() {
                return longitude;
            }

            public void setLongitude(Double longitude) {
                this.longitude = longitude;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }
        }
    }
}

