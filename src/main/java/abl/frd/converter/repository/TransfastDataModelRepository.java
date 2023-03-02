package abl.frd.converter.repository;

import abl.frd.converter.model.TransfastDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TransfastDataModelRepository extends JpaRepository<TransfastDataModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table transfast_data_table",
            nativeQuery = true
    )
    void truncateTransfastDataTable();
}
