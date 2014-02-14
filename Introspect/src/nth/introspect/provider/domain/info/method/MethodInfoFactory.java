package nth.introspect.provider.domain.info.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nth.introspect.Introspect;
import nth.introspect.provider.domain.DomainProvider;
import nth.introspect.provider.domain.info.classinfo.ClassInfo;
import nth.introspect.provider.domain.info.property.PropertyInfo;
import nth.introspect.provider.domain.info.valuemodel.factories.MethodValueModelFactory;
import nth.introspect.util.StringUtil;

public class MethodInfoFactory {

	public static List<MethodInfo> create(Class<?> introspectedClass) {
		ArrayList<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
		try {
			List<String> propertyNames = getPropertyNames(introspectedClass);

			Method[] methods = introspectedClass.getMethods();
			List<String> unwantedMethodNames = getUnwantedMethodNames(introspectedClass);
			for (Method method : methods) {
				if (!unwantedMethodNames.contains(method.getName())) {
					String linkedPropertyName = findLinkedPropertyName(method, propertyNames);
					MethodInfo methodInfo = new MethodInfo(method, linkedPropertyName);
					methodInfos.add(methodInfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// order form properties (note that the formOrder value getter is not dynamic)
		Collections.sort(methodInfos, new MethodInfosOrderComparator());
		return methodInfos;
	}

	private static String findLinkedPropertyName(Method method, List<String> propertyNames) {
		String methodName = method.getName();
		String linkedPropertyName = null;
		for (String propertyName : propertyNames) {
			if (methodName.startsWith(propertyName) && (linkedPropertyName == null || propertyName.length() > linkedPropertyName.length())) {
				linkedPropertyName = propertyName;
			}
		}
		return linkedPropertyName;
	}

	public static List<String> getPropertyNames(Class<?> introspectedClass) {
		DomainProvider domainProvider = Introspect.getDomainProvider();
		List<PropertyInfo> propertyInfos = domainProvider.getPropertyInfos(introspectedClass);
		List<String> propertyNames = new ArrayList<String>();
		for (PropertyInfo propertyInfo : propertyInfos) {
			propertyNames.add(propertyInfo.getName());
		}
		return propertyNames;
	}

	private static List<String> getUnwantedMethodNames(Class<?> introspectedClass) {
		ArrayList<String> unwantedMethodNames = new ArrayList<String>();
		// add property read method names
		try {
			unwantedMethodNames.add("getChildren");// children of service object that represent a HtmlNakedTreeNode need to be ignored as action method
			for (String suffix : ClassInfo.METHOD_NAMES) {
				String unwantedMethodName = MethodValueModelFactory.createMethodName(introspectedClass.getSimpleName(), suffix);
				unwantedMethodNames.add(unwantedMethodName.toString());
			}
			List<PropertyInfo> propertyInfos = Introspect.getDomainProvider().getPropertyInfos(introspectedClass);
			for (PropertyInfo propertyInfo : propertyInfos) {
				try {
					String propertyName = propertyInfo.getName();
					unwantedMethodNames.add(propertyInfo.getReadMethod().getName());
					unwantedMethodNames.add(propertyInfo.getWriteMethod().getName());
					for (String suffix : PropertyInfo.METHOD_NAMES) {
						String unwantedMethodName = MethodValueModelFactory.createMethodName(propertyName, suffix);
						unwantedMethodNames.add(unwantedMethodName.toString());
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// add method names
		for (Method method : introspectedClass.getMethods()) {
			if (method.getDeclaringClass() != Object.class) {
				for (String suffix : MethodInfo.METHOD_NAMES) {
					StringBuffer unwantedMethodName = new StringBuffer(method.getName());
					unwantedMethodName.append(StringUtil.firstCharToUpperCase(suffix));
					unwantedMethodNames.add(unwantedMethodName.toString());
				}
			}
		}
		// add object method names
		for (Method method : Object.class.getMethods()) {
			unwantedMethodNames.add(method.getName());
		}
		return unwantedMethodNames;
	}
}
