package abl.frd.converter.controller;

import abl.frd.converter.service.BeftnExcelSplitService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private BeftnExcelSplitService beftnExcelSplitService;

    @GetMapping("/uploadFile")
    public String showForm(Model model) {
        String outputDir = System.getProperty("user.dir") + "/output";
        beftnExcelSplitService.clearOutputDirectory(outputDir);
        return "beftnSplitUpload";
    }

    @PostMapping("/split")
    public String splitExcel(@RequestParam("file") MultipartFile file,
                             @RequestParam("maxRows") int maxRows,
                             Model model) {
        String outputDir = System.getProperty("user.dir") + "\\output";
        new File(outputDir).mkdirs();

        try {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileNameWithoutExtension = file.getOriginalFilename().substring(0, extensionIndex);
            String inputFilePath = outputDir + "/" + file.getOriginalFilename();

            Map<String, List<String>> returnMap = beftnExcelSplitService.splitExcelFile(file.getInputStream(), outputDir, maxRows, fileNameWithoutExtension);
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
