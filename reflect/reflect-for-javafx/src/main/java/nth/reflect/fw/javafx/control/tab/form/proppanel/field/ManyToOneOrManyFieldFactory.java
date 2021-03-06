package nth.reflect.fw.javafx.control.tab.form.proppanel.field;

import nth.reflect.fw.layer5provider.reflection.behavior.fieldmode.FieldModeType;
import nth.reflect.fw.ui.tab.form.FormTab;
import nth.reflect.fw.ui.tab.form.propertypanel.PropertyField;
import nth.reflect.fw.ui.tab.form.propertypanel.PropertyFieldFactory;
import nth.reflect.fw.ui.valuemodel.PropertyValueModel;

public class ManyToOneOrManyFieldFactory implements PropertyFieldFactory {

	@Override
	public PropertyField create(FormTab formTab, PropertyValueModel propertyValueModel) {
		return new ManyToOneOrManyField(formTab, propertyValueModel);
	}

	@Override
	public boolean canCreateFor(PropertyValueModel propertyValueModel) {
		return propertyValueModel.getPropertyInfo().getFieldMode()==FieldModeType.MANY_TO_ONE_OR_MANY;
	}

}
