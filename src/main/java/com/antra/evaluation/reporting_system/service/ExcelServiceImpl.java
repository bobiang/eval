package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelRepository excelRepository;
    @Autowired
    ExcelGenerationService excelGenerationServiceImpl;
    
    AtomicInteger id = new AtomicInteger(1);
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
    
    public ExcelResponse createExcel(ExcelRequest request) {
    	ExcelResponse response=new ExcelResponse();
    	
        ExcelData data = new ExcelData(request);
        String filename = data.getTitle();
        String newName	=filename;
        File f = new File(filename);
        int prefix=1;
        while (f.exists()) {
        	synchronized(this) {
        		prefix++;
        		newName = prefix+"_"+filename;}
            f=new File(newName);                      
        }
        response.setFilename(response.getResponse() + "set the filename to "+filename+"\n");
        data.setTitle(newName);
        ExcelFile file = new ExcelFile(id.getAndIncrement(), data);
        this.getRepo().saveFile(file);
        try {
			this.getExcelGen().generateExcelReport(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response.setResponse(response.getResponse() +"successfully saved");
        response.setFileId(id.decrementAndGet());
        id.getAndIncrement();
        response.setFilename(data.getTitle());
        
    	return response;
    };

    public ExcelResponse createMultiExcel(MultiSheetExcelRequest request) {
    	ExcelResponse response=new ExcelResponse();
    	
        ExcelData data = new ExcelData(request);
        String filename = data.getTitle();
        String newName	=filename;
        File f = new File(filename);
        int prefix=1;
        while (f.exists()) {
        	synchronized(this) {
        		prefix++;
        		newName = prefix+"_"+filename;}
            f=new File(newName);                      
        }
        response.setFilename(response.getResponse() + "set the filename to "+filename+"\n");
        data.setTitle(newName);
        ExcelFile file = new ExcelFile(id.getAndIncrement(), data);
        this.getRepo().saveFile(file);
        try {
			this.getExcelGen().generateExcelReport(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response.setResponse(response.getResponse() +"successfully saved");
        response.setFileId(id.decrementAndGet());
        id.getAndIncrement();
        response.setFilename(data.getTitle());
        
    	return response;
    }
    
    public Optional<ExcelFile> download(int id) {
        if (this.getRepo() == null) {
            //log.error("File Not Exist");
            return null;
        }
        Optional<ExcelFile> file = this.getRepo().getFileById(id);
        if (!file.isPresent()) {
            //log.error("File Not Exist");
            return null;
        }
        
        
        ExcelFile excelFile = file.get();
//        String filename = excelFile.getExcelData().getTitle();
//        InputStream fis = getExcelBodyById(id);
//       
        return Optional.of(excelFile);
    }
   
    public ResponseEntity<List<ExcelResponse>> list(){
    	var response = new ArrayList<ExcelResponse>();
	    try {
	        List<ExcelFile> files = this.getRepo().getFiles();
	        for (ExcelFile file: files) {
	            ExcelData data = file.getExcelData();
	            ExcelResponse r = new ExcelResponse();
	            r.setFileId(file.getId());
	            r.setFilename(data.getTitle());
	            response.add(r);
	        }
	    } catch (Exception e) {

	    }
	    return new ResponseEntity<>(response, HttpStatus.OK);
    }
	@Override
	public Optional<ExcelFile> delete(int id) {
        Optional<ExcelFile> excelFile = getRepo().getFileById(id);
        if (!excelFile.isPresent()) {
            return null;
        }
        
        getRepo().deleteFile(id);
        
        return excelFile;
	}

}
