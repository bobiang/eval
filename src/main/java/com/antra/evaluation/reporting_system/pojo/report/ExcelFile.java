package com.antra.evaluation.reporting_system.pojo.report;

public class ExcelFile {
    private int id;
    private ExcelData excelData;
    public ExcelFile(int i,ExcelData data) {
        this.id =i;
        this.excelData = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExcelData getExcelData() {
        return excelData;
    }

    public void setExcelData(ExcelData excelData) {
        this.excelData = excelData;
    }

}
