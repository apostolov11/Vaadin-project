package com.example.application.views.reminder;


import com.example.application.data.entity.Person;
import com.example.application.data.entity.Reminder;
import com.example.application.data.service.ReminderService;
import com.example.application.data.service.SamplePersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;


@PageTitle("Reminder")
@Route(value = "reminder", layout = MainLayout.class)
@Uses(Icon.class)
public class ReminderView extends Div {


    private final SamplePersonService personService;
    private final ReminderService reminderService;
    private Grid<Person> grid;
    private ReminderGrid reminderGrid;
    private Button addReminder;


    @Autowired
    public ReminderView(SamplePersonService personService, ReminderService reminderService) {
        this.personService = personService;
        this.reminderService = reminderService;
        init();
    }

    private void init() {
        add(new Label("Reminder"));
        addPersonGrid();
        addReminder = new Button("Add reminder", el -> {
            final Reminder reminder = new Reminder();
            reminder.setPerson(grid.asSingleSelect().getValue());
            openReminderForm(reminder);
        });

        add(new HorizontalLayout(addReminder));
        reminderGrid = new ReminderGrid(new ArrayList<>());
        add(reminderGrid);
        setSizeFull();
    }

    public void refreshGridData() {
        grid.setItems(query -> personService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    private void addPersonGrid() {

        grid = new Grid<>(Person.class, false);
        grid.addColumn(Person::getFirstName).setHeader("First name").setSortable(true).setFrozen(true).setResizable(true);
        grid.addColumn(Person::getLastName).setHeader("Last name").setSortable(true);
        grid.addColumn(Person::getEmail).setHeader("Email").setSortable(true);


        refreshGridData();
        grid.asSingleSelect().addValueChangeListener(el -> {
            final Person person = el.getValue();
            addReminder.setEnabled(person != null);
            refreshReminderGridData();
        });
        add(grid);
    }

    private void openReminderForm(Reminder reminder) {

        final Dialog dialog = new Dialog();
        final ReminderForm reminderForm = new ReminderForm(reminder);
        reminderForm.addCancelClickListener(closeL -> dialog.close());
        reminderForm.addSaveClickListener(saveL -> {
            if (reminderForm.isSaved()) {
                reminderService.update(reminder);
                dialog.close();
                refreshReminderGridData();
            }
        });
        dialog.add(reminderForm);
        dialog.setHeight("450px");
        dialog.setWidth("300px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.open();
    }

    private void refreshReminderGridData() {
        reminderGrid.refreshReminderGrid(reminderService.findByPerson(grid.asSingleSelect().getValue()));
    }

}
