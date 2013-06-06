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

import com.socrata.android.client.DataTypesMapper;
import com.socrata.android.client.JsonAdapter;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Common datatypes conversion
 */
public class DataTypeConversionTest extends TestCase {

    public static final String json = "{\n" +
            "  \"phone\" : {\n" +
            "    \"phone_number\" : \"(206) 555-5555\",\n" +
            "    \"phone_type\" : \"Cell\"\n" +
            "  },\n" +
            "  \"percent\" : \"10\",\n" +
            "  \"datetimewtimezone\" : 1347865200,\n" +
            "  \"plaintext\" : \"Hello World\",\n" +
            "  \"location\" : {\n" +
            "    \"needs_recoding\" : false,\n" +
            "    \"longitude\" : \"-74.00382286468441\",\n" +
            "    \"latitude\" : \"40.72935037801244\",\n" +
            "    \"human_address\" : \"{\\\"address\\\":\\\"39 Downing Street\\\",\\\"city\\\":\\\"New York\\\",\\\"state\\\":\\\"NY\\\",\\\"zip\\\":\\\"10014\\\"}\"\n" +
            "  },\n" +
            "  \"star\" : 5,\n" +
            "  \"number\" : \"10\",\n" +
            "  \"linkeddataset\" : \"2\",\n" +
            "  \"photo\" : \"Y8gzwb1a3Pfnc6CLvkB1JI-0gumRqpD-dktIjrrl4WA\",\n" +
            "  \"formattedtext\" : \"<p>&lt;b&gt;Hello World&lt;/b&gt;</p>\",\n" +
            "  \"url\" : {\n" +
            "    \"description\" : \"Socrata's URL\",\n" +
            "    \"url\" : \"http://www.socrata.com\"\n" +
            "  },\n" +
            "  \"document\" : {\n" +
            "    \"file_id\" : \"okHW9JujliIICsxjo136q4c77xyi8E3uQ_NdkLrrnNE\",\n" +
            "    \"filename\" : \"TestDocument.docx\"\n" +
            "  },\n" +
            "  \"multiplechoice\" : \"pkdf-26df\",\n" +
            "  \"flag\" : \"orange\",\n" +
            "  \"email\" : \"support@socrata.com\",\n" +
            "  \"money\" : \"10\",\n" +
            "  \"checkbox\" : true,\n" +
            "  \"datetime\" : \"2012-09-17T00:00:00\"\n" +
            "}";

    private static final String fieldsJson = "[\"phone\",\":updated_at\",\"percent\",\"datetimewtimezone\",\"plaintext\",\"location\",\"star\",\"number\",\"linkeddataset\",\"photo\",\"formattedtext\",\"url\",\"document\",\"multiplechoice\",\"flag\",\"email\",\"checkbox\",\"money\",\":id\",\":created_at\",\"datetime\"]";

    private static final String typesJson = "[\"phone\",\"meta_data\",\"percent\",\"date\",\"text\",\"location\",\"stars\",\"number\",\"dataset_link\",\"photo\",\"html\",\"url\",\"document\",\"drop_down_list\",\"flag\",\"email\",\"checkbox\",\"money\",\"meta_data\",\"meta_data\",\"calendar_date\"]";

    public void testTypeConversion() throws JSONException, InstantiationException, IllegalAccessException {
        JSONObject parsedJson = new JSONObject(json);
        JsonAdapter<DataTypeModel> jsonAdapter = new JsonAdapter<DataTypeModel>(DataTypeModel.class, new DataTypesMapper());
        JSONArray fields = new JSONArray(fieldsJson);
        JSONArray types = new JSONArray(typesJson);
        for (int i = 0; i < fields.length(); i++) {
            String field = fields.optString(i);
            String type = types.optString(i);
            jsonAdapter.addFieldMapping(field, type);
        }
        DataTypeModel dataTypeModel = jsonAdapter.fromJsonObject(parsedJson);
        assertNotNull(dataTypeModel);
    }

}
