package abl.frd.converter.repository;

import abl.frd.converter.model.CityExchangeDataModel;
import abl.frd.converter.model.TransfastDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface CityExchangeDataModelRepository extends JpaRepository<CityExchangeDataModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table city_exchange_data_table",
            nativeQuery = true
    )
    void truncateCityExchangeDataTable();
}
