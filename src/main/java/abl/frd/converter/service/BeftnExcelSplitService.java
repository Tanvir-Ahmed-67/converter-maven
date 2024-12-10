package abl.frd.converter.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class BeftnExcelSplitService {
    public Map<String, List<String>> splitExcelFile(InputStream inputStream, String outputDirectory, int maxRowsPerFile, String fileName) throws IOException {
        // Load the input Excel file
        Workbook inputWorkbook = WorkbookFactory.create(inputStream);
        Sheet inputSheet = inputWorkbook.getSheetAt(0); // Assuming working with the first sheet
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        int rowCount = 0;
        int fileCount = 1;
        double grossAmount=0;
        String formattedTotal = "";
        double totalAmountInEachFile=0;
        List<String> totalAmountList = new ArrayList<>();
        List<String> grossAmountList = new ArrayList<>();
        List<String> rowList = new ArrayList<>();

        Map<String, List<String>> returnMap = new HashMap<>();

        Workbook outputWorkbook = null;
        Sheet outputSheet = null;
        int outputRowCount = 0; // Start from 0 for the first row (header row)

        // Create the first output workbook and sheet
        outputWorkbook = new XSSFWorkbook();
        outputSheet = outputWorkbook.createSheet("Sheet1");

        // Write the header to the first output file
        writeHeader(inputSheet, outputSheet); // Explicitly write the header to the first file
        outputRowCount++; // Move to the next row after the header

        // Iterate over rows in the input sheet (skip the first header row)
        Iterator<Row> rowIterator = inputSheet.iterator();
        rowIterator.next(); // Skip the header row from input (already copied)

        while (rowIterator.hasNext()) {
            Row inputRow = rowIterator.next();
            Cell inputCell = inputRow.getCell(9);

            // Start a new workbook when the maximum rows per file are reached
            if (rowCount % maxRowsPerFile == 0 && rowCount > 0) {
                // Adjust column widths for the current sheet
                autoSizeColumns(outputSheet);

                // Save the current workbook before starting a new one
                saveWorkbook(outputWorkbook, outputDirectory + "/"+fileName + fileCount++ + ".xlsx");
                totalAmountList.add(formattedTotal);
                totalAmountInEachFile=0;
                // Create a new workbook and sheet for the next file
                outputWorkbook = new XSSFWorkbook();
                outputSheet = outputWorkbook.createSheet("Sheet1");

                // Write the header again in the new sheet
                writeHeader(inputSheet, outputSheet); // Add header to the new file
                outputRowCount = 1; // Start after header
            }
            // Write data row with correct row index
            writeRow(inputRow, outputSheet.createRow(outputRowCount), outputWorkbook);
            totalAmountInEachFile=totalAmountInEachFile+inputCell.getNumericCellValue();
            grossAmount=grossAmount+inputCell.getNumericCellValue();
            formattedTotal = decimalFormat.format(totalAmountInEachFile);
            outputRowCount++; // Increment for the next row in the output file
            rowCount++;
        }

        // Save the last workbook if it exists
        if (outputWorkbook != null) {
            // Adjust column widths for the last sheet
            autoSizeColumns(outputSheet);
            saveWorkbook(outputWorkbook, outputDirectory + "/"+fileName + fileCount + ".xlsx");
            totalAmountList.add(formattedTotal);
            grossAmountList.add(String.valueOf(decimalFormat.format(grossAmount)));
            rowList.add(String.valueOf(rowCount));
        }
        returnMap.put("individualSum",totalAmountList);
        returnMap.put("grossSum",grossAmountList);
        returnMap.put("totalCount",rowList);
        inputWorkbook.close();
        return returnMap;
    }

    public void clearOutputDirectory(String outputDirectory) {
        File dir = new File(outputDirectory);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    private void saveWorkbook(Workbook workbook, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }
    private void writeHeader(Sheet inputSheet, Sheet outputSheet) {
        // Get the first row (header) from the input sheet
        Row headerRow = inputSheet.getRow(0); // Assuming first row is the header
        if (headerRow != null) {
            // Create a new row in the output sheet and copy the header
            Row outputHeaderRow = outputSheet.createRow(0);
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell inputCell = headerRow.getCell(i);
                Cell outputCell = outputHeaderRow.createCell(i);

                if (inputCell != null) {
                    // Copy cell value as-is (no formatting here)
                    switch (inputCell.getCellType()) {
                        case STRING:
                            outputCell.setCellValue(inputCell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(inputCell)) {
                                outputCell.setCellValue(inputCell.getDateCellValue());
                            } else {
                                outputCell.setCellValue(inputCell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            outputCell.setCellValue(inputCell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            outputCell.setCellFormula(inputCell.getCellFormula());
                            break;
                        default:
                            outputCell.setCellValue("");
                    }
                }
            }
        }
    }
    private void writeRow(Row inputRow, Row outputRow, Workbook outputWorkbook) {
        DataFormat dataFormat = outputWorkbook.createDataFormat();
        CellStyle textStyle = outputWorkbook.createCellStyle();
        textStyle.setDataFormat(dataFormat.getFormat("@")); // Text format for cells

        DataFormatter dataFormatter = new DataFormatter(); // For consistent formatting

        for (int i = 0; i < inputRow.getLastCellNum(); i++) {
            Cell inputCell = inputRow.getCell(i);
            Cell outputCell = outputRow.createCell(i);
            int cellNumber = inputCell.getColumnIndex();
            if (inputCell != null && cellNumber!=9) {   // All cell except the cell where contains amount
                // Get cell value as a string using DataFormatter
                String cellValue = dataFormatter.formatCellValue(inputCell);

                // Write cell value as a string
                outputCell.setCellValue(cellValue);

                // Apply text format to preserve leading zeros and exact formatting
                outputCell.setCellStyle(textStyle);
            }
            else{
                outputCell.setCellValue(inputCell.getNumericCellValue());
            }
        }
    }
    private void autoSizeColumns(Sheet outputSheet) {
        for (int i = 0; i < outputSheet.getRow(0).getLastCellNum(); i++) {
            outputSheet.autoSizeColumn(i);
        }
    }
}

