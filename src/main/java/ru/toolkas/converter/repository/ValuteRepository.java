package ru.toolkas.converter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.toolkas.converter.domain.Valute;

import java.util.Optional;

public interface ValuteRepository extends CrudRepository<Valute, Long> {
    Optional<Valute> findByName(String name);
}

