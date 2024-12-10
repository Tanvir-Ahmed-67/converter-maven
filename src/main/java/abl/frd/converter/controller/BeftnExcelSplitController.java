package abl.frd.converter.controller;

import abl.frd.converter.service.BeftnExcelSplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping("/beftn")
public class BeftnExcelSplitController {
    @Value("${output.directory}")
    private String outputDir;
    @Autowired
    private BeftnExcelSplitService beftnExcelSplitService;

    @GetMapping("/uploadFile")
    public String showForm() {
        beftnExcelSplitService.clearOutputDirectory(outputDir);
        return "beftnSplitUpload";
    }

    @PostMapping("/split")
    public String splitExcel(@RequestParam("file") MultipartFile file,
                             @RequestParam("maxRows") int maxRows,
                             @RequestParam("initialFileNumber") int initialFileNumber,
                             Model model) {
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs(); // Ensure directory exists
        }

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
        } catch (IOException e) {
            model.addAttribute("message", "Error occurred: " + e.getMessage());
        }

        return "beftnSplitResult";
    }
}
