package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.repo.ExcelRepository;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface ExcelService {
    InputStream getExcelBodyById(int id);
    ExcelRepository getRepo();
    public ExcelGenerationService getExcelGen();
    
}
