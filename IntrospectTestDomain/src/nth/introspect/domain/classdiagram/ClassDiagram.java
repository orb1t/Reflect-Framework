package nth.introspect.domain.classdiagram;

import java.util.ArrayList;
import java.util.List;

import nth.introspect.Introspect;
import nth.introspect.provider.domain.DomainProvider;
import nth.introspect.provider.domain.info.method.MethodInfo;
import nth.introspect.provider.domain.info.property.PropertyInfo;
import nth.introspect.provider.domain.info.valuemodel.annotations.GenericReturnType;
import nth.introspect.provider.domain.info.valuemodel.annotations.OrderInForm;

public class ClassDiagram {

	private ClassFeature name;
	private ClassFeature superclass;
	private final List<ClassFeature> interfaces;
	private final List<ClassFeature> properties;
	private final List<ClassFeature> methods;

	public ClassDiagram() {
		interfaces = new ArrayList<ClassFeature>();
		properties = new ArrayList<ClassFeature>();
		methods = new ArrayList<ClassFeature>();
	}

	public ClassDiagram(Class<?> introspectedClass) {
		this();// initialize lists

		// name
		setName(new ClassFeature(introspectedClass));

		// superclass
		Class<?> superClass = introspectedClass.getSuperclass();
		if (superClass != null) {
			setSuperclass(new ClassFeature(superClass));
		}
		
		// interfaces
		Class<?>[] interfaces = introspectedClass.getInterfaces();
		for (Class<?> interfaze : interfaces) {
			getInterfaces().add(new ClassFeature(interfaze));
		}

		// properties
		DomainProvider domainProvider = Introspect.getDomainProvider();
		List<PropertyInfo> propertyInfos = domainProvider.getPropertyInfos(introspectedClass);
		for (PropertyInfo propertyInfo : propertyInfos) {
			getProperties().add(new ClassFeature(propertyInfo));
		}

		// methods
		List<MethodInfo> methodInfos = domainProvider.getMethodInfos(introspectedClass);
		for (MethodInfo methodInfo : methodInfos) {
			getMethods().add(new ClassFeature(methodInfo));
		}
	}

	@OrderInForm(1)
	public ClassFeature getName() {
		return name;
	}

	public void setName(ClassFeature name) {
		this.name = name;
	}

	@OrderInForm(2)
	public ClassFeature getSuperclass() {
		return superclass;
	}

	public void setSuperclass(ClassFeature superclass) {
		this.superclass = superclass;
	}

	@OrderInForm(3)
	@GenericReturnType(ClassFeature.class)
	public List<ClassFeature> getInterfaces() {
		return interfaces;
	}

	@OrderInForm(4)
	@GenericReturnType(ClassFeature.class)
	public List<ClassFeature> getProperties() {
		return properties;
	}

	@OrderInForm(5)
	@GenericReturnType(ClassFeature.class)
	public List<ClassFeature> getMethods() {
		return methods;
	}

}