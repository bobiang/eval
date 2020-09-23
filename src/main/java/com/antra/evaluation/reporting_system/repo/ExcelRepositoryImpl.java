package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExcelRepositoryImpl implements ExcelRepository {

    Map<Integer, ExcelFile> excelData = new ConcurrentHashMap<>();

    @Override
    public Optional<ExcelFile> getFileById(int id) {
        return Optional.ofNullable(excelData.get(id));
    }

    @Override
    public ExcelFile saveFile(ExcelFile file) {
        excelData.put(file.getId(),file);
        return file;
    }

    @Override
    public ExcelFile deleteFile(int id) {
        if (excelData.containsKey(id)) {
            ExcelFile e = excelData.get(id);
            File file = new File(e.getExcelData().getTitle());
            file.delete();
            excelData.remove(id);
            return e;
        }
        return null;
    }

    @Override
    public List<ExcelFile> getFiles() {
        List<ExcelFile> result = new ArrayList<>();
        for (int key: excelData.keySet()) {
            result.add(excelData.get(key));
        }
        return result;
    }
}

