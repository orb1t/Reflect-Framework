package nth.reflect.fw.layer5provider.reflection.behavior.parameterfactory;

import java.lang.reflect.Method;
import java.util.Optional;

import nth.reflect.fw.layer2service.MainMenu;
import nth.reflect.fw.layer2service.ServiceObject;
import nth.reflect.fw.layer3domain.DomainObject;
import nth.reflect.fw.layer5provider.reflection.behavior.BehavioralMethods;
import nth.reflect.fw.layer5provider.reflection.behavior.executionmode.ExecutionMode;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethod;

/**
 * <p>
 * The parameter factory allows you to create an object for an
 * {@link ActionMethod}. This object can then be edited by the user (depending
 * how the {@link ActionMethod} is annotated, see {@link ExecutionMode}
 * annotation) after which it is passed as method parameter when the
 * {@link ActionMethod} is invoked.
 * </p>
 * <p>
 * The {@link MainMenu} will display all {@link ActionMethod}s of all registered
 * {@link ServiceObject}s that can directly be executed (without the need of an
 * opened {@link DomainObject}). This means that only {@link ActionMethod}s that
 * either have no method parameter or have an {@link ParameterFactory} are
 * displayed as menu items in the {@link MainMenu}.
 * <p>
 * 
 * <h3>ParameterFactory Annotation</h3>
 * <p>
 * {@insert ParameterFactory}
 * </p>
 * 
 * 
 * <h3>ParameterFactory Method</h3>
 * <p>
 * {@insert ParameterFactoryMethod}
 * </p>
 * 
 * @author nilsth
 *
 */
public class ParameterFactoryModelFactory {
	public static ParameterFactoryModel create( Method method, Class<?> methodParameterType) {
		Optional<ParameterFactoryModel> methodModel = createMethodModel(method);
		if (methodModel.isPresent()) {
			return methodModel.get();
		}

		ParameterFactoryModel annotationModel = createAnnotationModel( method, methodParameterType);
		return annotationModel;
	}

	private static ParameterFactoryModel createAnnotationModel(Method method, Class<?> methodParameterType) {
		if (hasParameterFactory(method )) {
			return new ParameterFactoryAnnotationModel(
					methodParameterType);
		} else {
			return null;
		}
	}

	private static boolean hasParameterFactory(Method method) {
		ParameterFactory annotation = method.getAnnotation(ParameterFactory.class);
		return annotation!=null;
	}

	private static Optional<ParameterFactoryModel> createMethodModel(Method method) {
		Optional<Method> parameterFactoryMethod = BehavioralMethods.PARAMETER_FACTORY.findFor(method);
		if (parameterFactoryMethod.isPresent()) {
			return Optional.of(new ParameterFactoryMethodModel(parameterFactoryMethod.get()));
		} else {
			return Optional.empty();
		}
	}
	
}
