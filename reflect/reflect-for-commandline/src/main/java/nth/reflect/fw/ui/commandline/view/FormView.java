package nth.reflect.fw.ui.commandline.view;

import java.util.List;

import nth.reflect.fw.layer5provider.reflection.ReflectionProvider;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethodInfo;
import nth.reflect.fw.layer5provider.reflection.info.classinfo.ClassInfo;
import nth.reflect.fw.layer5provider.reflection.info.property.PropertyInfo;
import nth.reflect.fw.ui.commandline.domain.table.Row;
import nth.reflect.fw.ui.commandline.domain.table.Table;

public class FormView extends CommandLineView {

	private Table table;

	public FormView(ReflectionProvider reflectionProvider, ActionMethodInfo actionMethodInfo, Object domainObject) {
		table = new Table();
		// empty row
		Row row = table.addRow();
		row.addCell("", Table.MAX_WIDTH_IN_COLS);

		// get propertyInfos
		Class<?> returnClass = actionMethodInfo.getGenericReturnType();
		ClassInfo classInfo = reflectionProvider.getClassInfo(returnClass);
		List<PropertyInfo> propertyInfos = classInfo.getPropertyInfosSorted();

		// add properties to form
		for (PropertyInfo propertyInfo : propertyInfos) {
			row = table.addRow();
			row.addCell(propertyInfo.getDisplayName(), 18);
			row.addCell(propertyInfo.getValue(domainObject).toString(), 60);
		}
	}

	@Override
	public String toString() {
		return table.toString();
	}


}
