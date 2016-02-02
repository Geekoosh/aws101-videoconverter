package com.geekoosh.aws.video.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConvertJob {
    private @Id @GeneratedValue Long id;
    private String input;
    private String lowRes;
    private String highRes;
    private String thumbnail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getLowRes() {
        return lowRes;
    }

    public void setLowRes(String lowRes) {
        this.lowRes = lowRes;
    }

    public String getHighRes() {
        return highRes;
    }

    public void setHighRes(String highRes) {
        this.highRes = highRes;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
