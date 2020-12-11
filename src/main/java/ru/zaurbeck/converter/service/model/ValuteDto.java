package ru.zaurbeck.converter.service.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ValuteDto {
    @XmlAttribute(name = "ID")
    public String id;

    @XmlElement(name = "NumCode")
    public int numCode;

    @XmlElement(name = "CharCode")
    public String charCode;

    @XmlElement(name = "Nominal")
    public int nominal;

    @XmlElement(name = "Name")
    public String name;

    @XmlElement(name = "Value")
    public String value;
}
