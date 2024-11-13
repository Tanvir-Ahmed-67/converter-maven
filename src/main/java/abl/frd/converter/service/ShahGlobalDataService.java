package abl.frd.converter.service;

import abl.frd.converter.helper.CityExchangeDataServiceHelper;
import abl.frd.converter.helper.ShahGlobalDataServiceHelper;
import abl.frd.converter.model.CityExchangeDataModel;
import abl.frd.converter.model.ShahGlobalDataModel;
import abl.frd.converter.repository.ShahGlobalDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ShahGlobalDataService {
    @Autowired
    ShahGlobalDataModelRepository shahGlobalDataModelRepository;
    public void clearDatabase(){
        truncateShahGlobalDataTable();
    }
    @Transactional
    public void truncateShahGlobalDataTable(){
        shahGlobalDataModelRepository.truncateShahGlobalDataTable();
    }
    public void save(MultipartFile file) {
        try{
            List<ShahGlobalDataModel> shahGlobalDataModel = ShahGlobalDataServiceHelper.csvToShahGlobalDataModels(file.getInputStream());
            shahGlobalDataModelRepository.saveAll(shahGlobalDataModel);
        }catch (IOException e){
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<ShahGlobalDataModel> shahGlobalDataModelList = shahGlobalDataModelRepository.findAll();
        ByteArrayInputStream in = ShahGlobalDataServiceHelper.shahGlobalDataModelToCSV(shahGlobalDataModelList);
        return in;
    }
}
