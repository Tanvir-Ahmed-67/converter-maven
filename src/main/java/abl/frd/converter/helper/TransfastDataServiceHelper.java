package abl.frd.converter.helper;
import abl.frd.converter.model.TransfastDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
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
public class TransfastDataServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};
    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        }
        return false;
    }
    public static List<TransfastDataModel> csvToTransfastDataModels(InputStream is) {
        try{
            Workbook records = new XSSFWorkbook(is);
            Sheet worksheet = records.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = worksheet.iterator();
            List<TransfastDataModel> transfastDataModelList = new ArrayList<>();
            Row row = null;
            rowIterator.next();
            boolean flag= false;
            while (rowIterator.hasNext()){
                if(flag==false){
                    for(int i=0; i<4; i++){
                        // Ignoring first 5 rows. Data is started from 6th row. So we need to skip first 5th row
                        row = rowIterator.next();
                    }
                    flag=true;
                }

                TransfastDataModel transfastDataModel = new TransfastDataModel();
                row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> eachCell = new ArrayList<>();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    DataFormatter df = new DataFormatter();
                    eachCell.add(df.formatCellValue(cell));
                }
                if(!eachCell.isEmpty() && eachCell.size()==22) {
                    transfastDataModel.setInvoiceNo(eachCell.get(0));
                    transfastDataModel.setTfPin(eachCell.get(1));
                    transfastDataModel.setReferenceNo(eachCell.get(2));
                    transfastDataModel.setInvoiceDate(eachCell.get(3));
                    transfastDataModel.setPaidDate(eachCell.get(4));
                    transfastDataModel.setStatus(eachCell.get(5));
                    transfastDataModel.setRemitter(eachCell.get(6));
                    transfastDataModel.setBeneficiary(eachCell.get(7));
                    transfastDataModel.setBeneficiaryAccount(eachCell.get(9));
                    transfastDataModel.setBranchName(eachCell.get(10));
                    transfastDataModel.setBranchCode(eachCell.get(11));
                    transfastDataModel.setPayingBranchRoutingNo(eachCell.get(12));
                    transfastDataModel.setPayingBankBranchName(eachCell.get(13));
                    transfastDataModel.setBankName(eachCell.get(14));
                    transfastDataModel.setBeneficiaryState(eachCell.get(15));
                    transfastDataModel.setBeneficiaryCityName(eachCell.get(16));
                    transfastDataModel.setCashierName(eachCell.get(17));
                    transfastDataModel.setAmountDoller(Double.parseDouble(eachCell.get(18).replaceAll(",", "")));
                    transfastDataModel.setAmountLocal(Double.parseDouble(eachCell.get(19).replaceAll(",", "")));
                    transfastDataModel.setBeneficiaryPhone(eachCell.get(20));
                    transfastDataModel.setRemitterCountry(eachCell.get(21));

                    transfastDataModelList.add(transfastDataModel);
                    eachCell.clear();
                }
            }
            if(transfastDataModelList.isEmpty()){
                throw new IOException();
            }
            return transfastDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream transfastModelToCSV(List<TransfastDataModel> transfastDataModelList) {

           final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);
           try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
                   for (TransfastDataModel transfastDataModel : transfastDataModelList) {
                       List<Object> data = Arrays.asList(
                               "7040",
                               transfastDataModel.getTfPin().trim(),
                               "BDT",  // Currency
                               transfastDataModel.getAmountLocal(),
                               transfastDataModel.getPaidDate(),
                               transfastDataModel.getBeneficiary().trim(),
                               transfastDataModel.getRemitter().trim(),
                               transfastDataModel.getBeneficiaryAccount().trim(),
                               transfastDataModel.getBankName().trim(),
                               "11",   // Bank Code
                               transfastDataModel.getBranchName().trim(),
                               transfastDataModel.getPayingBranchRoutingNo().trim(),
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
