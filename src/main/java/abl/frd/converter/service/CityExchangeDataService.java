package abl.frd.converter.service;

import abl.frd.converter.helper.CityExchangeDataServiceHelper;
import abl.frd.converter.helper.TransfastDataServiceHelper;
import abl.frd.converter.model.CityExchangeDataModel;
import abl.frd.converter.model.TransfastDataModel;
import abl.frd.converter.repository.CityExchangeDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
@Service
public class CityExchangeDataService {
    @Autowired
    CityExchangeDataModelRepository cityExchangeDataModelRepository;

    public void clearDatabase(){
        truncateCityExchangeDataTable();
    }
    @Transactional
    public void truncateCityExchangeDataTable(){
        cityExchangeDataModelRepository.truncateCityExchangeDataTable();
    }
    public void save(MultipartFile file) {
        try
        {
            List<CityExchangeDataModel> cityExchangeDataModel = CityExchangeDataServiceHelper.csvToCityExchangeDataModels(file.getInputStream());
            cityExchangeDataModelRepository.saveAll(cityExchangeDataModel);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<CityExchangeDataModel> cityExchangeDataModelList = cityExchangeDataModelRepository.findAll();
        ByteArrayInputStream in = CityExchangeDataServiceHelper.cityExchangeModelToCSV(cityExchangeDataModelList);
        return in;
    }
}
