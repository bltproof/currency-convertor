package ru.toolkas.converter.service.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCursDto {
    @XmlAttribute
    public String name;

    @XmlAttribute(name = "Date")
    public String date;

    @XmlElement(name = "Valute")
    public List<ValuteDto> valutes;
}
