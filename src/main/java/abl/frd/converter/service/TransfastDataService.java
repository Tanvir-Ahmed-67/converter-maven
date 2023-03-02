package abl.frd.converter.service;

import abl.frd.converter.helper.InfinityBeftnDataServiceHelper;
import abl.frd.converter.helper.TransfastDataServiceHelper;
import abl.frd.converter.model.InfinityBeftnModel;
import abl.frd.converter.model.TransfastDataModel;
import abl.frd.converter.repository.TransfastDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TransfastDataService {
    @Autowired
    TransfastDataModelRepository transfastDataModelRepository;
    public void clearDatabase(){
        truncateTransfastDataTable();
    }
    @Transactional
    public void truncateTransfastDataTable(){
        transfastDataModelRepository.truncateTransfastDataTable();
    }
    public void save(MultipartFile file) {
        try
        {
            List<TransfastDataModel> transfastDataModels = TransfastDataServiceHelper.csvToTransfastDataModels(file.getInputStream());
            transfastDataModelRepository.saveAll(transfastDataModels);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

}
