package com.example.application.views.book;

import com.example.application.data.entity.Book;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;

public class BookForm extends VerticalLayout {

    private TextField bookName = new TextField("Book");
    private TextField authorName = new TextField("Author");
    private TextField genre = new TextField("Genre");
    private TextField rating = new TextField("Rating");
    @PropertyId("firstName")
    private final TextField person = new TextField("Person");
    private boolean saved;

    private Book book;
    private BeanValidationBinder<Book> binder = new BeanValidationBinder<>(Book.class);
    private Button cancel;
    private Button save;

    public BookForm(Book book) {
        this.book = book;
        init();
    }

    private void init() {
        setSizeFull();
        final FormLayout form = new FormLayout();
        form.add(bookName);
        form.add(authorName);
        form.add(genre);
        form.add(rating);
        form.add(person);
        binder.bindInstanceFields(this);
        person.setValue(book.getPerson().getFirstName());
        person.setReadOnly(true);
        binder.readBean(this.book);
        HorizontalLayout buttons = configureButtons();
        add(form, buttons);
    }

    private HorizontalLayout configureButtons() {
        save = new Button("Save", l -> {
            if (binder.isValid()) {
                try {
                    binder.writeBean(book);
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
