package com.antra.evaluation.reporting_system.pojo.report;


import java.util.List;

public class ExcelDataSheet {
    private String title;
    private List<ExcelDataHeader> headers;
    private List<List<Object>> dataRows;
    public ExcelDataSheet(){}
    public ExcelDataSheet(String n, List<ExcelDataHeader> h, List<List<Object>> rows) {
        this.title = n;
        this.headers = h;
        this.dataRows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ExcelDataHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ExcelDataHeader> headers) {
        this.headers = headers;
    }

    public List<List<Object>> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<List<Object>> dataRows) {
        this.dataRows = dataRows;
    }
}
