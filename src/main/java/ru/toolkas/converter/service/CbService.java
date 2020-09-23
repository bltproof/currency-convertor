package ru.toolkas.converter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.toolkas.converter.service.model.ValCursDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;

@Service
@PropertySource("classpath:cp.properties")
public class CbService {
    @Value("${url}")
    private String cbUrl;

    public ValCursDto fetchValutes() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCursDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (ValCursDto) jaxbUnmarshaller.unmarshal(new URL(cbUrl));
    }
}
