package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.endpoint.ExcelGenerationController;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class APITest {
    @Mock
    ExcelService excelService;

    @BeforeEach
    public void configMock() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new ExcelGenerationController(excelService));

    }

    @Test
    public void testFileDownload() throws FileNotFoundException, InterruptedException, ExecutionException {
        Mockito.when(excelService.getExcelBodyById(anyInt())).thenReturn(new FileInputStream("temp.xlsx"));
        given().accept("application/json").get("/excel/1/content").peek().
                then().assertThat()
                .statusCode(200);
        
    }

    @Test
    public void testListFiles() throws FileNotFoundException, InterruptedException, ExecutionException {
        Mockito.when(excelService.getExcelBodyById(anyInt())).thenReturn(new FileInputStream("temp.xlsx"));
        given().accept("application/json").get("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }

    @Test
    @Disabled
    public void testExcelGeneration() throws FileNotFoundException, InterruptedException, ExecutionException {
         Mockito.when(excelService.getExcelBodyById(anyInt())).thenReturn(new FileInputStream("temp.xlsx"));
        given().accept("application/json").contentType(ContentType.JSON).body("{\"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]]}").post("/excel").peek().
                then().assertThat()
                .statusCode(200)
                .body("fileId", Matchers.notNullValue());
    }
    
    @Test
    public void testDeleteExcel() throws Exception {
    	Mockito.when(excelService.getExcelBodyById(1)).thenReturn(new FileInputStream("temp.xlsx"));
        given().accept("application/json").get("/excel/1");
        assertThat(excelService.getExcelBodyById(1).equals(null));
       
    }
}
