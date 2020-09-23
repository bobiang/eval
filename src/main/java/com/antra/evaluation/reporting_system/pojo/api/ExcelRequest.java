package com.antra.evaluation.reporting_system.pojo.api;

import java.util.List;

public class ExcelRequest {
    private String filename;
    private List<String> headers;
    private String description;
    private List<List<Object>> data;
    private String submitter;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }



    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getFilename() {

        return filename;
    }

    public void setFilename(String filename) {

        this.filename = filename;
    }

    public boolean valid() {
        if (headers == null || data == null) {
            return false;
        }
        if (filename == null) {
            filename = "untitled";
        }
        if (!(filename.endsWith(".xlsx") || filename.endsWith(".xls"))) {
            setFilename(filename+".xlsx");
        }
        if (headers.isEmpty() || data.isEmpty()) {
            return false;
        }
        int len = headers.size();
        for (var d: data) {
            if (d.size() != len) {
                return false;
            }
        }
        return true;
    }
}
