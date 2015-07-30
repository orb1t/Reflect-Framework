package nth.introspect.layer5provider.reflection.info.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import nth.introspect.generic.util.AnnotationUtil;
import nth.introspect.generic.util.TypeUtil;
import nth.introspect.layer5provider.reflection.info.valuemodel.annotations.GenericReturnType;

public abstract class ValueType {

	private final Class<?> type;
	private final TypeCategory typeCategory;
	private final Class<?> typeOrGenericCollectionType;


	public ValueType(Class<?> type, Method method, TypeCategory[] noneSupportedCategories) {
		this.type = TypeUtil.getComplexType(type);
		typeCategory = TypeUtil.getTypeCategory(type);

		validateTypeCategory(typeCategory, noneSupportedCategories, method);

		typeOrGenericCollectionType = getTypeOrGenericCollectionType(this.type, typeCategory, method);
	}

	private  Class<?> getTypeOrGenericCollectionType(Class<?> type, TypeCategory typeCategory, Method method) {
		// get typeOrGenericCollectionType. 
		if (type == null || typeCategory != TypeCategory.COLLECTION_TYPE || this.getClass()==MethodParameterType.class) {//Note that ParameterType does not get here because it does not support collections (yet). If so: also implement a GenericParameterType annotation (see GenericReturnType annotation)
			return type;
		} else {
			try {
				// when it is a collection: get generic type from an annotation at the method
				Annotation annotation = AnnotationUtil.findAnnotation(method, GenericReturnType.class);
				Class<?> annotatedType=(Class<?>) AnnotationUtil.getAnnotationValue(annotation);
				return TypeUtil.getComplexType(annotatedType);
			} catch (Exception e) {
				// failed: throw error message
				StringBuffer message = new StringBuffer();
				message.append("Method: ");
				message.append(method.getDeclaringClass().getCanonicalName());
				message.append(".");
				message.append(method.getName());
				message.append(" requires a: ");
				message.append(GenericReturnType.class.getSimpleName());
				message.append(" annotation to specify the collection type.");
				throw new RuntimeException(message.toString(),e);
			}
		}
	}

	/**
	 * 
	 * @param typeCategory
	 * @param method
	 * @param noneSupportedCategories
	 * @throws RuntimeException
	 *             if typeCategory matches one of the noneSupportedCategories
	 */
	private void validateTypeCategory(TypeCategory typeCategory, TypeCategory[] noneSupportedCategories, Method method) throws RuntimeException {
		for (TypeCategory noneSupportedCategory : noneSupportedCategories) {
			if (typeCategory == noneSupportedCategory) {
				// throw error message
				StringBuffer message = new StringBuffer();
				message.append(this.getClass().getSimpleName());
				message.append(" for method: ");
				message.append(method.getDeclaringClass().getCanonicalName());
				message.append(".");
				message.append(method.getName());
				message.append(" may not be of type: ");
				message.append(noneSupportedCategory.toString());
				throw new RuntimeException(message.toString());
			}
		}
	}

	public Class<?> getType() {
		return type;
	}

	public TypeCategory getTypeCategory() {
		return typeCategory;
	}

	public Class<?> getTypeOrGenericCollectionType() {
		return typeOrGenericCollectionType;
	}

	@Override
	public String toString() {
		StringBuffer text = new StringBuffer();
		if (typeCategory == TypeCategory.COLLECTION_TYPE) {
			text.append(type.getSimpleName());
			text.append("(");
		}
		text.append("<");
		text.append(typeOrGenericCollectionType.getSimpleName());
		text.append(">");
		if (typeCategory == TypeCategory.COLLECTION_TYPE) {
			text.append(")");
		}
		return text.toString();
	}
}