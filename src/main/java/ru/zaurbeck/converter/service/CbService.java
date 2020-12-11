package ru.zaurbeck.converter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.zaurbeck.converter.service.model.ValCursDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@PropertySource("classpath:cp.properties")
public class CbService {
    @Value("${url}")
    private String cbUrl;

    public ValCursDto fetchValutes() throws IOException, JAXBException {
        String todayUrl = cbUrl + "?date_req=" + new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        JAXBContext jaxbContext = JAXBContext.newInstance(ValCursDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (ValCursDto) jaxbUnmarshaller.unmarshal(new URL(todayUrl));
    }
}
