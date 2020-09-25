package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelRepository excelRepository;
    @Autowired
    ExcelGenerationService excelGenerationServiceImpl;
    @Override
    public ExcelRepository getRepo() {
        return excelRepository;
    }
    @Override
    public ExcelGenerationService getExcelGen() {
    	return excelGenerationServiceImpl;
    }
    
    
    @Override
    public InputStream getExcelBodyById(int id) {
        //Optional<ExcelFile> fileInfo = excelRepository.getFileById(id);
        //if (fileInfo.isPresent()) {
	        //File file = new File(fileInfo.get().getExcelData().getTitle());
	        try {
	        	Optional<ExcelFile> fileInfo = excelRepository.getFileById(id);
	        	File file = new File(fileInfo.get().getExcelData().getTitle());
	            return new FileInputStream(file);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }catch (NullPointerException f){
	        	f.printStackTrace();
	        }
        //}
        return null;
    }
    


}
