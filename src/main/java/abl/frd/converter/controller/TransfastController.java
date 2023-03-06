package abl.frd.converter.controller;

import abl.frd.converter.ResponseMessage;
import abl.frd.converter.helper.TransfastDataServiceHelper;
import abl.frd.converter.service.TransfastDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@CrossOrigin("http://localhost:8089")
@RequestMapping("/transfast")
public class TransfastController {
    private final TransfastDataService transfastDataService;
    @Autowired
    public TransfastController(TransfastDataService transfastDataService){
        this.transfastDataService = transfastDataService;
    }
    @GetMapping(value = "/index")
    public String homePage() {
        transfastDataService.clearDatabase();
        return "transfast";
    }
    @GetMapping(value = "/cleardb")
    public ResponseEntity<ResponseMessage> clearDb() {
        String message = "Database Cleared!";
        transfastDataService.clearDatabase();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (TransfastDataServiceHelper.hasCSVFormat(file)) {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileNameWithoutExtension = file.getOriginalFilename().substring(0, extensionIndex);
            try {
                transfastDataService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/transfast/download/")
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

}
