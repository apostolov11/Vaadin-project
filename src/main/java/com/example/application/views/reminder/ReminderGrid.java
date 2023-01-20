package com.example.application.views.reminder;


import com.example.application.data.entity.Person;
import com.example.application.data.entity.Reminder;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ReminderGrid extends Grid<Reminder> {


    private final List<Reminder> reminders;
    private ListDataProvider<Reminder> reminderListDataProvider;


    public ReminderGrid(List<Reminder> reminders) {
        super(Reminder.class, false);
        this.reminders = reminders;
        init();
    }

    private void init() {
        final Column<Reminder> nameColumn = addColumn(Reminder::getText).setFrozen(true).setResizable(true).setSortable(true).setHeader("Text");
        addColumn(Reminder::getDate).setResizable(true).setSortable(true).setHeader("Date");

        addColumn(reminder -> {
            final Person person = reminder.getPerson();
            return person.getFirstName() + "/" + person.getLastName();
        }).setResizable(true).setSortable(true).setHeader("Person");

        HeaderRow headerRow = appendHeaderRow();
        final TextField nameFilterField = new TextField();
        nameFilterField.setPlaceholder("Type date to filter");
        nameFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilterField.setClearButtonVisible(true);
        nameFilterField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        nameFilterField.setWidthFull();
        nameFilterField.getStyle().set("max-width", "100%");
        nameFilterField.addValueChangeListener(
                e -> {
                    reminderListDataProvider.clearFilters();
                    reminderListDataProvider.addFilter(f -> {
                                final String name = f.getDate().toString();
                                final String value = e.getValue();
                                if (StringUtils.isNotBlank(value)) {
                                    return name.contains(value.toLowerCase());
                                }
                                return true;
                            }
                    );
                }
        );
        headerRow.getCell(nameColumn).setComponent(nameFilterField);
        refreshReminderGrid(reminders);
    }

    public void refreshReminderGrid(List<Reminder> reminders) {
        reminderListDataProvider = new ListDataProvider<>(reminders);
        this.setItems(reminderListDataProvider);
    }

}
