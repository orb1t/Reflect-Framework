package nth.reflect.ui.vaadin.tab.form.row;

import java.util.ArrayList;
import java.util.List;

import nth.reflect.fw.ui.tab.form.FormTab;
import nth.reflect.fw.ui.tab.form.propertypanel.PropertyField;
import nth.reflect.fw.ui.tab.form.propertypanel.PropertyFieldFactory;
import nth.reflect.fw.ui.valuemodel.PropertyValueModel;
import nth.reflect.ui.vaadin.tab.form.row.field.TextFieldFactory;

public class PropertyPanelFactory extends nth.reflect.fw.ui.tab.form.propertypanel.PropertyPanelFactory<PropertyPanel> {

	@Override
	public List<PropertyFieldFactory> createFieldFactories() {
		List<PropertyFieldFactory> fieldFactories=new ArrayList<>();
		fieldFactories.add(new TextFieldFactory());
		return fieldFactories;
	}

	@Override
	public PropertyPanel createPropertyPanel(FormTab formTab, PropertyValueModel propertyValueModel) {
		PropertyField propertyField = createPropertyField(formTab, propertyValueModel);
		return new PropertyPanel(formTab, propertyValueModel, propertyField);
	}

	

}
