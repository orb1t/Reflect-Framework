package nth.introspect.container.impl;

import nth.introspect.application.IntrospectApplication;
import nth.introspect.container.IntrospectContainer;
import nth.introspect.documentation.DomainLayer;

/**
 * This {@link IntrospectContainer} represents the {@link DomainLayer}
 * @author nilsth
 *
 */
public class DomainContainer extends IntrospectContainer {
	
	public DomainContainer(IntrospectApplication application) {
		super(new InfrastructureContainer(application));
	}

}