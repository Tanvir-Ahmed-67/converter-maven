package abl.frd.converter.repository;

import abl.frd.converter.model.EzRemitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EzRemitModelRepository extends JpaRepository<EzRemitModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table ez_remit_data_table",
            nativeQuery = true
    )
    void truncateEzRemitDataTable();
}
