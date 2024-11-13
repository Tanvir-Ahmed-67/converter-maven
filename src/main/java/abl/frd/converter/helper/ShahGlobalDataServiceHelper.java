package abl.frd.converter.helper;

import abl.frd.converter.model.CityExchangeDataModel;
import abl.frd.converter.model.ShahGlobalDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ShahGlobalDataServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};
    public static boolean hasCSVFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null &&
                (contentType.equalsIgnoreCase("text/csv") ||
                        contentType.equalsIgnoreCase("application/csv") ||
                        contentType.equalsIgnoreCase("application/vnd.ms-excel") ||
                        contentType.equalsIgnoreCase("text/plain") ||
                        contentType.equalsIgnoreCase("application/vnd.oasis.opendocument.spreadsheet")||
                        contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    public static List<ShahGlobalDataModel> csvToShahGlobalDataModels(InputStream is) {
        try{
            Workbook records = new XSSFWorkbook(is);
            Row r;
            StringBuilder rowData = new StringBuilder();
            Sheet worksheet = records.getSheetAt(0);
            List<ShahGlobalDataModel> shahGlobalDataModelList = new ArrayList<>();
            int rowStart = 1;// Math.min(15, worksheet.getFirstRowNum());
            int rowEnd = worksheet.getLastRowNum(); //Math.max(1400, worksheet.getLastRowNum());

            /*
            r=worksheet.getRow(3);
            Cell cell = r.getCell(0,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            String enteredDate="";
            if (cell != null && cell.getCellType() == CellType.STRING){
                rowData.append(cell.getStringCellValue());
                rowData.append(" "); // Separator for cells, change if needed
                enteredDate = rowData.toString().replace("DATE:", "").trim();
            }
             */
            for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
                ShahGlobalDataModel shahGlobalDataModel = new ShahGlobalDataModel();
                List<String> eachCell = new ArrayList<>();
                r = worksheet.getRow(rowNum);
                if (r == null) {
                    // This whole row is empty
                    break;
                }
                int lastColumn = Math.max(r.getLastCellNum(), 9);
                for (int cn = 1; cn < lastColumn; cn++) {
                    Cell c = r.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    DataFormatter df = new DataFormatter();
                    df.formatCellValue(c);
                    if (c == null) {
                        // The spreadsheet is empty in this cell
                        eachCell.add("");  // setting dummy value as cell is empty here
                    } else {
                        // cell has contents here
                        String value = df.formatCellValue(c);
                        eachCell.add(value);
                    }
                }
                //  System.out.println(eachCell.toString());
                shahGlobalDataModel.setTranNo(eachCell.get(0));
                shahGlobalDataModel.setBeneficiaryName(eachCell.get(3));
                shahGlobalDataModel.setBeneficiaryAccount(eachCell.get(4));
                shahGlobalDataModel.setBankName(eachCell.get(5));
                shahGlobalDataModel.setBranchName(eachCell.get(7));
                shahGlobalDataModel.setAmount(Double.parseDouble(eachCell.get(2)));
                shahGlobalDataModel.setEnteredDate(eachCell.get(1));
                shahGlobalDataModel.setRemitterName(eachCell.get(9));
                shahGlobalDataModel.setBranchCode(eachCell.get(8));
                shahGlobalDataModelList.add(shahGlobalDataModel);
                eachCell.clear();
            }
            if(shahGlobalDataModelList.isEmpty()){
                throw new IOException();
            }
            return shahGlobalDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream shahGlobalDataModelToCSV(List<ShahGlobalDataModel> shahGlobalDataModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator("\r\n").withIgnoreEmptyLines(true);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (ShahGlobalDataModel shahGlobalDataModel : shahGlobalDataModelList) {
                List<Object> data = Arrays.asList(
                        "7040",
                        shahGlobalDataModel.getTranNo().trim(),
                        "BDT",  // Currency
                        shahGlobalDataModel.getAmount(),
                        shahGlobalDataModel.getEnteredDate(),
                        shahGlobalDataModel.getRemitterName().trim(),
                        shahGlobalDataModel.getBeneficiaryName().trim(),
                        shahGlobalDataModel.getBeneficiaryAccount().trim(),
                        shahGlobalDataModel.getBankName().trim(),
                        "11",   // Bank Code
                        shahGlobalDataModel.getBranchName().trim(),
                        shahGlobalDataModel.getBranchCode().trim(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        "0"
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}
