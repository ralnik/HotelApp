package ru.ralnik.core.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ralnik.core.db.entity.Reserved;

@Repository
public interface ReservedRepository extends JpaRepository<Reserved, Long> {
}
