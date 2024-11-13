package abl.frd.converter.repository;

import abl.frd.converter.model.ShahGlobalDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShahGlobalDataModelRepository extends JpaRepository<ShahGlobalDataModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table shah_global_data_table",
            nativeQuery = true
    )
    void truncateShahGlobalDataTable();
}
