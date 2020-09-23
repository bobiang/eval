package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.repo.ExcelRepository;

import java.io.InputStream;

public interface ExcelService {
    InputStream getExcelBodyById(int id);
    ExcelRepository getRepo();
}
