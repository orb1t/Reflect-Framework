package nth.reflect.fw.ui.swing.tab.table;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import nth.reflect.fw.generic.util.TypeUtil;
import nth.reflect.fw.generic.valuemodel.ReadOnlyValueModel;
import nth.reflect.fw.layer1userinterface.controller.Refreshable;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.layer5provider.reflection.ReflectionProvider;
import nth.reflect.fw.layer5provider.reflection.behavior.format.impl.JavaFormatFactory;
import nth.reflect.fw.layer5provider.reflection.info.classinfo.ClassInfo;
import nth.reflect.fw.layer5provider.reflection.info.property.PropertyInfo;

public class MethodTableModel extends AbstractTableModel implements
		DomainTableModel, Refreshable {

	// TODO move to Reflect package to replace domainTableModel?

	private static final long serialVersionUID = 605374068245011236L;
	private ArrayList<Object> list;
	private List<PropertyInfo> propertyInfos;
	private final ReadOnlyValueModel valueModel;
	private Format format;

	public MethodTableModel(ReflectionProvider reflectionProvider, LanguageProvider languageProvider, ReadOnlyValueModel valueModel) {
		this.valueModel = valueModel;
		Class<?> objectClass = valueModel.getValueType();
		if (TypeUtil.isJavaType(objectClass) || TypeUtil.isEnum(objectClass)) {
			JavaFormatFactory formatFactory = new JavaFormatFactory(languageProvider);
			format = formatFactory.create(objectClass);
		} else {
			ClassInfo classInfo = reflectionProvider.getClassInfo(objectClass);
			propertyInfos = classInfo.getPropertyInfosSortedAndVisibleInTable();
		}
		refresh();
	}

	@Override
	public int getColumnCount() {
		if (propertyInfos==null) {
			return 1;//table represents a java type
		}
		return propertyInfos.size();
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = list.get(rowIndex);
		if (propertyInfos==null) {
			//java type 
			return format.format(value);
		}
		//domain object
		Object domainObject = value;
		PropertyInfo propertyInfo = propertyInfos.get(columnIndex);
		return propertyInfo.getFormatedValue(domainObject);
	}

	@Override
	public String getColumnName(int column) {
		if (propertyInfos==null) {
			//java type
			return null;
		}
		// domain type
		PropertyInfo propertyInfo = propertyInfos.get(column);
		return propertyInfo.getDisplayName();
	}

	@Override
	public Object getDomainValue(int rowIndex) {//TODO rename to value?
		return list.get(rowIndex);
	}

	@Override
	public void refresh() {
		Collection<?> collection = null;
		try {
			collection = (Collection<?>) valueModel.getValue();
			fireTableDataChanged();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		list = new ArrayList<Object>(collection);
	}

	public int getRow(Object domainObject) {
		return list.indexOf(domainObject);
	}

}
