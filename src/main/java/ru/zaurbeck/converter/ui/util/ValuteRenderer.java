package ru.zaurbeck.converter.ui.util;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import ru.zaurbeck.converter.domain.Valute;

import java.util.List;

public class ValuteRenderer implements IChoiceRenderer<Valute> {
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
}
