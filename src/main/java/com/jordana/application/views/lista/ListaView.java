package com.jordana.application.views.lista;

import com.jordana.application.controller.TarefaController;
import com.jordana.application.model.Tarefas;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;
import java.util.Set;

@PageTitle("Lista")
@Menu(icon = "line-awesome/svg/bookmark-solid.svg", order = 0)
@Route(value = "")
@RouteAlias(value = "")
public class ListaView extends Composite<VerticalLayout> {

    private final TarefaController tarefaController;
    private final Grid<Tarefas> grid;

    private final ComboBox<String> comboBoxCategoria;
    private final ComboBox<String> comboBoxResponsavel;
    private final TextField textFieldTarefa;
    private final DatePicker datePickerData;
    private final RadioButtonGroup<String> radioGroupPrioridade;
    private final CheckboxGroup<String> checkboxGroupStatus;
    private final Button buttonSave;

    private Tarefas tarefaAtual;

    public ListaView() {
        tarefaController = new TarefaController();

        FormLayout formLayout3Col = new FormLayout();
        comboBoxCategoria = new ComboBox<>();
        comboBoxResponsavel = new ComboBox<>();
        textFieldTarefa = new TextField();
        datePickerData = new DatePicker();
        radioGroupPrioridade = new RadioButtonGroup<>();
        checkboxGroupStatus = new CheckboxGroup<>();
        buttonSave = new Button("Save");
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
        buttonSave.addClickListener(e -> saveTarefa());

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
        formLayout3Col.add(buttonDelete);

        // Grid
        grid = new Grid<>(Tarefas.class);
        grid.setColumns("descricao", "dataTarefa", "prioridade", "status", "categoriaId", "responsavelId");
        grid.addComponentColumn(tarefa -> {
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(e -> deleteTarefa(tarefa));
            return deleteButton;
        }).setHeader("Ações");

        grid.addItemDoubleClickListener(event -> editTarefa(event.getItem()));

        getContent().add(grid);
        atualizarGrid();
    }

    private void setComboBoxCategoriaData(ComboBox<String> comboBox) {
        List<String> categorias = tarefaController.getAllCategorias();
        comboBox.setItems(categorias);
    }

    private void setComboBoxResponsavelData(ComboBox<String> comboBox) {
        List<String> responsaveis = tarefaController.getAllResponsaveis();
        comboBox.setItems(responsaveis);
    }

    private void saveTarefa() {
        boolean success;
        if (tarefaAtual == null) {
            success = tarefaController.saveTarefa(comboBoxCategoria.getValue(), comboBoxResponsavel.getValue(),
                    textFieldTarefa.getValue(), datePickerData.getValue().toString(),
                    radioGroupPrioridade.getValue(),
                    String.join(", ", checkboxGroupStatus.getValue()));
        } else {
            tarefaAtual.setDescricao(textFieldTarefa.getValue());
            tarefaAtual.setDataTarefa(datePickerData.getValue());
            tarefaAtual.setPrioridade(radioGroupPrioridade.getValue());
            tarefaAtual.setStatus(String.join(", ", checkboxGroupStatus.getValue()));
            tarefaAtual.setCategoriaId(tarefaController.getCategoriaIdByDescricao(comboBoxCategoria.getValue()));
            tarefaAtual.setResponsavelId(tarefaController.getResponsavelIdByNome(comboBoxResponsavel.getValue()));
            success = tarefaController.updateTarefa(tarefaAtual);
        }

        if (success) {
            Notification.show("Tarefa salva com sucesso!", 3000, Notification.Position.TOP_CENTER);
            atualizarGrid();
            clearForm();
        } else {
            Notification.show("Erro ao salvar a tarefa.", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void atualizarGrid() {
        List<Tarefas> tarefas = tarefaController.getAllTarefas();
        grid.setItems(tarefas);
    }

    private void editTarefa(Tarefas tarefa) {
        tarefaAtual = tarefa;
        comboBoxCategoria.setValue(tarefaController.getCategoriaDescricaoById(tarefa.getCategoriaId()));
        comboBoxResponsavel.setValue(tarefaController.getResponsavelNomeById(tarefa.getResponsavelId()));
        textFieldTarefa.setValue(tarefa.getDescricao());
        datePickerData.setValue(tarefa.getDataTarefa());
        radioGroupPrioridade.setValue(tarefa.getPrioridade());
        checkboxGroupStatus.setValue(Set.of(tarefa.getStatus().split(", ")));
    }

    private void deleteTarefa(Tarefas tarefa) {
        boolean success = tarefaController.deleteTarefa(tarefa);
        if (success) {
            Notification.show("Tarefa deletada com sucesso!", 3000, Notification.Position.TOP_CENTER);
            atualizarGrid();
        } else {
            Notification.show("Erro ao deletar a tarefa.", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void clearForm() {
        comboBoxCategoria.clear();
        comboBoxResponsavel.clear();
        textFieldTarefa.clear();
        datePickerData.clear();
        radioGroupPrioridade.clear();
        checkboxGroupStatus.clear();
        tarefaAtual = null;
    }
}
