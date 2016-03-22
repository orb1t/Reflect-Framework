package nth.introspect.layer5provider.reflection.behavior;

import nth.introspect.IntrospectFramework;
import nth.introspect.documentation.Documentation;
import nth.introspect.layer3domain.DomainObject;
import nth.introspect.layer3domain.DomainObjectProperty;
import nth.introspect.layer5provider.reflection.info.actionmethod.ActionMethod;

/**
 * <p>
 * {@link BehavioralAnnotation}s are recognized by the {@link IntrospectFramework}
 * and define how {@link DomainObject}s, {@link DomainObjectProperty}s or
 * {@link ActionMethod}s behave (how they act and how they displayed).
 * </p>
 * {@link BehavioralAnnotation}s are normally located before the declaration of
 * the member:
 * <ul>
 * <li>{@link BehavioralAnnotation}s for a class: is located before the class key word
 * </li>
 * <li>{@link BehavioralAnnotation}s for a {@link DomainObjectProperty}: is located
 * before the getter method of a {@link DomainObjectProperty}</li>
 * <li>{@link BehavioralAnnotation}s for an {@link ActionMethod}: is located before the
 * {@link ActionMethod}</li>
 * </ul>
 * </ul>
 * 
 * @author nilsth
 *
 */
public interface BehavioralAnnotation extends Documentation {

}
