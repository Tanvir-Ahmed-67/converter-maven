package abl.frd.converter.helper;

import abl.frd.converter.model.TransfastDataModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
            Workbook records = new HSSFWorkbook(is);
            Sheet worksheet = records.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = worksheet.iterator();
            List<TransfastDataModel> transfastDataModelList = new ArrayList<>();
            Row row = null;
            rowIterator.next();
            int count=0;
            System.out.println("test.........1.......");
            while (rowIterator.hasNext()){
                if(count==0){
                    row = rowIterator.next();
                    count++;
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
                if(!eachCell.isEmpty()) {
                    transfastDataModel.setReferenceNo(eachCell.get(0));
                    System.out.println("test........2........");
                    /*
                    transfastDataModel.setCustomerNo(eachCell.get(1));
                    transfastDataModel.setRemitterName(eachCell.get(2));
                    transfastDataModel.setRemitterAccount(eachCell.get(3));
                    transfastDataModel.setRemitterAccountType(eachCell.get(4));
                    transfastDataModel.setBeneficiaryName(eachCell.get(5));
                    transfastDataModel.setBeneficiaryAccount(eachCell.get(6).replaceAll("\\.", ""));
                    transfastDataModel.setBeneficiaryAccountType(eachCell.get(7));
                    transfastDataModel.setRoutingNumber(eachCell.get(8));
*/
                    transfastDataModel.setCurrency(eachCell.get(9));
                    transfastDataModel.setAmount(Double.parseDouble(eachCell.get(10).replaceAll(",", "")));
                    transfastDataModelList.add(transfastDataModel);
                    eachCell.clear();
                    count++;
                }
            }
            System.out.println("test.......3.........");
            return transfastDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
