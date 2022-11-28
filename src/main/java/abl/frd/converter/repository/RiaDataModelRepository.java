package abl.frd.converter.repository;

import abl.frd.converter.model.RiaDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RiaDataModelRepository extends JpaRepository<RiaDataModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table ria_data_table",
            nativeQuery = true
    )
    void truncateRiaDataTable();

}

