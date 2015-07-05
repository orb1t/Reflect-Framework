package nth.introspect.application;

import java.util.List;

import nth.introspect.Introspect;
import nth.introspect.controller.userinterface.UserInterfaceController;
import nth.introspect.documentation.InfrastructureObject;
import nth.introspect.documentation.IntrospectGettingStarted;
import nth.introspect.documentation.ObjectBehavior;
import nth.introspect.documentation.ServiceObject;
import nth.introspect.provider.Provider;
import nth.introspect.provider.about.AboutProvider;
import nth.introspect.provider.authorization.AuthorizationProvider;
import nth.introspect.provider.domain.info.DomainInfoProvider;
import nth.introspect.provider.domain.info.valuemodel.annotations.GenericReturnType;
import nth.introspect.provider.domain.info.valuemodel.annotations.VisibleInForm;
import nth.introspect.provider.domain.info.valuemodel.annotations.VisibleInTable;
import nth.introspect.provider.language.LanguageProvider;
import nth.introspect.provider.notification.NotificationProvider;
import nth.introspect.provider.path.PathProvider;
import nth.introspect.provider.validation.ValidationProvider;

/**
 * {@link IntrospectApplication} is used as initialization parameter for the
 * {@link Introspect} framework.<br>
 * An Introspect application has several purposes:
 * <ul>
 * <li>It provides the name, icon and description of the application (see
 * {@link ObjectBehavior})</li>
 * <li>It provides the location of the distribution package (jar, war)</li>
 * <li>It provides the {@link UserInterfaceController} type, needed for the
 * application with the
 * {@link IntrospectApplication#getUserInterfaceControllerClass()} method. Each
 * application type (commandline, swing, vaadin) requires different
 * implementations of the {@link UserInterfaceController}</li>
 * <li>It provides a list of {@link ServiceObject} types with the
 * {@link IntrospectApplication#getServiceClasses()} method.
 * {@link ServiceObject}s basically provide the actionable/menu items</li>
 * <li>It provides a list of {@link DomainObject} types that need to be created
 * using dependency injection, with the
 * {@link IntrospectApplication#getDomainClasses()} method.</li>
 * <li>It provides a list of {@link InfrastructureObject} types with the
 * {@link IntrospectApplication#getInfrastructureClasses()} method.
 * {@link InfrastructureObject}s basically communicate to the outside world
 * (i.e. data base access objects, email clients, soap clients, etc)</li>
 * <li>It provides the {@link Provider} types with the
 * {@link IntrospectApplication#get...ProviderClass()} methods. {@link Provider}
 * Objects help with <a
 * href="https://en.wikipedia.org/wiki/Cross-cutting_concern">cross cutting
 * concerns</a>. Each application type (commandline, swing, vaadin) requires
 * different implementations of the providers</li>
 * </ul>
 * <p>
 * Each application type (command line, Android, Vaadin, etc..) has its own
 * implementation of {@link IntrospectApplication} to help initializing the
 * framework. See the type hierarchy of {@link IntrospectApplication} to learn
 * which classes can be used and view their java doc to learn how to use them.
 * </p>
 * <p>
 * If you create a new application you will be extending one of these classes.
 * You will only need to implement some of the methods mentioned above (at least
 * the {@link IntrospectApplication#getServiceClasses()} method).
 * </p>
 * <p>
 * For examples see the {@link IntrospectGettingStarted} section.
 * </p>
 * @author nilsth
 * 
 */

public interface IntrospectApplication {

	public Class<? extends UserInterfaceController<?>> getUserInterfaceControllerClass();

	public Class<? extends DomainInfoProvider> getDomainInfoProviderClass();

	public Class<? extends AboutProvider> getVersionProviderClass();

	public Class<? extends PathProvider> getPathProviderClass();

	public Class<? extends LanguageProvider> getLanguageProviderClass();

	public Class<? extends AuthorizationProvider> getAuthorizationProviderClass();

	public Class<? extends ValidationProvider> getValidationProviderClass();

	public Class<? extends NotificationProvider> getNotificationProviderClass();

	// TODO implement to add custom views: public List<Class<?>>
	// getUserInterfaceViewClasses();. View must interped methodinfo to indicate
	// is they can view the method result or parameter

	public List<Class<?>> getServiceClasses();

	public List<Class<?>> getInfrastructureClasses();

}
