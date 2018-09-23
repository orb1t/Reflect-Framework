package nth.reflect.ui.vaadin.view.form;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import nth.reflect.fw.generic.valuemodel.ReadOnlyValueModel;
import nth.reflect.fw.layer1userinterface.UserInterfaceContainer;
import nth.reflect.fw.layer1userinterface.view.ViewController;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.layer5provider.reflection.ReflectionProvider;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethodInfo;
import nth.reflect.fw.layer5provider.reflection.info.classinfo.ClassInfo;
import nth.reflect.fw.layer5provider.reflection.info.property.PropertyInfo;
import nth.reflect.fw.ui.item.method.FormOkItem;
import nth.reflect.fw.ui.item.tab.CancelItem;
import nth.reflect.fw.ui.valuemodel.BufferedDomainValueModel;
import nth.reflect.fw.ui.valuemodel.PropertyValueModel;
import nth.reflect.fw.ui.view.FormMode;
import nth.reflect.fw.ui.view.form.propertypanel.PropertyPanelFactory;
import nth.reflect.ui.vaadin.UserInterfaceControllerForVaadin;
import nth.reflect.ui.vaadin.css.Overflow;
import nth.reflect.ui.vaadin.css.SizeUnit;
import nth.reflect.ui.vaadin.css.StyleBuilder;
import nth.reflect.ui.vaadin.view.container.TabView;
import nth.reflect.ui.vaadin.view.form.row.PropertyPanel;

@SuppressWarnings("serial")
public class FormView extends TabView implements nth.reflect.fw.ui.view.FormView {

	private static final int PADDING = 20;
	private final UserInterfaceContainer userInterfaceContainer;
	private final Object actionMethodOwner;
	private final ActionMethodInfo actionMethodInfo;
	private final Object methodParameterValue;
	private final Object domainObject;
	private final FormMode formMode;
	private final BufferedDomainValueModel domainValueModel;
	private final ReflectionProvider reflectionProvider;
	private List<PropertyPanel> propertyPanels;

	public FormView(UserInterfaceContainer userInterfaceContainer, Object actionMethodOwner,
			ActionMethodInfo actionMethodInfo, Object methodParameterValue, Object domainObject, FormMode formMode) {
		this.userInterfaceContainer = userInterfaceContainer;
		this.actionMethodOwner = actionMethodOwner;
		this.actionMethodInfo = actionMethodInfo;
		this.methodParameterValue = methodParameterValue;
		this.domainObject = domainObject;
		this.formMode = formMode;

		this.reflectionProvider = userInterfaceContainer.get(ReflectionProvider.class);
		UserInterfaceControllerForVaadin userinterfaceController = userInterfaceContainer
				.get(UserInterfaceControllerForVaadin.class);
		PropertyPanelFactory<PropertyPanel> formRowFactory = userinterfaceController.getPropertyPanelFactory();
		
		this.domainValueModel = new BufferedDomainValueModel(userInterfaceContainer,  domainObject,
				formMode);

		propertyPanels=createFormRows(formRowFactory);
		
		FormLayout formLayout = createFormLayout();
		add(formLayout);
	}

	private void updateFormRows() {
		for (PropertyPanel propertyPanel : propertyPanels) {
			propertyPanel.updateFromPropertyValueModel();
		}
	}

	private List<PropertyPanel> createFormRows(PropertyPanelFactory<PropertyPanel> formRowFactory) {
		List<PropertyPanel> propertyPanels=new ArrayList<>();
		ClassInfo domainInfo = reflectionProvider.getClassInfo(domainObject.getClass());
		List<PropertyInfo> propertyInfos = domainInfo.getPropertyInfosSorted();
		for (PropertyInfo propertyInfo : propertyInfos) {
			PropertyValueModel propertyValueModel = new PropertyValueModel(domainValueModel, propertyInfo, formMode);
			PropertyPanel propertyPanel = formRowFactory.createPropertyPanel(this, propertyValueModel);
			propertyPanels.add(propertyPanel);
		}
		return propertyPanels;
	}

	private Component createOkCancelButtonGroup() {
		HorizontalLayout okCancelButtonGroup = new HorizontalLayout();
		new StyleBuilder().setPadding(PADDING, 0, 0, 0).setFor(okCancelButtonGroup);

		LanguageProvider languageProvider = userInterfaceContainer.get(LanguageProvider.class);
		UserInterfaceControllerForVaadin userInterfaceController = userInterfaceContainer
				.get(UserInterfaceControllerForVaadin.class);
		@SuppressWarnings("rawtypes")
		ViewController tabViewController = userInterfaceController.getViewController();

		FormOkItem okItem = new FormOkItem(this, actionMethodOwner, actionMethodInfo, domainValueModel);
		Button okButton = new Button(okItem.getText());
		okButton.addClickListener(event -> {
			okItem.getAction().run();
		});
		okCancelButtonGroup.add(okButton);

		@SuppressWarnings("unchecked")
		CancelItem cancelItem = new CancelItem(languageProvider, tabViewController, this);
		Button cancelButton = new Button(cancelItem.getText());
		cancelButton.addClickListener(event -> {
			cancelItem.getAction().run();
		});
		okCancelButtonGroup.add(cancelButton);

		return okCancelButtonGroup;
	}

	private FormLayout createFormLayout() {

		FormLayout formLayout = new FormLayout();
		new StyleBuilder().setHeight(100, SizeUnit.PERCENT).setOverflow(Overflow.AUTO).setPadding(PADDING)
				.setFor(formLayout);

		for (PropertyPanel propertyPanel : propertyPanels) {
			formLayout.add(propertyPanel);
		}

		if (formMode == FormMode.EDIT_MODE) {
			Component buttonGroup = createOkCancelButtonGroup();
			formLayout.add(buttonGroup);
		}

		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		return formLayout;
	}

	@Override
	public String getViewTitle() {
		return actionMethodInfo.getDisplayName();
	}

	@Override
	public String getViewDescription() {
		return actionMethodInfo.getDescription();
	}

	@Override
	public URL getViewIconURL() {
		return actionMethodInfo.getFontIconUrl(actionMethodOwner);
	}

	@Override
	public void onViewActivate() {
		updateFormRows();
	}

	@Override
	public Object getMethodOwner() {
		return actionMethodOwner;
	}

	@Override
	public ActionMethodInfo getMethodInfo() {
		return actionMethodInfo;
	}

	@Override
	public Object getMethodParameter() {
		return methodParameterValue;
	}

	@Override
	public ReadOnlyValueModel getDomainValueModel() {
		return domainValueModel;
	}

	@Override
	public FormMode getFormMode() {
		return formMode;
	}

	@Override
	public Object getDomainObject() {
		return domainObject;
	}

	@Override
	public UserInterfaceContainer getUserInterfaceContainer() {
		return userInterfaceContainer;
	}

}
