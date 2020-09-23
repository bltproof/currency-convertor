package ru.toolkas.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.toolkas.converter.domain.Valute;
import ru.toolkas.converter.repository.ValuteRepository;
import ru.toolkas.converter.service.model.ValCursDto;
import ru.toolkas.converter.service.model.ValuteDto;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CurrencyConversionService {
    @Autowired
    private CbService cbService;

    @Autowired
    private ValuteRepository valuteRepository;

    @PostConstruct
    public void refreshTodayValutes() throws IOException, JAXBException {
        ValCursDto valCurs = cbService.fetchValutes();
        LocalDate published = LocalDate.parse(valCurs.date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        for(ValuteDto valuteDto: valCurs.valutes) {
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

    public BigDecimal convert(String fromName, BigDecimal fromAmount, String toName) {
        Optional<Valute> fromOpt = valuteRepository.findByName(fromName);
        Optional<Valute> toOpt = valuteRepository.findByName(toName);

        Valute from = fromOpt.orElseThrow();
        Valute to = toOpt.orElseThrow();

        return fromAmount.multiply(from.getValue()).multiply(BigDecimal.valueOf(from.getNominal())).divide(to.getValue().multiply(BigDecimal.valueOf(to.getNominal())), MathContext.DECIMAL128);

    }
}
