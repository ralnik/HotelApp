/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.core.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ralnik.core.db.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
