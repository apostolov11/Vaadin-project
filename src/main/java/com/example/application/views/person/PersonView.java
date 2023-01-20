package com.example.application.views.person;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
import com.example.application.data.service.SamplePersonService;
import com.example.application.views.MainLayout;
import com.example.application.views.book.BookForm;
import com.example.application.views.book.BookGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;


@PageTitle("Persons")
@Route(value = "person", layout = MainLayout.class)
@Uses(Icon.class)
public class PersonView extends Div {


    private final SamplePersonService personService;
    private final BookService bookService;
    private Button addButton;
    private Button editButton;
    private Button removeButton;
    private Grid<Person> grid;
    private BookGrid bookGrid;
    private Button addBook;
    private Button deleteBook;


    @Autowired
    public PersonView(SamplePersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
        init();
    }

    private void init() {
        add(new Label("Person"));
        addPersonButtons();
        addPersonGrid();
        addBook = new Button("Add book", el -> {
            final Book book = new Book();
            book.setPerson(grid.asSingleSelect().getValue());
            openBookForm(book);
        });
//        deleteBook = new Button("Delete Book", el -> {
//            Long personId = grid.asSingleSelect().getValue().getId();
//            Book book = bookService.findByPersonId(personId);
//
//            Long asd = 123L;
//            bookService.delete(book.getId());
//        });
        add(new HorizontalLayout(addBook));
        bookGrid = new BookGrid(new ArrayList<>());
        add(bookGrid);
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
            editButton.setEnabled(person != null);
            removeButton.setEnabled(person != null);
            addBook.setEnabled(person != null);
            refreshBookGridData();
        });
        add(grid);
    }

    private void addPersonButtons() {

        addButton = new Button("add", el -> {
            final Person person = new Person();
            openPersonForm(person);
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton = new Button("Change", el -> {
            final Person person = grid.asSingleSelect().getValue();
            openPersonForm(person);
        });
        editButton.setEnabled(false);
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton = new Button("Delete", el -> {
            final Dialog dialog = new Dialog();
            final Button yes = new Button("Yes", removeL -> {
                personService.delete(grid.asSingleSelect().getValue().getId());
                dialog.close();
                refreshGridData();
            });
            final Button no = new Button("No", removeL -> dialog.close());
            final HorizontalLayout removeDialogButtons = new HorizontalLayout(yes, no);
            dialog.setWidth("350px");
            dialog.setHeight("200px");
            dialog.add(new VerticalLayout(new H3("Do you wanna delete it?"), removeDialogButtons));
            dialog.open();
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton.setEnabled(false);
        final HorizontalLayout buttons = new HorizontalLayout(addButton, editButton, removeButton);
        add(buttons);
    }

    private void openPersonForm(Person person) {

        final Dialog dialog = new Dialog();
        final PersonForm personForm = new PersonForm(person);
        personForm.addCancelClickListener(closeL -> dialog.close());
        personForm.addSaveClickListener(saveL -> {
            if (personForm.isSaved()) {
                personService.update(person);
                dialog.close();
                refreshGridData();
            }
        });
        dialog.add(personForm);
        dialog.setHeight("450px");
        dialog.setWidth("300px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.open();
    }

    private void openBookForm(Book book) {

        final Dialog dialog = new Dialog();
        final BookForm bookForm = new BookForm(book);
        bookForm.addCancelClickListener(closeL -> dialog.close());
        bookForm.addSaveClickListener(saveL -> {
            if (bookForm.isSaved()) {
                bookService.update(book);
                dialog.close();
                refreshBookGridData();
            }
        });
        dialog.add(bookForm);
        dialog.setHeight("450px");
        dialog.setWidth("300px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.open();
    }

    private void refreshBookGridData() {
        bookGrid.refreshBookGrid(bookService.findByPerson(grid.asSingleSelect().getValue()));
    }
}
