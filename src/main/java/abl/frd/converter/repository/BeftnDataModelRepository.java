package abl.frd.converter.repository;

import abl.frd.converter.model.BeftnDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BeftnDataModelRepository extends JpaRepository<BeftnDataModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table beftn_data_table",
            nativeQuery = true
    )
    void truncateBeftnDataTable();
    @Modifying
    @Transactional
    @Query(
            value = "truncate table hibernate_sequence",
            nativeQuery = true
    )
    void truncateHibernateSequenceTable();
    @Modifying
    @Transactional
    @Query(
            value = "truncate table beftn_seq",
            nativeQuery = true
    )
    void truncateBeftnSeqTable();
    @Modifying
    @Transactional
    @Query(
            value = "insert into beftn_seq values('1')",
            nativeQuery = true
    )
    void initializeBeftnSeqTable();
}
