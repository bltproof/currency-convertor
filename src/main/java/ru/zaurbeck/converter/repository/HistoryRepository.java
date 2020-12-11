package ru.zaurbeck.converter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zaurbeck.converter.domain.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
    Iterable<History> findAllByOrderByCreatedDesc();
}

