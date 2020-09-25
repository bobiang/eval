package com.antra.evaluation.reporting_system.pojo.api;

public class MultiSheetExcelRequest extends ExcelRequest{
    private String splitBy;
    public String getSplitBy() {
        return splitBy;
    }

    public void setSplitBy(String splitBy) {
        this.splitBy = splitBy;
    }

    public boolean valid() {
        if (splitBy == null) {
            return false;
        }
        if (getFilename() == null) {
            setFilename("untitled");
        }
        if (!(getFilename().endsWith(".xlsx") || getFilename().endsWith(".xls"))) {
            setFilename(getFilename()+".xlsx");
        }
        if (getHeaders() == null || getData()==null) {
            return false;
        }
        int len = getHeaders().size();
        for (var d: getData()) {
            if (d.size() != len) {
                return false;
            }
        }
        return true;
    }
}
