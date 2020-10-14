package ru.toolkas.converter.ui.pages.start;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.toolkas.converter.domain.History;
import ru.toolkas.converter.domain.Valute;
import ru.toolkas.converter.repository.HistoryRepository;
import ru.toolkas.converter.service.CurrencyConversionService;
import ru.toolkas.converter.ui.pages.BasePage;
import ru.toolkas.converter.ui.util.ValuteRenderer;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StartPage extends BasePage {
    @SpringBean
    private CurrencyConversionService currencyConversionService;

    @SpringBean
    private HistoryRepository historyRepository;

    public StartPage() throws IOException, JAXBException {
        RepeatingView historyView = new RepeatingView("history");
        add(new ConverterForm("converter-form", historyView));
        add(historyView);

        updateHistoryView(historyView);
    }

    private void updateHistoryView(RepeatingView historyView) {
        historyView.removeAll();

        int index = 0;
        for (History history : historyRepository.findAllByOrderByCreatedDesc()) {
            AbstractItem item = new AbstractItem(historyView.newChildId());
            historyView.add(item);

            index++;

            item.add(new Label("index", index));
            item.add(new Label("fromCurrency", history.getFrom().getCharCode()));
            item.add(new Label("fromAmount", history.getFromAmount()));
            item.add(new Label("toCurrency", history.getTo().getCharCode()));
            item.add(new Label("toAmount", history.getToAmount()));
        }
    }

    private class ConverterForm extends Form<Void> {
        private static final long serialVersionUID = -3911553377789246770L;

        private BigDecimal fromAmount;
        private Valute from;
        private Valute to;
        private String result;

        private final RepeatingView historyView;

        public ConverterForm(String id, RepeatingView historyView) throws IOException, JAXBException {
            super(id);

            this.historyView = historyView;

            List<Valute> valutes = new ArrayList<>(currencyConversionService.getTodayValutes());
            valutes.sort(Comparator.comparing(Valute::getCharCode));

            add(new ValuteChoice("fromCurrency", new PropertyModel<>(this, "from"), valutes, new ValuteRenderer()));
            add(new ValuteChoice("toCurrency", new PropertyModel<>(this, "to"), valutes, new ValuteRenderer()));
            add(new NumberTextField<BigDecimal>("fromAmount", new PropertyModel<>(this, "fromAmount")));

            add(new Label("result", new PropertyModel<>(this, "result")));
        }

        @Override
        protected void onSubmit() {
            super.onSubmit();

            BigDecimal toAmount = currencyConversionService.convert(from, fromAmount, to);
            result = fromAmount + " " + from.getCharCode() + " = " + toAmount.setScale(4, RoundingMode.HALF_UP) + " " + to.getCharCode();

            updateHistoryView(historyView);
        }
    }

    private static class ValuteChoice extends DropDownChoice<Valute> {
        private static final long serialVersionUID = 8091192321910887352L;

        public ValuteChoice(String id, IModel<Valute> model, List<? extends Valute> choices, IChoiceRenderer<? super Valute> renderer) {
            super(id, model, choices, renderer);
        }

        @Override
        protected String getNullKeyDisplayValue() {
            return "Валюта";
        }
    }
}
