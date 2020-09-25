package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.service.ExcelGenerationService;
import com.antra.evaluation.reporting_system.service.ExcelGenerationServiceImpl;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@RestController

public class ExcelGenerationController {


    private static final Logger log = LoggerFactory.getLogger(ExcelGenerationController.class);
    ExcelService excelService;
    
    
    @Autowired
    public ExcelGenerationController(ExcelService excelService) {
        this.excelService = excelService;

    }

    AtomicInteger id = new AtomicInteger(1);

    @PostMapping("/excel")
    @ApiOperation("Generate Excel")
    public ResponseEntity<ExcelResponse> createExcel(@RequestBody @Validated ExcelRequest request) throws IOException, InterruptedException, ExecutionException {
        ExcelResponse response = new ExcelResponse();
        if (!request.valid()) {
            log.error("Not Valid Input");
            response.setResponse("Not Valid Input");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
        response = this.excelService.createExcel(request);
        log.info(response.getResponse());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/excel/auto")
    @ApiOperation("Generate Multi-Sheet Excel Using Split field")
    public ResponseEntity<ExcelResponse> createMultiSheetExcel(@RequestBody @Validated MultiSheetExcelRequest request) throws IOException, InterruptedException, ExecutionException {
        ExcelResponse response = new ExcelResponse();
        if (!request.valid()) {
            log.error("Not Valid Input");
            response.setResponse("Not Valid Input");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
        response = this.excelService.createMultiExcel(request);
        log.info(response.getResponse());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/excel")
    @ApiOperation("List all existing files")
    public ResponseEntity<List<ExcelResponse>> listExcels() {

        return excelService.list();
    }

    @GetMapping("/excel/{id}/content")
    public void downloadExcel(@PathVariable int id, HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
       
        ExcelFile excelFile = excelService.download(id).get();
        String filename = excelFile.getExcelData().getTitle();
        InputStream fis = excelService.getExcelBodyById(id);
       
        response.setHeader("Content-Type","application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment; filename="+filename);
        FileCopyUtils.copy(fis, response.getOutputStream());

    }

    @DeleteMapping("/excel/{id}")
    public ResponseEntity<ExcelResponse> deleteExcel(@PathVariable int id) throws InterruptedException, ExecutionException {
        var response = new ExcelResponse();
        Optional<ExcelFile> excelFile = null;
        try {
	        excelFile=excelService.delete(id);
        }catch(NullPointerException e) {
        	e.printStackTrace();
        }
        if(excelFile==null) {
            log.error("File Not Exist");
            response.setResponse("File Not Exist");
            return new ResponseEntity<>(response, HttpStatus.OK);         
        }
        response.setResponse("successfully delete file "+id);
        response.setFilename(excelFile.get().getExcelData().getTitle());
        log.info(response.getResponse());
        return new ResponseEntity<>(response, HttpStatus.OK);
        


	        
        }
}

// Log
// Exception handling
// Validation
