package com.example.application.views.main;

import com.example.application.model.Student;
import com.example.application.security.SecurityService;
import com.example.application.service.StudentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.Collections;

@PageTitle("Main")
@Route(value = "")
@PermitAll
public class MainView extends HorizontalLayout {

    Grid<Student> grid = new Grid<>(Student.class);
    TextField filterText = new TextField();
    StudentForm form;
    private StudentService service;
    private SecurityService security;

    public MainView(StudentService service, SecurityService security) {
        this.service = service;
        this.security = security;

        addClassName("MainPage");
        setSizeFull();
        configureForm();
        configureGrid();
        add(
                getToolbar(),
                getContent()
        );
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setStudent(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new StudentForm(service.findAll(String.valueOf(filterText)));
        form.setWidth("25em");
        form.addListener(StudentForm.SaveEvent.class, this::saveStudent);
        form.addListener(StudentForm.DeleteEvent.class, this::deleteStudent);
        form.addListener(StudentForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteStudent(StudentForm.DeleteEvent event){
        service.delete(event.getStudent());
        updateList();
        closeEditor();
    }

    private void saveStudent(StudentForm.SaveEvent event){
        service.add(event.getStudent());
        updateList();
        closeEditor();
    }


    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addStudentButton = new Button("Add Student");
        Button logOut = new Button("Log out", e -> security.logout());
        addStudentButton.addClickListener(e -> addStudent());
        HorizontalLayout toolbar = new HorizontalLayout((Component) addStudentButton, logOut);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addStudent() {
        grid.asSingleSelect().clear();
        editStudent(new Student());
    }

    private void configureGrid() {
        grid.addClassName("student-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(student -> student.getFirstName()).setHeader("First Name");
        grid.addColumn(student -> student.getLastName()).setHeader("Last Name");
        grid.addColumn(student -> student.getCpa()).setHeader("Cpa");
        grid.addColumn(student -> student.getGender()).setHeader("Gender");
        grid.addColumn(student -> student.getEmail()).setHeader("Email");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editStudent(e.getValue()));


    }

    private void editStudent(Student student) {
        if(student == null){
            closeEditor();
        } else{
            form.setStudent(student);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
