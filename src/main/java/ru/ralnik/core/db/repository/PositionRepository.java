package ru.ralnik.core.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ralnik.core.db.entity.Positions;

@Repository
public interface PositionRepository extends JpaRepository<Positions, Long> {
}
