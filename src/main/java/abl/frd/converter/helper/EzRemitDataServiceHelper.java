package abl.frd.converter.helper;

import abl.frd.converter.model.EzRemitModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EzRemitDataServiceHelper {
    public static String TYPE = "text/csv";
    public static Logger logger = LoggerFactory.getLogger(EzRemitDataServiceHelper.class);
    public static boolean hasCSVFormat(MultipartFile file) {
        logger.info("Given File Format: "+file.getContentType());
        if (file.getContentType().equals("text/csv") || file.getContentType().equals("application/csv") || file.getContentType().equals("application/vnd.ms-excel")) {
            logger.info("File format checked and passed successfully");
            return true;
        }
        return false;
    }

    public static List<EzRemitModel> csvToEzRemitDataModels(InputStream is) {
        logger.info("Inside csvToEzRemitDataModels(InputStream is) method");
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<EzRemitModel> ezRemitDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                logger.info("Inside FOR LOOP. Iterating one by one data row"+csvRecord);
                EzRemitModel ezRemitModel = new EzRemitModel(
                        csvRecord.get("Reference Number").replace("\"", ""),
                        Double.parseDouble(csvRecord.get("Paying Amount").replace("\"", "")),
                        csvRecord.get("ENTERED DATE").replace("\"", ""),
                        csvRecord.get("Remitter_Name").replace("\"", ""),
                        csvRecord.get("Beneficiary Name").replace("\"", ""),
                        csvRecord.get("Beneficiary account").replace("\"", ""),
                        csvRecord.get("Remitter Address").replace("\"", ""),
                        csvRecord.get("Remitter Country").replace("\"", ""));
                ezRemitDataModelList.add(ezRemitModel);
            }
            logger.info("FOR LOOP execution successfull. ezRemitDataModelList generated successfully: "+ezRemitDataModelList);
            return ezRemitDataModelList;
        } catch (IOException e) {
            logger.error("csvToEzRemitDataModels(InputStream is) method execution created exception: "+e);
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream ezRemitDataModelsToCSV(List<EzRemitModel> ezRemitDataModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator("\r\n").withIgnoreEmptyLines(true);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (EzRemitModel ezRemitDataModel : ezRemitDataModelList) {
                List<Object> data = Arrays.asList(
                        "7040",
                        ezRemitDataModel.getTranNo().trim(),
                        "BDT",
                        ezRemitDataModel.getAmount(),
                        ezRemitDataModel.getEnteredDate().trim(),
                        ezRemitDataModel.getBeneficiaryName().trim(),
                        ezRemitDataModel.getRemitterName().trim(),
                        ezRemitDataModel.getBeneficiaryAccount().trim(),
                        "AGRANI BANK LTD.",
                        "11",
                        "Principal Br",
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
            logger.error("Error occured. Failed to import data to CSV file"+e);
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
