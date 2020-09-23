package ru.toolkas.converter.ui.pages.start;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.toolkas.converter.domain.Valute;
import ru.toolkas.converter.service.CurrencyConversionService;
import ru.toolkas.converter.ui.pages.BasePage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StartPage extends BasePage {
    @SpringBean
    private CurrencyConversionService currencyConversionService;

    public StartPage() throws IOException, JAXBException {
        List<Valute> valutes = new ArrayList<>(currencyConversionService.getTodayValutes());
        valutes.sort(Comparator.comparing(Valute::getCharCode));

        DropDownChoice<Valute> fromCurrency = new DropDownChoice<>("fromCurrency", valutes, new IChoiceRenderer<Valute>() {
            @Override
            public Object getDisplayValue(Valute valute) {
                return valute.getCharCode();
            }

            @Override
            public String getIdValue(Valute valute, int i) {
                return valute.getCbId();
            }

            @Override
            public Valute getObject(String id, IModel<? extends List<? extends Valute>> model) {
                return model.getObject().stream().filter(v -> v.getCbId().equals(id)).findAny().orElseThrow();
            }
        });
        add(fromCurrency);

        DropDownChoice<Valute> toCurrency = new DropDownChoice<>("toCurrency", valutes, new IChoiceRenderer<Valute>() {
            @Override
            public Object getDisplayValue(Valute valute) {
                return valute.getCharCode();
            }

            @Override
            public String getIdValue(Valute valute, int i) {
                return valute.getCbId();
            }

            @Override
            public Valute getObject(String id, IModel<? extends List<? extends Valute>> model) {
                return model.getObject().stream().filter(v -> v.getCbId().equals(id)).findAny().orElseThrow();
            }
        });
        add(toCurrency);
    }
}
