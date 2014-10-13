package nth.introspect.util;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import nth.introspect.provider.domain.info.valuemodel.impl.SimpleValue;
import nth.introspect.valuemodel.ReadOnlyValueModel;

public class AnnotationUtil {

	public static boolean hasAnnotation(PropertyDescriptor propertyDescriptor, Class<? extends Annotation> annotationClass) {
		return hasAnnotation(propertyDescriptor.getReadMethod(), annotationClass);
	}

	private static boolean hasAnnotation(Method method, Class<? extends Annotation> annotationClass) {
		return method.isAnnotationPresent(annotationClass);
	}

	public static ReadOnlyValueModel createValueModel(PropertyDescriptor propertyDescriptor, Class<? extends Annotation> annotationClass, String annotationMethodName) {
		Object value=getValue(propertyDescriptor.getReadMethod(), annotationClass, annotationMethodName);
		return new SimpleValue(value);
	}

	private static Object getValue(Method method, Class<? extends Annotation> annotationClass, String annotationMethodName) {
		try {
			Annotation annotation = method.getAnnotation(annotationClass);
			Class<?>[] signature = new Class[0];
			Method annotationMethod = annotation.getClass().getMethod(annotationMethodName, signature);
			Object[] arguments = new Object[0];
			return annotationMethod.invoke(annotation, arguments);
		} catch (Exception e) {
			return null;
		}
	}

}
