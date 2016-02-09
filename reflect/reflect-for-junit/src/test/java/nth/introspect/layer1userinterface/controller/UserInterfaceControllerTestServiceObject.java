package nth.introspect.layer1userinterface.controller;

import nth.introspect.layer5provider.reflection.behavior.executionmode.ExecutionMode;
import nth.introspect.layer5provider.reflection.behavior.executionmode.ExecutionModeType;
import nth.introspect.layer5provider.reflection.behavior.parameterfactory.ParameterFactory;

public class UserInterfaceControllerTestServiceObject {

	private static final String SERVICE_OBJECT = "ServiceObject";

	@ParameterFactory
	@ExecutionMode(mode=ExecutionModeType.EDIT_PARAMETER_THEN_EXECUTE_METHOD_OR_CANCEL)
	public void editActionMethodDomainParameter(UserInterfaceControllerTestDomainObject domainObject) {
		
	}
	
	@Override
	public String toString() {
		return SERVICE_OBJECT;
	}
}
