package nth.reflect.fw.javafx.control.tab.form;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import nth.reflect.fw.generic.util.TitleUtil;
import nth.reflect.fw.javafx.control.tab.Tab;
import nth.reflect.fw.layer1userinterface.UserInterfaceContainer;
import nth.reflect.fw.layer5provider.reflection.ReflectionProvider;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethodInfo;
import nth.reflect.fw.ui.tab.form.FormMode;
import nth.reflect.fw.ui.valuemodel.BufferedDomainValueModel;

public class FormTab extends Tab implements nth.reflect.fw.ui.tab.form.FormTab {

	private final UserInterfaceContainer userInterfaceContainer;
	private final Object methodOwner;
	private final ActionMethodInfo actionMethodInfo;
	private final Object methodParameterValue;
	private final Object domainObject;
	private final FormMode formMode;
	private final ReflectionProvider reflectionProvider;
	private BufferedDomainValueModel domainValueModel;

	public FormTab(UserInterfaceContainer userInterfaceContainer, Object methodOwner,
			ActionMethodInfo actionMethodInfo, Object methodParameterValue, Object domainObject,
			FormMode formMode) {
		this.userInterfaceContainer = userInterfaceContainer;
		this.reflectionProvider=userInterfaceContainer.get(ReflectionProvider.class);
		this.methodOwner = methodOwner;
		this.actionMethodInfo = actionMethodInfo;
		this.methodParameterValue = methodParameterValue;
		this.domainObject = domainObject;
		this.formMode = formMode;


		domainValueModel = new BufferedDomainValueModel(userInterfaceContainer,
				domainObject, formMode);

		
		PropertiesPanel domainPropertyPane = new PropertiesPanel(this);
//		setCenter(domainPropertyPane);
		ScrollPane scrollPane=new ScrollPane(domainPropertyPane);
		//TODO new RfxVerticalFlingScroller(scrollPane);
		setCenter(scrollPane);
		
//		VBox vbox=new VBox();
//		vbox.setPrefHeight(javafx.scene.control.Control.USE_COMPUTED_SIZE);
//		vbox.setMaxHeight(Double.POSITIVE_INFINITY);
//		vbox.getChildren().add(scrollPane);
		
		HBox bottomButtonPane=new RfxFormBottomToolBar(this);
		setBottom(bottomButtonPane);

	}

	@Override
	public String getDisplayName() {
		 return TitleUtil.createTitle(reflectionProvider, actionMethodInfo,
				domainValueModel.getValue());
	}


	@Override
	public void onSelected() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getMethodOwner() {
		return methodOwner;
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
	public BufferedDomainValueModel getDomainValueModel() {
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
