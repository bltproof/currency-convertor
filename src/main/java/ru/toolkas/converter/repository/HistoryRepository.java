package ru.toolkas.converter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.toolkas.converter.domain.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
    Iterable<History> findAllByOrderByCreatedDesc();
}

