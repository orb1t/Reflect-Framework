package nth.reflect.fw.layer5provider.reflection.behavior.displayname;

import java.lang.reflect.Method;

import nth.reflect.fw.ReflectFramework;
import nth.reflect.fw.layer3domain.DomainObjectProperty;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.layer5provider.reflection.behavior.TextModel;
import nth.reflect.fw.layer5provider.reflection.info.actionmethod.ActionMethod;

/**
 * <p>
 * The {@link Class}Names, {@link DomainObjectProperty} names and
 * {@link ActionMethod}Names are part of the
 * <a href="http://martinfowler.com/bliki/UbiquitousLanguage.html">ubiquitous
 * language</a> (in terms both understand by users and developers). The
 * {@link ReflectFramework} therefore displays the same names as used in the
 * program code.
 * </p>
 * 
 * <h3>DisplayName Default</h3>
 * <p>
 * {@link Class} names, {@link DomainObjectProperty} names and
 * {@link ActionMethod} names in the
 * <a href="https://en.wikipedia.org/wiki/Codebase">codebase</a> use names such
 * as OrderService, orderLines, addOrderLine (using no spaces,
 * <a href="https://nl.wikipedia.org/wiki/CamelCase">camelCase</a> and no
 * special characters). If the user is a an human, more user friendly names are
 * needed such as �Orders� �Order lines� and �Add order line�. This format is
 * called the {@link DisplayName}. The {@link ReflectFramework} will
 * automatically convert the names used in the code to a human readable format
 * ({@link DisplayName}) when needed.
 * </p>
 * <p>
 * The {@link ReflectFramework} supports DisplayNames for multiple languages.
 * The {@link LanguageProvider} is used to get the default displayNames from a
 * language specific property files. For more information see
 * {@link LanguageProvider}.
 * </p>
 * 
 * <h3>DisplayName Annotation</h3>
 * <p>
 * {@insert DisplayName}
 * </p>
 * 
 * @author nilsth
 *
 */
public class DisplayNameModel extends TextModel {

	public DisplayNameModel(LanguageProvider languageProvider, Class<?> objectType, String simpleName,
			String canonicalName) {
		super(languageProvider, objectType, simpleName, canonicalName);
	}

	public DisplayNameModel(LanguageProvider languageProvider, Method getterMethod, String simpleName,
			String canonicalName, String linkedPropertyName) {
		super(languageProvider, getterMethod, simpleName, canonicalName, linkedPropertyName);
	}

	public DisplayNameModel(LanguageProvider languageProvider, Method getterMethod, String simpleName,
			String canonicalName) {
		super(languageProvider, getterMethod, simpleName, canonicalName);
	}

	@Override
	public String createKey(String canonicalName) {
		return canonicalName.concat(".displayname");
	}

	@Override
	public String getDefaultTextFromAnnotation(Class<?> objectType) {
		DisplayName annotation = objectType.getAnnotation(DisplayName.class);
		return (annotation == null) ? null : annotation.englishName();
	}

	@Override
	public String getDefaultTextFromAnnotation(Method method) {
		DisplayName annotation = method.getAnnotation(DisplayName.class);
		return (annotation == null) ? null : annotation.englishName();
	}

}
