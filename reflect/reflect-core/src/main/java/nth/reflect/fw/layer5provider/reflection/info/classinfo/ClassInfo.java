package nth.reflect.fw.layer5provider.reflection.info.classinfo;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import nth.reflect.fw.layer2service.ServiceObject;
import nth.reflect.fw.layer3domain.DomainObject;
import nth.reflect.fw.layer5provider.ProviderContainer;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.layer5provider.reflection.ReflectionProvider;
import nth.reflect.fw.layer5provider.reflection.behavior.description.DescriptionModel;
import nth.reflect.fw.layer5provider.reflection.behavior.displayname.DisplayNameModel;
import nth.reflect.fw.layer5provider.reflection.behavior.fonticon.FontIconModel;
import nth.reflect.fw.layer5provider.reflection.behavior.fonticon.FontIconModelFactory;
import nth.reflect.fw.layer5provider.reflection.behavior.title.TitleModel;
import nth.reflect.fw.layer5provider.reflection.behavior.validation.ValidationMethodFactory;
import nth.reflect.fw.layer5provider.reflection.info.NameInfo;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethodInfo;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethodInfoFactory;
import nth.reflect.fw.layer5provider.reflection.info.property.PropertyInfo;
import nth.reflect.fw.layer5provider.reflection.info.property.PropertyInfoFactory;

/**
 * Provides information on a {@link ServiceObject} or {@link DomainObject}.<br>
 * 
 * @author nilsth
 * 
 */
public class ClassInfo implements NameInfo {

	private final String simpleName;
	private final String canonicalName;
	private final DescriptionModel descriptionModel;
	private final Class<?> objectClass;
	private final DisplayNameModel displayNameModel;
	private final TitleModel titleModel;
	private final FontIconModel fontIconModel;
	private final List<Method> validationMethods;
	private final List<PropertyInfo> propertyInfosSorted;
	private final List<ActionMethodInfo> actionMethodInfosSorted;

	public ClassInfo(ProviderContainer providerContainer, Class<?> objectClass) {
		LanguageProvider languageProvider = providerContainer.get(LanguageProvider.class);
		ReflectionProvider reflectionProvider = providerContainer.get(ReflectionProvider.class);
		// PathProvider pathProvider =
		// providerContainer.get(PathProvider.class);
		this.simpleName = objectClass.getSimpleName();
		this.canonicalName = objectClass.getCanonicalName();
		this.objectClass = objectClass;
		this.displayNameModel = new DisplayNameModel(languageProvider, objectClass, simpleName, canonicalName);
		this.descriptionModel = new DescriptionModel(languageProvider, objectClass, simpleName, canonicalName);
		this.titleModel = new TitleModel(reflectionProvider);
		this.fontIconModel = FontIconModelFactory.create(objectClass);
		this.validationMethods = ValidationMethodFactory.create(objectClass);
		this.propertyInfosSorted = PropertyInfoFactory.createSorted(providerContainer, objectClass);
		this.actionMethodInfosSorted = ActionMethodInfoFactory.createSorted(providerContainer, this);
	}

	@Override
	public String getSimpleName() {
		return simpleName;
	}

	@Override
	public String getCanonicalName() {
		return canonicalName;
	}

	public Class<?> getObjectClass() {
		return objectClass;
	}

	public String getDisplayName() {
		return displayNameModel.getText();
	}

	public String getDescription() {
		return descriptionModel.getText();
	}

	public URL getFontIconUrl(Object obj) {
		return fontIconModel.getFontIconUrl(obj);
	}

	public String getTitle(Object obj) {
		return titleModel.getTitle(obj);
	}

	@Override
	public String toString() {
		return canonicalName;
	}

	public List<Method> getAllValidationMethods() {
		return validationMethods;
	}

	public List<PropertyInfo> getPropertyInfosSorted() {
		return propertyInfosSorted;
	}

	public List<PropertyInfo> getPropertyInfosSortedAndVisibleInTable() {
		List<PropertyInfo> sortedPropertyInfosVisibleInTable = propertyInfosSorted.stream()
				.filter(propertyInfo -> propertyInfo.isVisibleInTable()).collect(Collectors.toList());
		return sortedPropertyInfosVisibleInTable;
	}

	public PropertyInfo getPropertyInfo(String propertyName) {
		for (PropertyInfo propertyInfo : propertyInfosSorted) {
			if (propertyInfo.getSimpleName().equals(propertyName)) {
				return propertyInfo;
			}
		}
		return null;
	}

	public List<ActionMethodInfo> getActionMethodInfosSorted() {
		return actionMethodInfosSorted;
	}

	public ActionMethodInfo getActionMethodInfo(String methodName) {
		for (ActionMethodInfo actionMethodInfo : actionMethodInfosSorted) {
			if (actionMethodInfo.getSimpleName().equals(methodName)) {
				return actionMethodInfo;
			}
		}
		return null;
	}

	public List<ActionMethodInfo> getActionMethodInfos(Predicate<ActionMethodInfo> filter) {
		List<ActionMethodInfo> filteredActionMethods = actionMethodInfosSorted.stream().filter(filter)
				.collect(Collectors.toList());
		return filteredActionMethods;
	}


}
