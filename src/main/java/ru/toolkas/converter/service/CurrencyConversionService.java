package ru.toolkas.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.toolkas.converter.domain.History;
import ru.toolkas.converter.domain.Valute;
import ru.toolkas.converter.repository.HistoryRepository;
import ru.toolkas.converter.repository.ValuteRepository;
import ru.toolkas.converter.service.model.ValCursDto;
import ru.toolkas.converter.service.model.ValuteDto;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CurrencyConversionService {
    @Autowired
    private CbService cbService;

    @Autowired
    private ValuteRepository valuteRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    @PostConstruct
    public void refreshTodayValutes() throws IOException, JAXBException {
        ValCursDto valCurs = cbService.fetchValutes();
        LocalDate published = LocalDate.parse(valCurs.date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        for (ValuteDto valuteDto : valCurs.valutes) {
            if (valuteRepository.findByCharCodeAndPublished(valuteDto.charCode, LocalDate.now()).isEmpty()) {
                Valute valute = new Valute();
                valute.setCbId(valuteDto.id);
                valute.setCharCode(valuteDto.charCode);
                valute.setNumCode(valuteDto.numCode);
                valute.setName(valuteDto.name);
                valute.setValue(new BigDecimal(valuteDto.value.replaceAll(",", ".")));
                valute.setNominal(valuteDto.nominal);

                valute.setPublished(published);

                valuteRepository.save(valute);
            }
        }
    }

    @Transactional
    public BigDecimal convert(Valute from, BigDecimal fromAmount, Valute to) {
        BigDecimal result = fromAmount
                .multiply(from.getValue())
                .multiply(BigDecimal.valueOf(to.getNominal()))
                .divide(to.getValue().multiply(BigDecimal.valueOf(from.getNominal())),
                        MathContext.DECIMAL128
                );

        History history = new History();
        history.setCreated(LocalDateTime.now());
        history.setFrom(from);
        history.setFromAmount(fromAmount);
        history.setTo(to);
        history.setToAmount(result);

        historyRepository.save(history);

        return result;
    }

    public List<Valute> getTodayValutes() throws IOException, JAXBException {
        List<Valute> valutes = valuteRepository.findByPublished(LocalDate.now());
        if (valutes.isEmpty()) {
            refreshTodayValutes();
            return getTodayValutes();
        }

        return valutes;
    }
}
