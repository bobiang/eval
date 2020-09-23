package com.antra.evaluation.reporting_system.pojo.report;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelData {
    private String title;
    private LocalDateTime generatedTime;
    private List<ExcelDataSheet> sheets;
    public ExcelData() {}
    public ExcelData(ExcelRequest request) {
        title = request.getFilename();
        sheets = new ArrayList<>();
        List<String> headers = request.getHeaders();
        List<ExcelDataHeader> h = new ArrayList<>();
        for (String s: headers) {
            h.add(new ExcelDataHeader(s));
        }

        ExcelDataSheet sheet = new ExcelDataSheet("single sheet", h, request.getData());
        sheets.add(sheet);
        this.generatedTime = LocalDateTime.now();
    }

    public ExcelData(MultiSheetExcelRequest request) {
        title = request.getFilename();
        sheets = new ArrayList<>();
        List<String> headers = request.getHeaders();

        Map<String, List<List<Object>>> sheetMap = new HashMap<>();
        String splitBy = request.getSplitBy();
        int i = headers.indexOf(splitBy);
        for (List<Object> row: request.getData()) {
            String sheet = (String) row.get(i);
            row.remove(i);
            List val = sheetMap.getOrDefault(sheet, new ArrayList<>());
            val.add(row);
            sheetMap.put(sheet, val);
        }
        headers.remove(i);
        List<ExcelDataHeader> h = new ArrayList<>();
        for (String s: headers) {
            h.add(new ExcelDataHeader(s));
        }

        for (String key : sheetMap.keySet()) {
            ExcelDataSheet dataSheet = new ExcelDataSheet(key, h,sheetMap.get(key));
            sheets.add(dataSheet);
        }


    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(LocalDateTime generatedTime) {
        this.generatedTime = generatedTime;
    }

    public List<ExcelDataSheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<ExcelDataSheet> sheets) {
        this.sheets = sheets;
    }
}
