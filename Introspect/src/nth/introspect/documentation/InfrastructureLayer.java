package nth.introspect.documentation;

import nth.introspect.container.impl.InfrastructureContainer;

/**
 * The {@link InfrastructureLayer} is part if the {@link IntrospectArchitecture}. <br>
 * The {@link InfrastructureLayer} is also know as:
 * <ul><li><a href="http://en.wikipedia.org/wiki/Data_access_layer">Data access layer</a></li>
 * <li><a href="http://en.wikipedia.org/wiki/Persistence_layer">Persistence layer</a></li>
 * <li>Logging Layer</a></li>
 * <li>Networking Layer</li>
 * <li>And other services which are required to support the {@link ServiceLayer} or {@link DomainLayer}</li> 
 * </ul>
 * The {@link InfrastructureLayer} contains {@link InfrastructureObject}'s that provide generic technical capabilities to support the higher layers (see {@link IntrospectArchitecture})<br>
 * The {@link InfrastructureLayer} is implemented by the {@link InfrastructureContainer}<br>
 * Note that this layer is the bottom layer, which means that objects in the upper layers may know the objects in this layer but not visa versa! See {@link IntrospectArchitecture}
 * 
 * <h2>Infrastructure Objects</h2>
 * {@insert InfrastructureObject}
 *
 */
public interface InfrastructureLayer extends Documentation {

}