package abl.frd.converter.helper;

import abl.frd.converter.model.ApiDataModel;
import abl.frd.converter.model.BeftnDataModel;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
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
    public static List<BeftnDataModel> csvToBeftnModels(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<BeftnDataModel> beftnDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                BeftnDataModel beftnDataModel = new BeftnDataModel(
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
                beftnDataModelList.add(beftnDataModel);
            }
            return beftnDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream beftnModelToCSV(List<BeftnDataModel> beftnDataModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator("\r\n").withIgnoreEmptyLines(true);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (BeftnDataModel beftnDataModel : beftnDataModelList) {
                List<Object> data = Arrays.asList(
                        beftnDataModel.getExCode().trim(),
                        beftnDataModel.getTranNo().trim(),
                        beftnDataModel.getCurrency().trim(),
                        beftnDataModel.getAmount(),
                        beftnDataModel.getEnteredDate().trim(),
                        beftnDataModel.getBeneficiary().trim(),
                        beftnDataModel.getRemitter().trim(),
                        beftnDataModel.getBeneficiaryAccount().trim(),
                        beftnDataModel.getBankName().trim(),
                        "11",
                        beftnDataModel.getBranchName().trim(),
                        beftnDataModel.getBranchCode().trim(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        "0"
                );
                System.out.println(("<<<<<<<<<<<<<<<<<<<<<<<<<<"+beftnDataModelList));
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
