package abl.frd.converter.helper;

import abl.frd.converter.model.CityExchangeDataModel;
import abl.frd.converter.model.TransfastDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class CityExchangeDataServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};
    public static boolean hasCSVFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null &&
                (contentType.equalsIgnoreCase("text/csv") ||
                        contentType.equalsIgnoreCase("application/csv") ||
                        contentType.equalsIgnoreCase("application/vnd.ms-excel") ||
                        contentType.equalsIgnoreCase("text/plain") ||
                        contentType.equalsIgnoreCase("application/vnd.oasis.opendocument.spreadsheet"));
    }
    public static List<CityExchangeDataModel> csvToCityExchangeDataModels(InputStream is) {
        try{
            Workbook records = new XSSFWorkbook(is);
            Sheet worksheet = records.getSheetAt(0);
            List<CityExchangeDataModel> cityExchangeDataModelList = new ArrayList<>();
            int rowStart = 9;// Math.min(15, worksheet.getFirstRowNum());
            int rowEnd = worksheet.getLastRowNum(); //Math.max(1400, worksheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
                CityExchangeDataModel cityExchangeDataModel = new CityExchangeDataModel();
                List<String> eachCell = new ArrayList<>();
                Row r = worksheet.getRow(rowNum);
                if (r == null) {
                    // This whole row is empty
                    break;
                }
                int lastColumn = Math.max(r.getLastCellNum(), 9);
                for (int cn = 0; cn < lastColumn; cn++) {
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
                cityExchangeDataModel.setTranNo(eachCell.get(1));
                cityExchangeDataModel.setBeneficiaryName(eachCell.get(2));
                cityExchangeDataModel.setBeneficiaryAccount(eachCell.get(3));
                cityExchangeDataModel.setBankName(eachCell.get(4));
                cityExchangeDataModel.setBranchName(eachCell.get(5));
                cityExchangeDataModel.setAmount(Double.parseDouble(eachCell.get(6)));
                cityExchangeDataModel.setRemitterName(eachCell.get(7));


                cityExchangeDataModelList.add(cityExchangeDataModel);
                eachCell.clear();
            }
            if(cityExchangeDataModelList.isEmpty()){
                throw new IOException();
            }
            return cityExchangeDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream cityExchangeModelToCSV(List<CityExchangeDataModel> cityExchangeDataModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator("\r\n").withIgnoreEmptyLines(true);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (CityExchangeDataModel cityExchangeDataModel : cityExchangeDataModelList) {
                List<Object> data = Arrays.asList(
                        "7040",
                        cityExchangeDataModel.getTranNo().trim(),
                        "BDT",  // Currency
                        cityExchangeDataModel.getAmount(),
                        cityExchangeDataModel.getEnteredDate(),
                        cityExchangeDataModel.getRemitterName().trim(),
                        cityExchangeDataModel.getBeneficiaryName().trim(),
                        cityExchangeDataModel.getBeneficiaryAccount().trim(),
                        cityExchangeDataModel.getBankName().trim(),
                        "11",   // Bank Code
                        cityExchangeDataModel.getBranchName().trim(),
                        "4006",
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
