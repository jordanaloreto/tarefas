package com.jordana.application.views.responsaveis;

import com.jordana.application.model.Responsaveis;
import com.jordana.application.controller.ResponsaveisController;
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

@PageTitle("Responsaveis")
@Menu(icon = "line-awesome/svg/address-card-solid.svg", order = 1)
@Route(value = "responsaveis")
public class ResponsaveisView extends Composite<VerticalLayout> {

    private final ResponsaveisController responsaveisController;

    public ResponsaveisView() {
        this.responsaveisController = new ResponsaveisController();

        TextField textField = new TextField();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow = new HorizontalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        textField.setLabel("Nome do Responsável");
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
            String nome = textField.getValue();
            if (nome == null || nome.isEmpty()) {
                Notification.show("Nome do Responsável não pode estar vazio.");
                return;
            }

            Responsaveis responsavel = new Responsaveis();
            responsavel.setNome(nome);
            boolean success = responsaveisController.saveResponsavel(responsavel);

            if (success) {
                Notification.show("Responsável salvo com sucesso!");
                textField.clear(); // Limpa o campo de texto
            } else {
                Notification.show("Falha ao salvar o responsável.");
            }
        });
    }
}
