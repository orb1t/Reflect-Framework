package nth.reflect.fw.javafx.control.view.form.proppanel;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import nth.reflect.fw.javafx.control.style.RfxStyleSelector;
import nth.reflect.fw.javafx.control.style.RfxStyleSheet;
import nth.reflect.fw.javafx.control.view.form.FormView;
import nth.reflect.fw.javafx.control.view.form.PropertiesPanel;
import nth.reflect.fw.ui.style.MaterialColorSetCssName;
import nth.reflect.fw.ui.style.component.PropertyPanelStyle;
import nth.reflect.fw.ui.valuemodel.PropertyValueModel;
import nth.reflect.fw.ui.view.form.propertypanel.PropertyField;
import nth.reflect.fw.ui.view.form.propertypanel.PropertyFieldWidth;

public class PropertyLabelAndFieldPanel extends BorderPane {

	private final PropertyLabel propertyLabel;
	private final PropertyField propertyField;
	private final FormView formView;

	public PropertyLabelAndFieldPanel(FormView formView, PropertyValueModel propertyValueModel, PropertyField propertyField) {
		getStyleClass().add(RfxStyleSheet.createStyleClassName(PropertyLabelAndFieldPanel.class));
		
		this.formView = (FormView) formView;

		propertyLabel = new PropertyLabel();
		setTop(propertyLabel);

		this.propertyField = propertyField;
		setCenter((Node) propertyField);

		setWidth(propertyField.getPropertyFieldWidth());
	}

	private void setWidth(PropertyFieldWidth propertyFieldWidth) {
		switch (propertyFieldWidth) {
		case FULL:
			DoubleBinding fullWidth = formView.widthProperty().subtract(2*PropertiesPanel.HORIZONTAL_GAP);
			prefWidthProperty().bind(fullWidth);
			break;
		case SMALL:
		default:
			setMaxWidth(PropertyPanelStyle.getMaxSmallWidth());
			setMinWidth(PropertyPanelStyle.getMinSmallWidth());
			break;
		}
		
	}

	public PropertyLabel getPropertyLabel() {
		return propertyLabel;
	}

	public PropertyField getPropertyField() {
		return propertyField;
	}
	
	public static void appendStyleGroups(RfxStyleSheet styleSheet) {
		styleSheet.addStyleGroup(RfxStyleSelector.createFor(PropertyLabelAndFieldPanel.class)).getProperties()
		.setBackground(MaterialColorSetCssName.CONTENT.BACKGROUND_HIGHLIGHTED()).setProperty("-fx-background-radius", "10 10 0 0");
	}

}
