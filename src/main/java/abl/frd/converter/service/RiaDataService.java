package abl.frd.converter.service;

import abl.frd.converter.helper.RiaDataServiceHelper;
import abl.frd.converter.model.RiaDataModel;
import abl.frd.converter.repository.RiaDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RiaDataService {
    @Autowired
    RiaDataModelRepository repository;

    public void save(MultipartFile file) {
        try
        {
            List<RiaDataModel> riaDataModels = RiaDataServiceHelper.csvToRiaDataModels(file.getInputStream());
            repository.saveAll(riaDataModels);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<RiaDataModel> riaDataModelList = repository.findAll();
        ByteArrayInputStream in = RiaDataServiceHelper.riaDataModelsToCSV(riaDataModelList);
        return in;
    }

    public void clearDatabase(){
        truncateRiaDataTable();
    }

    public List<RiaDataModel> getAllRiaDataModels() {
        return repository.findAll();
    }

    @Transactional
    public void truncateRiaDataTable() {
        repository.truncateRiaDataTable();
    }
}
