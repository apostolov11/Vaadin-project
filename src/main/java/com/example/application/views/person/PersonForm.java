package com.example.application.views.person;

import com.example.application.data.entity.Person;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;

public class PersonForm extends VerticalLayout {

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private boolean saved;

    private Person person;
    private BeanValidationBinder<Person> binder = new BeanValidationBinder<>(Person.class);
    private Button cancel;
    private Button save;


    public PersonForm(Person person) {
        this.person = person;
        init();
    }


    private void init()
    {
        setSizeFull();
        final FormLayout form = new FormLayout();
        form.add(firstName);
        form.add(lastName);
        form.add(email);
        binder.bindInstanceFields(this);
        binder.readBean(this.person);
        HorizontalLayout buttons = configureButtons();
        add(form, buttons);
    }


    private HorizontalLayout configureButtons()
    {
        save = new Button("Save", l -> {
            if (binder.isValid())
            {
                try
                {
                    binder.writeBean(person);
                    saved = true;
                }
                catch (ValidationException e)
                {
                    e.printStackTrace();
                }
            }
        });
        cancel = new Button("Cancel");
        final HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        return buttons;
    }


    public void addCancelClickListener(ComponentEventListener<ClickEvent<Button>> clickListener)
    {
        cancel.addClickListener(clickListener);
    }


    public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> clickListener)
    {
        save.addClickListener(clickListener);
    }


    public boolean isSaved()
    {
        return saved;
    }
}
