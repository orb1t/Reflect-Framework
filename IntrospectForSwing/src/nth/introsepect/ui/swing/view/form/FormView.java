package nth.introsepect.ui.swing.view.form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.net.URI;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import nth.introsepect.ui.swing.item.button.ItemButton;
import nth.introsepect.ui.swing.properygrid.PropertyGrid;
import nth.introsepect.ui.swing.properygrid.PropertyRow;
import nth.introsepect.ui.swing.properygrid.SwingUtil;
import nth.introsepect.ui.swing.view.SwingView;
import nth.introspect.Introspect;
import nth.introspect.provider.domain.DomainProvider;
import nth.introspect.provider.domain.info.method.MethodInfo;
import nth.introspect.provider.domain.info.method.MethodInfo.FormModeType;
import nth.introspect.provider.domain.info.property.PropertyInfo;
import nth.introspect.ui.item.method.FormOkItem;
import nth.introspect.ui.item.tab.CancelItem;
import nth.introspect.ui.item.tab.CloseThisTabItem;
import nth.introspect.ui.valuemodel.BufferedDomainValueModel;
import nth.introspect.util.TitleUtil;
import nth.introspect.valuemodel.ReadOnlyValueModel;

@SuppressWarnings("serial")
public class FormView extends SwingView implements nth.introspect.provider.userinterface.view.FormView { // implements ReadOnlyValueModel {

	private final MethodInfo methodInfo;
	private final Object serviceObject;
	private BufferedDomainValueModel domainValueModel;
	private final FormModeType formMode;
	// private final Object domainObject;
	private final Object methodParameterValue;

	public FormView(Object serviceObject, MethodInfo methodInfo, Object methodParameterValue, Object domainObject, FormModeType formMode) {
		this.serviceObject = serviceObject;
		this.methodInfo = methodInfo;
		this.methodParameterValue = methodParameterValue;
		// this.domainObject = domainObject;
		this.formMode = formMode;
		setLayout(new BorderLayout());

		DomainProvider domainProvider = Introspect.getDomainProvider();
		List<PropertyInfo> propertyInfos = domainProvider.getPropertyInfos(domainObject.getClass());

		boolean formIsReadonly = formMode != FormModeType.editParameterThenExecuteMethodOrCancel;
		domainValueModel = new BufferedDomainValueModel(domainObject, formIsReadonly);

		PropertyGrid propertyGrid = new PropertyGrid();
		add(propertyGrid, BorderLayout.CENTER);
		for (PropertyInfo propertyInfo : propertyInfos) {
			PropertyRow propertyRow = new PropertyRow(this, domainValueModel, propertyInfo, formIsReadonly);
			propertyGrid.addPropertyRow(propertyRow);
		}
		//FIXME: request focus to first enabled field

		add(createButtonBar(), BorderLayout.SOUTH);
	}

	private Component createButtonBar() {
		JPanel buttonBar = new JPanel();
		buttonBar.setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		buttonBar.add(Box.createHorizontalGlue());

		switch (formMode) {
		case executeMethodAfterConformation:
		case showParameterThenExecuteMethodOrCancel:
		case editParameterThenExecuteMethodOrCancel:
			buttonBar.add(createOkButton());
			buttonBar.add(createCancelButton());
			break;
		case executeMethodDirectly:
			// FormView should not be used anyway so do nothing
			break;
		case showParameterThenClose:
			buttonBar.add(createCloseButton());
			break;
		}

		buttonBar.add(Box.createHorizontalGlue());
		return buttonBar;
	}

	public JButton createCloseButton() {
		CloseThisTabItem closeItem = new CloseThisTabItem(this);
		return new ItemButton(closeItem);
	}

	public JButton createCancelButton() {
		CancelItem cancelItem = new CancelItem(this);
		return new ItemButton(cancelItem);
	}

	public JButton createOkButton() {
		FormOkItem okItem = new FormOkItem(this, serviceObject, methodInfo, domainValueModel);
		return new ItemButton(okItem);
	}

	@Override
	public String getViewTitle() {
		return TitleUtil.createTitle(methodInfo, domainValueModel.getValue(), true);
	}

	@Override
	public String getViewDescription() {
		return methodInfo.getDescription();
	}

	@Override
	public URI getViewIconURI() {
		return methodInfo.getIconURI(serviceObject);
	}

	public FormModeType getFormMode() {
		return formMode;
	}

	@Override
	public ReadOnlyValueModel getDomainValueModel() {
		return domainValueModel;
	}

	@Override
	public void onViewActivate() {
		SwingUtil.refreshComponentAndItsChildren(this);
	}

	@Override
	public MethodInfo getMethodInfo() {
		return methodInfo;
	}

	@Override
	public Object getServiceObject() {
		return serviceObject;
	}
}