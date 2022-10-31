package abl.frd.converter.service;

import abl.frd.converter.helper.BeftnDataServiceHelper;
import abl.frd.converter.model.BeftnDataModel;
import abl.frd.converter.model.ExchangeCodeMapperModel;
import abl.frd.converter.repository.BeftnDataModelRepository;
import abl.frd.converter.repository.ExchangeCodeMapperModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeftnDataService {
    @Autowired
    BeftnDataModelRepository repository;
    @Autowired
    ExchangeCodeMapperModelRepository exchangeCodeMapperModelRepository;

    Map<String,String> exchangeCodeMappingForService= null;
    public Map<String,String> mapExchangeCode(){
        List<ExchangeCodeMapperModel> exchangeCodeMapperModels = loadExchangeCodeMapperModel();
        Map<String,String> exchangeCodeMapping = new HashMap<String,String>();
        for(ExchangeCodeMapperModel exchangeCodeMapperModel: exchangeCodeMapperModels){
            exchangeCodeMapping.put(exchangeCodeMapperModel.getNrta(),exchangeCodeMapperModel.getExCode());
        }
        return exchangeCodeMapping;
    }
    public void save(MultipartFile file) {
        try
        {
            List<BeftnDataModel> beftnModels = BeftnDataServiceHelper.csvToBeftnModels(file.getInputStream());
            exchangeCodeMappingForService = mapExchangeCode();
            for(BeftnDataModel beftnDataModel : beftnModels){
                beftnDataModel.setExCode(exchangeCodeMappingForService.get(beftnDataModel.getExCode()).trim());
            }
            repository.saveAll(beftnModels);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<BeftnDataModel> beftnModels = repository.findAll();
        ByteArrayInputStream in = BeftnDataServiceHelper.beftnModelToCSV(beftnModels);
        return in;
    }

    public List<ExchangeCodeMapperModel> loadExchangeCodeMapperModel() {
        return exchangeCodeMapperModelRepository.findAll();
    }
    public List<BeftnDataModel> getAllBeftnModels() {
        return repository.findAll();
    }

    public void clearDatabase(){
        truncateBeftnDataTable();
        // truncateHibernetSequenceTable();
        truncateBeftnSeqTable();
        initializeBeftnSeqTable();
    }
    @Transactional
    public void truncateBeftnDataTable() {
        repository.truncateBeftnDataTable();
    }
    @Transactional
    public void truncateHibernetSequenceTable() {
        repository.truncateHibernateSequenceTable();
    }
    @Transactional
    public void truncateBeftnSeqTable() {
        repository.truncateBeftnSeqTable();
    }
    @Transactional
    public void initializeBeftnSeqTable() {
        repository.initializeBeftnSeqTable();
    }
}
