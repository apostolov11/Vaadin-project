package com.example.application.views.reminder;


import com.example.application.data.entity.Reminder;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;

public class ReminderForm extends VerticalLayout {

    private TextField text = new TextField("Text");
    private DatePicker date = new DatePicker("Select a date:");
    @PropertyId("firstName")
    private final TextField person = new TextField("Person");

    private boolean saved;

    private Reminder reminder;
    private BeanValidationBinder<Reminder> binder = new BeanValidationBinder<>(Reminder.class);
    private Button cancel;
    private Button save;


    public ReminderForm (Reminder reminder) {
        this.reminder = reminder;
        init();
    }

    private void init() {
        setSizeFull();
        final FormLayout form = new FormLayout();
        form.add(text);
        form.add(date);
        binder.bindInstanceFields(this);
        person.setValue(reminder.getPerson().getFirstName());
        person.setReadOnly(true);
        binder.readBean(this.reminder);
        HorizontalLayout buttons = configureButtons();
        add(form, buttons);
    }

    private HorizontalLayout configureButtons() {
        save = new Button("Save", l -> {
            if (binder.isValid()) {
                try {
                    binder.writeBean(reminder);
                    saved = true;
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }
        });
        cancel = new Button("Cancel");
        final HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        return buttons;
    }

    public void addCancelClickListener(ComponentEventListener<ClickEvent<Button>> clickListener) {
        cancel.addClickListener(clickListener);
    }


    public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> clickListener) {
        save.addClickListener(clickListener);
    }


    public boolean isSaved() {
        return saved;
    }
}
