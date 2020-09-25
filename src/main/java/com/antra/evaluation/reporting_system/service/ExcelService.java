package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;

public interface ExcelService {
    InputStream getExcelBodyById(int id);
    ExcelRepository getRepo();
    public ExcelGenerationService getExcelGen();
    ExcelResponse createExcel(ExcelRequest request);
    ExcelResponse createMultiExcel(MultiSheetExcelRequest request);
    Optional<ExcelFile> download(int id);
    ResponseEntity<List<ExcelResponse>> list();
	Optional<ExcelFile> delete(int id);
    
}
