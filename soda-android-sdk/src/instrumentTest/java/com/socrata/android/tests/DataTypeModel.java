/*
 * SODA Android SDK - Socrata, Inc
 *
 * Copyright (C) 2013 Socrata, Inc
 * All rights reserved.
 *
 * Developed for Socrata, Inc by:
 * 47 Degrees, LLC
 * http://47deg.com
 * hello@47deg.com
 */

package com.socrata.android.tests;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;
import com.socrata.android.soql.datatypes.Document;
import com.socrata.android.soql.datatypes.Location;
import com.socrata.android.soql.datatypes.Phone;
import com.socrata.android.soql.datatypes.Url;

import java.util.Date;

/**
 * Mapping entity for unittest
 */
@SodaEntity
public class DataTypeModel {

    private Integer percent;

    @SodaField("datetimewtimezone")
    private Date dateTimeWithoutTimeZone;

    private String plaintext;

    private Integer number;

    @SodaField("linkeddataset")
    private String linkedDataset;

    @SodaField("formattedtext")
    private String formattedText;

    private Double money;

    @SodaField("datetime")
    private Date dateTime;

    private Phone phone;

    private Location location;

    private Integer star;

    private String photo;

    private Url url;

    private Document document;

    @SodaField("multiplechoice")
    private String multipleChoice;

    private String flag;

    private String email;


    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Date getDateTimeWithoutTimeZone() {
        return dateTimeWithoutTimeZone;
    }

    public void setDateTimeWithoutTimeZone(Date dateTimeWithoutTimeZone) {
        this.dateTimeWithoutTimeZone = dateTimeWithoutTimeZone;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLinkedDataset() {
        return linkedDataset;
    }

    public void setLinkedDataset(String linkedDataset) {
        this.linkedDataset = linkedDataset;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(String multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
