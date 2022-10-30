package abl.frd.converter.repository;

import abl.frd.converter.model.ExchangeCodeMapperModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeCodeMapperModelRepository extends JpaRepository<ExchangeCodeMapperModel, Integer> {
}
