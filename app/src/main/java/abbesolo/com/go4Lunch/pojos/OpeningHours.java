package abbesolo.com.go4Lunch.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.pojos. All rights reserved.
//
public class OpeningHours {
    @SerializedName("open_now")
    @Expose
    private Boolean openNow;

    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekday_text = null;

    public List<Object> getWeekdayText() {
        return weekday_text;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekday_text = weekdayText;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }
}
