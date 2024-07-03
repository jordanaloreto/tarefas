package com.jordana.application.views.categoria;

import com.jordana.application.model.Categoria;
import com.jordana.application.controller.CategoriaController;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

    public CategoriaView() {
        this.categoriaController = new CategoriaController();

        TextField textField = new TextField();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow = new HorizontalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        textField.setLabel("Categoria");
        textField.setWidth("min-content");
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        getContent().add(textField);
        getContent().add(buttonPrimary);
        getContent().add(layoutRow);

        buttonPrimary.addClickListener(event -> {
            String descricao = textField.getValue();
            if (descricao == null || descricao.isEmpty()) {
                Notification.show("Descrição não pode estar vazia.");
                return;
            }

            Categoria categoria = new Categoria();
            categoria.setDescricao(descricao);
            boolean success = categoriaController.saveCategoria(categoria);

            if (success) {
                Notification.show("Categoria salva com sucesso!");
                textField.clear();
            } else {
                Notification.show("Falha ao salvar a categoria.");
            }
        });
    }
}
