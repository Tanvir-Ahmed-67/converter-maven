package abl.frd.converter.repository;

import abl.frd.converter.model.InfinityBeftnModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InfinityBeftnModelRepository extends JpaRepository<InfinityBeftnModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table infinity_beftn_data_table",
            nativeQuery = true
    )
    void truncateInfinityBeftnDataTable();
}
