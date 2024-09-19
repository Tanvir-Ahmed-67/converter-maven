package abl.frd.converter.controller;

import abl.frd.converter.ResponseMessage;
import abl.frd.converter.helper.CityExchangeDataServiceHelper;
import abl.frd.converter.helper.TransfastDataServiceHelper;
import abl.frd.converter.service.CityExchangeDataService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@CrossOrigin("http://localhost:8089")
@RequestMapping("/city")
public class CityExchangeController {
    private final CityExchangeDataService cityExchangeDataService;
    public CityExchangeController(CityExchangeDataService cityExchangeDataService){
        this.cityExchangeDataService = cityExchangeDataService;
    }
    @GetMapping(value = "/index")
    public String homePage() {
        cityExchangeDataService.clearDatabase();
        return "cityExchange";
    }
    @GetMapping(value = "/cleardb")
    public ResponseEntity<ResponseMessage> clearDb() {
        String message = "Database Cleared!";
        cityExchangeDataService.clearDatabase();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CityExchangeDataServiceHelper.hasCSVFormat(file)) {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileNameWithoutExtension = file.getOriginalFilename().substring(0, extensionIndex);
            try {
                cityExchangeDataService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/city/download/")
                        .path(fileNameWithoutExtension + ".txt")
                        .toUriString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
    }
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(cityExchangeDataService.load());
        int extensionIndex = fileName.lastIndexOf(".");
        String fileNameWithoutExtension = fileName.substring(0,extensionIndex);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileNameWithoutExtension+".txt")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
