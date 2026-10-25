package com.example.crud;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private final CustomerRepository repo;
    private final Grid<Customer> grid = new Grid<>(Customer.class);
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final Button addBtn = new Button("Add customer");
    private final Button deleteBtn = new Button("Delete selected");

    public MainView(CustomerRepository repo) {
        this.repo = repo;

        HorizontalLayout form = new HorizontalLayout(firstName, lastName, addBtn, deleteBtn);
        add(form, grid);

        grid.setColumns("id", "firstName", "lastName");
        listCustomers();

        addBtn.addClickListener(e -> {
            if (!firstName.getValue().isEmpty() && !lastName.getValue().isEmpty()) {
                repo.save(new Customer(firstName.getValue(), lastName.getValue()));
                firstName.clear();
                lastName.clear();
                listCustomers();
            }
        });

        deleteBtn.addClickListener(e -> {
            Customer selected = grid.asSingleSelect().getValue();
            if (selected != null) {
                repo.delete(selected);
                listCustomers();
            }
        });
    }

    private void listCustomers() {
        grid.setItems(repo.findAll());
    }
}