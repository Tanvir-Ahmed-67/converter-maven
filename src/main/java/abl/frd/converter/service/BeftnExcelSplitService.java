package abl.frd.converter.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class BeftnExcelSplitService {

    private static final int BATCH_SIZE = 100; // Process rows in batches of 100

    public Map<String, List<String>> splitExcelFile(InputStream inputStream, String outputDirectory, int maxRowsPerFile, String fileName, int fileStartingNumber) throws IOException {
        Workbook inputWorkbook = WorkbookFactory.create(inputStream);
        Sheet inputSheet = inputWorkbook.getSheetAt(0); // Assuming working with the first sheet
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        int rowCount = 0;
        int fileCount = fileStartingNumber;
        double grossAmount = 0;
        double totalAmountInEachFile = 0;
        List<String> totalAmountList = new ArrayList<>();
        List<String> grossAmountList = new ArrayList<>();
        List<String> rowList = new ArrayList<>();

        Map<String, List<String>> returnMap = new HashMap<>();

        SXSSFWorkbook outputWorkbook = new SXSSFWorkbook();
        outputWorkbook.setCompressTempFiles(true);
        Sheet outputSheet = outputWorkbook.createSheet("Sheet1");

        writeHeader(inputSheet, outputSheet);
        int outputRowCount = 1;

        Iterator<Row> rowIterator = inputSheet.rowIterator();
        rowIterator.next(); // Skip the header row

        List<Row> batchRows = new ArrayList<>(); // Temporary storage for batch processing

        while (rowIterator.hasNext()) {
            Row inputRow = rowIterator.next();
            batchRows.add(inputRow);

            // Process a batch when batch size is reached or no more rows
            if (batchRows.size() == BATCH_SIZE || !rowIterator.hasNext()) {
                for (Row row : batchRows) {
                    Cell inputCell = row.getCell(11);

                    if (rowCount % maxRowsPerFile == 0 && rowCount > 0) {
                        saveWorkbook(outputWorkbook, outputDirectory + "/" + fileName + fileCount++ + ".xlsx");
                        totalAmountList.add(decimalFormat.format(totalAmountInEachFile));
                        totalAmountInEachFile = 0;

                        outputWorkbook = new SXSSFWorkbook();
                        outputWorkbook.setCompressTempFiles(true);
                        outputSheet = outputWorkbook.createSheet("Sheet1");

                        writeHeader(inputSheet, outputSheet);
                        outputRowCount = 1;
                    }

                    writeRow(row, outputSheet.createRow(outputRowCount), outputWorkbook);
                    if (inputCell != null && inputCell.getCellType() == CellType.NUMERIC) {
                        totalAmountInEachFile += inputCell.getNumericCellValue();
                        grossAmount += inputCell.getNumericCellValue();
                    }
                    outputRowCount++;
                    rowCount++;
                }
                batchRows.clear(); // Clear batch memory

                // Optionally invoke garbage collection to free memory
                System.gc();
            }
        }

        if (outputWorkbook != null) {
            saveWorkbook(outputWorkbook, outputDirectory + "/" + fileName + fileCount + ".xlsx");
            totalAmountList.add(decimalFormat.format(totalAmountInEachFile));
            grossAmountList.add(decimalFormat.format(grossAmount));
            rowList.add(String.valueOf(rowCount));
        }

        inputWorkbook.close();
        returnMap.put("individualSum", totalAmountList);
        returnMap.put("grossSum", grossAmountList);
        returnMap.put("totalCount", rowList);
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

    private void saveWorkbook(SXSSFWorkbook workbook, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.dispose();
    }

    private void writeHeader(Sheet inputSheet, Sheet outputSheet) {
        Row headerRow = inputSheet.getRow(0);
        if (headerRow != null) {
            Row outputHeaderRow = outputSheet.createRow(0);
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell inputCell = headerRow.getCell(i);
                Cell outputCell = outputHeaderRow.createCell(i);

                if (inputCell != null) {
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
        textStyle.setDataFormat(dataFormat.getFormat("@"));

        DataFormatter dataFormatter = new DataFormatter();

        for (int i = 0; i < inputRow.getLastCellNum(); i++) {
            Cell inputCell = inputRow.getCell(i);
            Cell outputCell = outputRow.createCell(i);
            if (inputCell != null) {
                if (inputCell.getColumnIndex() != 11) {
                    String cellValue = dataFormatter.formatCellValue(inputCell);
                    // Remove special characters and spaces
                    cellValue = cellValue.replaceAll("[^a-zA-Z0-9 ]", "");
                    outputCell.setCellValue(cellValue);
                    outputCell.setCellStyle(textStyle);
                } else {
                    if (inputCell.getCellType() == CellType.NUMERIC) {
                        outputCell.setCellValue(inputCell.getNumericCellValue());
                    } else {
                        outputCell.setCellValue(0);
                    }
                }
            }
        }
    }

    private void autoSizeColumns(Sheet outputSheet) {
        for (int i = 0; i < outputSheet.getRow(0).getLastCellNum(); i++) {
            outputSheet.autoSizeColumn(i);
        }
    }
}