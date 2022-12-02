package abl.frd.converter.service;

import abl.frd.converter.helper.EzRemitDataServiceHelper;
import abl.frd.converter.helper.RiaDataServiceHelper;
import abl.frd.converter.model.EzRemitModel;
import abl.frd.converter.model.RiaDataModel;
import abl.frd.converter.repository.EzRemitModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EzRemitDataService {
    @Autowired
    EzRemitModelRepository ezRemitModelRepository;
    public void save(MultipartFile file) {
        try
        {
            List<EzRemitModel> ezRemitDataModels = EzRemitDataServiceHelper.csvToEzRemitDataModels(file.getInputStream());
            ezRemitModelRepository.saveAll(ezRemitDataModels);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<EzRemitModel> ezRemitDataModelList = ezRemitModelRepository.findAll();
        ByteArrayInputStream in = EzRemitDataServiceHelper.ezRemitDataModelsToCSV(ezRemitDataModelList);
        return in;
    }

    public void clearDatabase(){
        truncateEzRemitDataTable();
    }

    @Transactional
    public void truncateEzRemitDataTable() {
        ezRemitModelRepository.truncateEzRemitDataTable();
    }
}
