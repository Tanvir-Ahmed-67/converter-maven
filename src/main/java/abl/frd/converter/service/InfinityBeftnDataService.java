package abl.frd.converter.service;

import abl.frd.converter.helper.InfinityBeftnDataServiceHelper;
import abl.frd.converter.model.InfinityBeftnModel;
import abl.frd.converter.repository.InfinityBeftnModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class InfinityBeftnDataService {
    @Autowired
    InfinityBeftnModelRepository infinityBeftnModelRepository;

    public void save(MultipartFile file) {
        try
        {
            List<InfinityBeftnModel> infinityBeftnModels = InfinityBeftnDataServiceHelper.csvToInfinityBeftnModels(file.getInputStream());
            infinityBeftnModelRepository.saveAll(infinityBeftnModels);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<InfinityBeftnModel> infinityBeftnModelList = infinityBeftnModelRepository.findAll();
        ByteArrayInputStream in = InfinityBeftnDataServiceHelper.infinityBeftnModelToCSV(infinityBeftnModelList);
        return in;
    }

    public void clearDatabase(){
        truncateInfinityBeftnDataTable();
    }
    @Transactional
    public void truncateInfinityBeftnDataTable() {
        infinityBeftnModelRepository.truncateInfinityBeftnDataTable();
    }
}
