package com.example.application.views.book;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class BookGrid extends Grid<Book> {

    private final List<Book> books;
    private ListDataProvider<Book> bookListDataProvider;
    private Button deleteBook;
    private Grid<Book> grid;
    @Autowired
    private BookService bookService;

    public BookGrid(List<Book> books) {
        super(Book.class, false);
        this.books = books;
        init();
    }

    private void init() {
//        addBookButtons();

        final Column<Book> nameColumn = addColumn(Book::getBookName).setFrozen(true).setResizable(true).setSortable(true).setHeader("Book");
        addColumn(Book::getAuthorName).setResizable(true).setSortable(true).setHeader("Author");
        addColumn(Book::getGenre).setResizable(true).setSortable(true).setHeader("Genre");
        addColumn(Book::getRating).setResizable(true).setSortable(true).setHeader("Rating");


        addColumn(book -> {
            final Person person = book.getPerson();
            return person.getFirstName() + "/" + person.getLastName();
        }).setResizable(true).setSortable(true).setHeader("Person");

        HeaderRow headerRow = appendHeaderRow();
        final TextField nameFilterField = new TextField();
        nameFilterField.setPlaceholder("Type name to filter");
        nameFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilterField.setClearButtonVisible(true);
        nameFilterField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        nameFilterField.setWidthFull();
        nameFilterField.getStyle().set("max-width", "100%");
        nameFilterField.addValueChangeListener(
                e -> {
                    bookListDataProvider.clearFilters();
                    bookListDataProvider.addFilter(f -> {
                                final String name = f.getBookName().toLowerCase();
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
        refreshBookGrid(books);
    }

//    private void addBookButtons() {
//
//        deleteBook = new Button("Delete Book", el -> {
//            bookService.delete(grid.asSingleSelect().getValue().getId());
//            refreshGridData();
//        });
//        deleteBook.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        deleteBook.setEnabled(false);
//        final HorizontalLayout buttons = new HorizontalLayout(deleteBook);
//        buttons.add();
//
//    }
//    public void refreshGridData() {
//        grid.setItems(query -> bookService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//    }

    public void refreshBookGrid(List<Book> books) {
        bookListDataProvider = new ListDataProvider<>(books);
        this.setItems(bookListDataProvider);
    }
}
