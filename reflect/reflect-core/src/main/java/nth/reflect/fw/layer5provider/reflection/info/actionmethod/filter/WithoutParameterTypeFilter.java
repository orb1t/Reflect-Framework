package nth.reflect.fw.layer5provider.reflection.info.actionmethod.filter;

import java.util.function.Predicate;

import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethodInfo;

public class WithoutParameterTypeFilter implements Predicate<ActionMethodInfo> {

	private final Class<?> parameterType;

	public WithoutParameterTypeFilter(Class<?> parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * @param actionMethodInfo
	 *            The value that will be matched
	 * @return True if the method does not require a given parameter to be invoked. In other words: If the methods has no parameter or if the parameter has a parameter factory method
	 */
	@Override
	public boolean test(ActionMethodInfo actionMethodInfo) {
		Class<?> methodParameterClass = actionMethodInfo.getParameterType();
		return methodParameterClass == null || !actionMethodInfo.getParameterType().isAssignableFrom(parameterType);
	}

}
