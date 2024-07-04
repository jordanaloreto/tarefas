package com.jordana.application.views.lista;

import com.jordana.application.controller.TarefaController;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.notification.Notification;

import java.util.List;

@PageTitle("Lista")
@Menu(icon = "line-awesome/svg/bookmark-solid.svg", order = 0)
@Route(value = "")
@RouteAlias(value = "")
public class ListaView extends Composite<VerticalLayout> {

    private final TarefaController tarefaController;

    public ListaView() {
        tarefaController = new TarefaController();

        FormLayout formLayout3Col = new FormLayout();
        ComboBox<String> comboBoxCategoria = new ComboBox<>();
        ComboBox<String> comboBoxResponsavel = new ComboBox<>();
        TextField textFieldTarefa = new TextField();
        DatePicker datePickerData = new DatePicker();
        RadioButtonGroup<String> radioGroupPrioridade = new RadioButtonGroup<>();
        CheckboxGroup<String> checkboxGroupStatus = new CheckboxGroup<>();
        Button buttonSave = new Button("Save");
        Button buttonEdit = new Button("Edit");
        Button buttonDelete = new Button("Delete");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        formLayout3Col.setWidth("100%");
        formLayout3Col.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("250px", 2),
                new ResponsiveStep("500px", 3));

        comboBoxCategoria.setLabel("Categoria");
        comboBoxCategoria.setWidth("min-content");
        setComboBoxCategoriaData(comboBoxCategoria);

        comboBoxResponsavel.setLabel("Responsável");
        comboBoxResponsavel.setWidth("min-content");
        setComboBoxResponsavelData(comboBoxResponsavel);

        textFieldTarefa.setLabel("Tarefa");
        textFieldTarefa.setWidth("min-content");

        datePickerData.setLabel("Data da Tarefa");
        datePickerData.setWidth("min-content");

        radioGroupPrioridade.setLabel("Prioridade");
        radioGroupPrioridade.setWidth("min-content");
        radioGroupPrioridade.setItems("Alta", "Média", "Baixa");
        radioGroupPrioridade.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        checkboxGroupStatus.setLabel("Status");
        checkboxGroupStatus.setWidth("min-content");
        checkboxGroupStatus.setItems("Finalizado", "Não começado", "Em andamento");
        checkboxGroupStatus.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        buttonSave.setWidth("min-content");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSave.addClickListener(e -> saveTarefa(comboBoxCategoria.getValue(), comboBoxResponsavel.getValue(), 
                                            textFieldTarefa.getValue(), datePickerData.getValue().toString(), 
                                            radioGroupPrioridade.getValue(), 
                                            String.join(", ", checkboxGroupStatus.getValue())));


        buttonEdit.setWidth("min-content");
        buttonEdit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonDelete.setWidth("min-content");
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        getContent().add(formLayout3Col);
        formLayout3Col.add(comboBoxCategoria);
        formLayout3Col.add(comboBoxResponsavel);
        formLayout3Col.add(textFieldTarefa);
        formLayout3Col.add(datePickerData);
        formLayout3Col.add(radioGroupPrioridade);
        formLayout3Col.add(checkboxGroupStatus);
        formLayout3Col.add(buttonSave);
        formLayout3Col.add(buttonEdit);
        formLayout3Col.add(buttonDelete);
    }

    private void setComboBoxCategoriaData(ComboBox<String> comboBox) {
        List<String> categorias = tarefaController.getAllCategorias();
        comboBox.setItems(categorias);
    }

    private void setComboBoxResponsavelData(ComboBox<String> comboBox) {
        List<String> responsaveis = tarefaController.getAllResponsaveis();
        comboBox.setItems(responsaveis);
    }

    private void saveTarefa(String categoria, String responsavel, String descricao, String data,
                        String prioridade, String status) {
    boolean success = tarefaController.saveTarefa(categoria, responsavel, descricao, data, prioridade, status);
    if (success) {
        Notification.show("Tarefa salva com sucesso!", 3000, Notification.Position.TOP_CENTER);
    } else {
        Notification.show("Erro ao salvar a tarefa.", 3000, Notification.Position.TOP_CENTER);
    }
}

}
