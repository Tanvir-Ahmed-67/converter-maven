package abl.frd.converter.repository;

import abl.frd.converter.model.ApiDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApiDataModelRepository extends JpaRepository<ApiDataModel, Integer>{
    @Modifying
    @Transactional
    @Query(
            value = "truncate table api_data_table",
            nativeQuery = true
    )
    void truncateApiDataTable();
}

