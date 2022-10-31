package abl.frd.converter.helper;

import abl.frd.converter.model.ApiDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BeftnDataServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};
    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        return false;
    }
    public static List<ApiDataModel> csvToBeftnModels(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<ApiDataModel> apiDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ApiDataModel apiDataModel = new ApiDataModel(
                        csvRecord.get("Excode").replace("\"", ""),
                        csvRecord.get("Tranno").replace("\"", ""),
                        csvRecord.get("Currency").replace("\"", ""),
                        Double.parseDouble(csvRecord.get("Amount").replace("\"", "")),
                        csvRecord.get("Entered Date").replace("\"", ""),
                        csvRecord.get("Remitter").replace("\"", ""),
                        csvRecord.get("Beneficiary").replace("\"", ""),
                        csvRecord.get("Bene A/C").replace("\"", ""),
                        csvRecord.get("Bank Code").replace("\"", ""),
                        csvRecord.get("Bank Name").replace("\"", ""),
                        csvRecord.get("Branch Name").replace("\"", ""),
                        csvRecord.get("Branch Code").replace("\"", ""));
                apiDataModelList.add(apiDataModel);
            }
            return apiDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
