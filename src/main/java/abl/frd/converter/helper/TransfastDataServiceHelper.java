package abl.frd.converter.helper;
import abl.frd.converter.model.TransfastDataModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
            Workbook records = new XSSFWorkbook(is);
            Sheet worksheet = records.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = worksheet.iterator();
            List<TransfastDataModel> transfastDataModelList = new ArrayList<>();
            Row row = null;
            rowIterator.next();
            System.out.println("Servicehelper.........1.......");
            int count=0;
            while (rowIterator.hasNext()){
                if(count<=5) {
                    // ignore first 5 rows. Data is started from 6th row. So we need to skip first 5th row
                    for (int i = 0; i < 5; i++) {
                        row = rowIterator.next();
                        count++;
                        System.out.println(count);
                    }
                }
                TransfastDataModel transfastDataModel = new TransfastDataModel();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> eachCell = new ArrayList<>();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    DataFormatter df = new DataFormatter();
                    eachCell.add(df.formatCellValue(cell));
                }
                if(!eachCell.isEmpty()) {
                    transfastDataModel.setTfPin(eachCell.get(0));
                    transfastDataModel.setReferenceNo(eachCell.get(1));
                    transfastDataModel.setInvoiceNo(eachCell.get(2));
                    transfastDataModel.setAmount(Double.parseDouble(eachCell.get(3).replaceAll(",", "")));
                    transfastDataModel.setCurrency(eachCell.get(4));
                    transfastDataModel.setEnteredDate(eachCell.get(5));

                    transfastDataModel.setRemitter(eachCell.get(6));
                    transfastDataModel.setRemitterId(eachCell.get(7));
                    transfastDataModel.setRemitterCountryName(eachCell.get(8));
                    transfastDataModel.setRemitterCityName(eachCell.get(9));
                    transfastDataModel.setRemitterAdress(eachCell.get(10));

                    transfastDataModel.setBeneficiary(eachCell.get(12));
                    transfastDataModel.setBeneficiaryAdress(eachCell.get(13));
                    transfastDataModel.setBeneficiaryCityName(eachCell.get(14));
                    transfastDataModel.setBeneficiaryCountryName(eachCell.get(15));
                    transfastDataModel.setBeneficiaryPhone(eachCell.get(16));

                    transfastDataModel.setBranchCode(eachCell.get(17));
                    transfastDataModel.setBranchName(eachCell.get(18));
                    transfastDataModel.setBankName(eachCell.get(19));
                    transfastDataModel.setBeneficiaryAccount(eachCell.get(20));

                    transfastDataModelList.add(transfastDataModel);
                    eachCell.clear();
                }
            }
            System.out.println("service helper........2........"+transfastDataModelList.toString());
            return transfastDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
