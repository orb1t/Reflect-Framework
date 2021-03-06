package nth.reflect.fw.ui.swing.tab.form.proppanel.field;

import nth.reflect.fw.layer5provider.reflection.behavior.fieldmode.FieldModeType;
import nth.reflect.fw.ui.tab.form.FormTab;
import nth.reflect.fw.ui.tab.form.propertypanel.PropertyField;
import nth.reflect.fw.ui.tab.form.propertypanel.PropertyFieldFactory;
import nth.reflect.fw.ui.valuemodel.PropertyValueModel;

public class CheckBoxFieldFactory implements PropertyFieldFactory {

	@Override
	public PropertyField create(FormTab formTab, PropertyValueModel propertyValueModel) {
		return new CheckBoxField(propertyValueModel);
	}

	@Override
	public boolean canCreateFor(PropertyValueModel propertyValueModel) {
		return propertyValueModel.getPropertyInfo().getFieldMode()==FieldModeType.CHECK_BOX;
	}

}
