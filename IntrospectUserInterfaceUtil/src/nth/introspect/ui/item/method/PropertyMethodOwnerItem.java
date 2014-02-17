package nth.introspect.ui.item.method;

import java.net.URI;
import java.util.List;

import nth.introspect.Introspect;
import nth.introspect.filter.LogicFilter;
import nth.introspect.provider.domain.info.method.MethodInfo;
import nth.introspect.provider.domain.info.method.filter.LinkedToPropertyFilter;
import nth.introspect.provider.domain.info.method.filter.ParameterTypeFilter;
import nth.introspect.provider.domain.info.property.PropertyInfo;
import nth.introspect.provider.userinterface.view.FormView;
import nth.introspect.ui.item.HierarchicalItem;
import nth.introspect.valuemodel.ReadOnlyValueModel;

public class PropertyMethodOwnerItem extends HierarchicalItem {

	private FormView formView;

	public PropertyMethodOwnerItem (FormView formView, ReadOnlyValueModel parameterValueModel) {
		this(formView, parameterValueModel,null);
	}

	public PropertyMethodOwnerItem(FormView formView,
			ReadOnlyValueModel parameterValueModel,
			PropertyInfo propertyToExclude) {
		this.formView = formView;
		pupulateChildren(formView, parameterValueModel, propertyToExclude);
	}

	public void pupulateChildren(FormView formView,
			ReadOnlyValueModel parameterValueModel,
			PropertyInfo propertyToExclude) {
		ReadOnlyValueModel domainValueModel = formView.getDomainValueModel();
		Class<?> domainClass = domainValueModel.getValueType();
		Class<?> parameterClass = parameterValueModel.getValueType();

		List<PropertyInfo> propertyInfos = Introspect.getDomainProvider()
				.getPropertyInfos(domainClass);
		for (PropertyInfo propertyInfo : propertyInfos) {

			if (propertyInfo != propertyToExclude) {

				LogicFilter<MethodInfo> filter = new LogicFilter<MethodInfo>(
						new LinkedToPropertyFilter(propertyInfo));
				filter.and(new ParameterTypeFilter(parameterClass));

				List<MethodInfo> propertyMethods = Introspect
						.getDomainProvider()
						.getMethodInfos(domainClass, filter);
				for (MethodInfo propertyMethodInfo : propertyMethods) {
					PropertyMethodItem propertyMethodItem = new PropertyMethodItem(
							formView, propertyInfo, propertyMethodInfo,
							parameterValueModel);
					getChildren().add(propertyMethodItem);
				}
			}
		}
	}

	@Override
	public URI getIconURI() {
		return formView.getViewIconURI();
	}

	@Override
	public String getText() {
		// TODO return TitleUtil.createTitle(methodInfo, methodParameter,
		// false);
		return formView.getViewTitle();// using description instead of title
										// because the title could be truncated
	}

	@Override
	public boolean isVisible() {
		return hasChildren();
	}

	@Override
	public String getDescription() {
		return formView.getViewDescription();
	}

}