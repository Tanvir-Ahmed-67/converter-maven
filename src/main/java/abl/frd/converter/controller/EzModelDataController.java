package abl.frd.converter.controller;

import abl.frd.converter.ResponseMessage;
import abl.frd.converter.helper.EzRemitDataServiceHelper;
import abl.frd.converter.service.EzRemitDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin("http://localhost:8080")
@RequestMapping("/ezremit")
public class EzModelDataController {
    @Autowired
    private final EzRemitDataService ezRemitDataService;
    public Logger logger = LoggerFactory.getLogger(EzModelDataController.class);

    public EzModelDataController(EzRemitDataService ezRemitDataService){
        this.ezRemitDataService = ezRemitDataService;
    }

    @GetMapping(value = "/index")
    public String homePage() {
        ezRemitDataService.clearDatabase();
        return "ez";
    }

    @GetMapping(value = "/cleardb")
    public ResponseEntity<ResponseMessage> clearDb() {
        logger.info("Database Cleared!");
        String message = "Database Cleared!";
        ezRemitDataService.clearDatabase();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (EzRemitDataServiceHelper.hasCSVFormat(file)) {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileNameWithoutExtension = file.getOriginalFilename().substring(0, extensionIndex);
            try {
                logger.info("Attempting to save File data into database");
                ezRemitDataService.save(file);
                logger.info("Successfully Saved File data into database");
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/ezremit/download/")
                        .path(fileNameWithoutExtension + ".txt")
                        .toUriString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
            }
        }
        logger.info("Wrong File Format. hasCSVFormat(file) method checking failed");
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(ezRemitDataService.load());
        int extensionIndex = fileName.lastIndexOf(".");
        String fileNameWithoutExtension = fileName.substring(0,extensionIndex);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileNameWithoutExtension+".txt")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
