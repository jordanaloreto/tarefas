package com.jordana.application.views.categoria;

import com.jordana.application.model.Categoria;
import com.jordana.application.controller.CategoriaController;
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

@PageTitle("Categoria")
@Menu(icon = "line-awesome/svg/archive-solid.svg", order = 2)
@Route(value = "my-view")
public class CategoriaView extends Composite<VerticalLayout> {

    private final CategoriaController categoriaController;
    private final Grid<Categoria> grid;
    private final TextField textField;
    private Categoria categoriaEditando;

    public CategoriaView() {
        this.categoriaController = new CategoriaController();

        textField = new TextField();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow = new HorizontalLayout();
        grid = new Grid<>(Categoria.class);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        // Configuração do TextField
        textField.setLabel("Categoria");
        textField.setWidth("min-content");

        // Configuração do Button
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(event -> saveOrUpdateCategoria());

        // Configuração do Layout
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

        // Configuração do Grid
        grid.setColumns("descricao");
        grid.addComponentColumn(this::createRemoveButton).setHeader("Actions");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemDoubleClickListener(event -> editCategoria(event.getItem()));
        updateGrid();

        // Adicionando componentes ao layout
        getContent().add(textField, buttonPrimary, grid, layoutRow);
    }

    private void saveOrUpdateCategoria() {
        String descricao = textField.getValue();
        if (descricao == null || descricao.isEmpty()) {
            Notification.show("Descrição não pode estar vazia.");
            return;
        }

        if (categoriaEditando == null) {
            Categoria categoria = new Categoria();
            categoria.setDescricao(descricao);
            boolean success = categoriaController.saveCategoria(categoria);

            if (success) {
                Notification.show("Categoria salva com sucesso!");
                textField.clear();
                updateGrid();
            } else {
                Notification.show("Falha ao salvar a categoria.");
            }
        } else {
            categoriaEditando.setDescricao(descricao);
            boolean success = categoriaController.updateCategoria(categoriaEditando);

            if (success) {
                Notification.show("Categoria atualizada com sucesso!");
                textField.clear();
                categoriaEditando = null;
                updateGrid();
            } else {
                Notification.show("Falha ao atualizar a categoria.");
            }
        }
    }

    private void updateGrid() {
        grid.setItems(categoriaController.getAllCategorias());
    }

    private Button createRemoveButton(Categoria categoria) {
        Button removeButton = new Button(new Icon(VaadinIcon.TRASH));
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeButton.addClickListener(event -> {
            boolean success = categoriaController.deleteCategoria(categoria);
            if (success) {
                updateGrid();
                Notification.show("Categoria deletada com sucesso!");
            } else {
                Notification.show("Não é possível deletar a categoria. Ela está associada a uma tarefa.");
            }
        });
        return removeButton;
    }

    private void editCategoria(Categoria categoria) {
        textField.setValue(categoria.getDescricao());
        categoriaEditando = categoria;
    }
}
