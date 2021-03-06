package nth.reflect.fw.layer5provider.reflection.behavior.disabled;

import java.lang.reflect.Method;

import nth.reflect.fw.layer5provider.reflection.behavior.BehaviorMethodInvokeException;

/**
 * Model that returns the value of the {@link DisabledMethod}
 * 
 * @author nilsth
 *
 */
public class DisabledMethodModel  implements DisabledModel {

	private Method disabledMethod;

	public DisabledMethodModel(Method disabledMethod)  {
		this.disabledMethod = disabledMethod;
	}
	
	@Override
	public boolean isDisabled(Object obj) {
		Object[] arguments = new Object[0];
		try {
			return (boolean) disabledMethod.invoke(obj, arguments);
		} catch (Exception exception) {
			throw new BehaviorMethodInvokeException(disabledMethod, exception);
		}
	}

	


}
