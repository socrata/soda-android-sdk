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

package com.socrata.android.soql.datatypes;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;

/**
 * Remote SODA datatype for a Document
 */
@SodaEntity
public class Document {

    @SodaField("file_id")
    private String fileId;

    @SodaField("filename")
    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
