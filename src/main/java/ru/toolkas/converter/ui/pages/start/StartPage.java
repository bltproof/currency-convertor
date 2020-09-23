package ru.toolkas.converter.ui.pages.start;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.toolkas.converter.domain.Valute;
import ru.toolkas.converter.service.CurrencyConversionService;
import ru.toolkas.converter.ui.pages.BasePage;
import ru.toolkas.converter.ui.util.ValuteRenderer;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StartPage extends BasePage {
    @SpringBean
    private CurrencyConversionService currencyConversionService;

    public StartPage() throws IOException, JAXBException {
        add(new ConverterForm("converter-form"));
    }

    private class ConverterForm extends Form<Void> {
        private BigDecimal fromAmount;
        private Valute from;
        private Valute to;
        private String result;

        public ConverterForm(String id) throws IOException, JAXBException {
            super(id);

            List<Valute> valutes = new ArrayList<>(currencyConversionService.getTodayValutes());
            valutes.sort(Comparator.comparing(Valute::getCharCode));

            add(new DropDownChoice<>("fromCurrency", new PropertyModel<Valute>(this, "from"), valutes, new ValuteRenderer()));
            add(new DropDownChoice<>("toCurrency", new PropertyModel<Valute>(this, "to"), valutes, new ValuteRenderer()));
            add(new NumberTextField<BigDecimal>("fromAmount", new PropertyModel<>(this, "fromAmount")));

            add(new Label("result", new PropertyModel<>(this, "result")));
        }

        @Override
        protected void onSubmit() {
            super.onSubmit();

            BigDecimal toAmount = currencyConversionService.convert(from, fromAmount, to);
            result = new DecimalFormat("##").format(fromAmount) + " " + from.getCharCode() + " = " + new DecimalFormat("##.0000").format(toAmount) + " " + to.getCharCode();
        }
    }
}
