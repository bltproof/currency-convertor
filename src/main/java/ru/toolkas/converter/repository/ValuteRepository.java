package ru.toolkas.converter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.toolkas.converter.domain.Valute;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ValuteRepository extends CrudRepository<Valute, Long> {
    Optional<Valute> findByCharCode(String charCode);

    List<Valute> findByPublished(LocalDate published);

    Optional<Valute> findByCharCodeAndPublished(String charCode, LocalDate published);
}

