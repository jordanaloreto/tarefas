package com.jordana.application.views.responsaveis;

import com.jordana.application.model.Responsaveis;
import com.jordana.application.controller.ResponsaveisController;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Responsaveis")
@Menu(icon = "line-awesome/svg/address-card-solid.svg", order = 1)
@Route(value = "responsaveis")
public class ResponsaveisView extends Composite<VerticalLayout> {

    private final ResponsaveisController responsaveisController;
    private final Grid<Responsaveis> grid;
    private final TextField textField;
    private Responsaveis responsavelEditando;

    public ResponsaveisView() {
        this.responsaveisController = new ResponsaveisController();

        textField = new TextField();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow = new HorizontalLayout();
        grid = new Grid<>(Responsaveis.class);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        // Configuração do TextField
        textField.setLabel("Nome do Responsável");
        textField.setWidth("min-content");

        // Configuração do Button
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(event -> saveOrUpdateResponsavel());

        // Configuração do Layout
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

        // Configuração do Grid
        grid.setColumns("nome");
        grid.addComponentColumn(this::createRemoveButton).setHeader("Actions");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemDoubleClickListener(event -> editResponsavel(event.getItem()));
        updateGrid();

        // Adicionando componentes ao layout
        getContent().add(textField, buttonPrimary, grid, layoutRow);
    }

    private void saveOrUpdateResponsavel() {
        String nome = textField.getValue();
        if (nome == null || nome.isEmpty()) {
            Notification.show("Nome do Responsável não pode estar vazio.");
            return;
        }

        if (responsavelEditando == null) {
            Responsaveis responsavel = new Responsaveis();
            responsavel.setNome(nome);
            boolean success = responsaveisController.saveResponsavel(responsavel);

            if (success) {
                Notification.show("Responsável salvo com sucesso!");
                textField.clear();
                updateGrid();
            } else {
                Notification.show("Falha ao salvar o responsável.");
            }
        } else {
            responsavelEditando.setNome(nome);
            boolean success = responsaveisController.updateResponsavel(responsavelEditando);

            if (success) {
                Notification.show("Responsável atualizado com sucesso!");
                textField.clear();
                responsavelEditando = null;
                updateGrid();
            } else {
                Notification.show("Falha ao atualizar o responsável.");
            }
        }
    }

    private void updateGrid() {
        grid.setItems(responsaveisController.getAllResponsaveis());
    }

    private Button createRemoveButton(Responsaveis responsavel) {
        Button removeButton = new Button(new Icon(VaadinIcon.TRASH));
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeButton.addClickListener(event -> {
            boolean success = responsaveisController.deleteResponsavel(responsavel);
            if (success) {
                updateGrid();
                Notification.show("Responsável deletado com sucesso!");
            } else {
                Notification.show("Não é possível deletar o responsável.");
            }
        });
        return removeButton;
    }

    private void editResponsavel(Responsaveis responsavel) {
        textField.setValue(responsavel.getNome());
        responsavelEditando = responsavel;
    }
}
