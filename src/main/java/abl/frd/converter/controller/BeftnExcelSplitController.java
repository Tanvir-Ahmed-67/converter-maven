package abl.frd.converter.controller;

import abl.frd.converter.service.BeftnExcelSplitService;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:8080")
public class BeftnExcelSplitController {
    @Value("${output.directory}")
    private String outputDir;
    @Autowired
    private BeftnExcelSplitService beftnExcelSplitService;
    @PostConstruct
    public void init() {
        ZipSecureFile.setMinInflateRatio(0.0); // Or 0.0 to disable
    }

    @GetMapping("/")
    public String index() {
        beftnExcelSplitService.clearOutputDirectory(outputDir);
        return "redirect:/beftn/uploadFile";
    }

    @GetMapping("/beftn/uploadFile")
    public String showForm() {
        beftnExcelSplitService.clearOutputDirectory(outputDir);
        return "beftnSplitUpload";
    }

    @PostMapping("/beftn/split")
    public String splitExcel(@RequestParam("file") MultipartFile file,
                             @RequestParam("maxRows") int maxRows,
                             @RequestParam("initialFileNumber") int initialFileNumber,
                             Model model, HttpServletRequest request) {
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs(); // Ensure directory exists
        }
        // Robust way to get server IP as seen by client
        String serverIp = request.getLocalAddr();  // use getLocalAddr() instead of getRemoteAddr()
        String networkPath = "\\\\" + serverIp + "\\output";

        try {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileNameWithoutExtension = file.getOriginalFilename().substring(0, extensionIndex);
            String inputFilePath = outputDir + "/" + file.getOriginalFilename();

            Map<String, List<String>> returnMap = beftnExcelSplitService.splitExcelFile(file.getInputStream(), outputDir, maxRows, fileNameWithoutExtension, initialFileNumber);
            model.addAttribute("message", "BEFTN file splitted successfully!");
            model.addAttribute("individualSum", returnMap.get("individualSum"));
            model.addAttribute("grossSum", returnMap.get("grossSum"));
            model.addAttribute("totalCount", returnMap.get("totalCount"));
            model.addAttribute("outputDir", outputDir);
            model.addAttribute("networkPath", networkPath);

            File[] listOfFiles = outputDirectory.listFiles();
            List<String> fileNames = new ArrayList<>();
            if (listOfFiles != null) {
                Arrays.sort(listOfFiles, Comparator.comparingInt(f -> {
                    String name = f.getName();
                    java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+)(?=\\.[^.]+$)").matcher(name);
                    return matcher.find() ? Integer.parseInt(matcher.group(1)) : Integer.MAX_VALUE;
                }));
                for (File f : listOfFiles) {
                    if (f.isFile() && f.getName().endsWith(".xlsx")) {
                        fileNames.add(f.getName());
                    }
                }
            }
            model.addAttribute("fileNames", fileNames);
        } catch (IOException e) {
            model.addAttribute("message", "Error occurred: " + e.getMessage());
        }

        return "beftnSplitResult";
    }

    @GetMapping("/beftn/downloadSplit/{fileName:.+}")
    public ResponseEntity<Resource> downloadSplitFile(@PathVariable String fileName) {
        File file = new File(outputDir, fileName);
        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    @GetMapping("/beftn/downloadAll")
    public ResponseEntity<Resource> downloadAll() {
        File directory = new File(outputDir);
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return ResponseEntity.notFound().build();
        }

        File zipFile = new File(outputDir, "splitted_beftn.zip");
        try {
            try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(zipFile))) {
                byte[] buffer = new byte[4096];
                for (File file : files) {
                    // Only zip xlsx files, do not zip the zip file itself if it already exists
                    if (file.isFile() && file.getName().endsWith(".xlsx")) {
                        java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(file.getName());
                        zos.putNextEntry(zipEntry);
                        try (java.io.FileInputStream fis = new java.io.FileInputStream(file)) {
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                zos.write(buffer, 0, bytesRead);
                            }
                        }
                        zos.closeEntry();
                    }
                }
            }

            Resource resource = new FileSystemResource(zipFile);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFile.getName() + "\"")
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
