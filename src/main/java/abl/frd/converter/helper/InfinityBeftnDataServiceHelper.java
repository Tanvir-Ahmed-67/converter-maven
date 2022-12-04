package abl.frd.converter.helper;

import abl.frd.converter.model.InfinityBeftnModel;
import org.apache.commons.csv.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class InfinityBeftnDataServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};
    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        return false;
    }
    public static List<InfinityBeftnModel> csvToInfinityBeftnModels(InputStream is) {
        try{
             Workbook records = new HSSFWorkbook(is);
             Sheet worksheet = records.getSheetAt(0);
            //Iterate through each rows one by one
             Iterator<Row> rowIterator = worksheet.iterator();
            List<InfinityBeftnModel> infinityBeftnModelList = new ArrayList<>();
            Row row = null;
            rowIterator.next();
            int count=0;
            while (rowIterator.hasNext()){
                if(count==0){
                    row = rowIterator.next();
                    count++;
                }
                InfinityBeftnModel infinityBeftnModel = new InfinityBeftnModel();
                row = rowIterator.next();

                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> eachCell = new ArrayList<>();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    DataFormatter df = new DataFormatter();
                    eachCell.add(df.formatCellValue(cell));
                }
                infinityBeftnModel.setTranNo(eachCell.get(0));
                infinityBeftnModel.setCustomerNo(eachCell.get(1));
                infinityBeftnModel.setRemitterName(eachCell.get(2));
                infinityBeftnModel.setRemitterAccount(eachCell.get(3));
                infinityBeftnModel.setRemitterAccountType(eachCell.get(4));
                infinityBeftnModel.setBeneficiaryName(eachCell.get(5));
                infinityBeftnModel.setBeneficiaryAccount(eachCell.get(6).replaceAll(".",""));
                infinityBeftnModel.setBeneficiaryAccountType(eachCell.get(7));
                infinityBeftnModel.setRoutingNumber(eachCell.get(8));
                infinityBeftnModel.setCurrency(eachCell.get(9));
                infinityBeftnModel.setAmount(Double.parseDouble(eachCell.get(10).replaceAll(",","")));

                infinityBeftnModelList.add(infinityBeftnModel);
                eachCell.clear();
                count++;
            }
            return infinityBeftnModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream infinityBeftnModelToCSV(List<InfinityBeftnModel> infinityBeftnModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (InfinityBeftnModel infinityBeftnModel : infinityBeftnModelList) {
                List<Object> data = Arrays.asList(
                        infinityBeftnModel.getTranNo(),
                        "CRED",
                        infinityBeftnModel.getEnteredDate(),
                        infinityBeftnModel.getCurrency(),
                        infinityBeftnModel.getAmount(),
                        infinityBeftnModel.getRemitterName(),
                        "7010228",
                        null,
                        null,
                        null,
                        infinityBeftnModel.getBeneficiaryAccount(),
                        infinityBeftnModel.getBeneficiaryName(),
                        null,
                        null,
                        infinityBeftnModel.getRoutingNumber(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
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
