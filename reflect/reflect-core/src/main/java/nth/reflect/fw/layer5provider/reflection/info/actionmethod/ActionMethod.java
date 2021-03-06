package nth.reflect.fw.layer5provider.reflection.info.actionmethod;

import nth.reflect.fw.ReflectFramework;
import nth.reflect.fw.documentation.ReflectDocumentationInterface;
import nth.reflect.fw.layer1userinterface.controller.DownloadStream;
import nth.reflect.fw.layer1userinterface.controller.UserInterfaceController;
import nth.reflect.fw.layer2service.ServiceObject;
import nth.reflect.fw.layer3domain.DomainObject;
import nth.reflect.fw.layer3domain.DomainObjectProperty;
import nth.reflect.fw.layer5provider.reflection.behavior.BehavioralMethod;
import nth.reflect.fw.layer5provider.reflection.behavior.ObjectBehavior;
import nth.reflect.fw.layer5provider.reflection.behavior.executionmode.ExecutionMode;

/**
 * <p>
 * An {@link ActionMethod} is a method in a {@link DomainObject} or
 * {@link ServiceObject} that is displayed by the
 * {@link UserInterfaceController} as a menu item. An {@link ActionMethod} is
 * invoked by the {@link UserInterfaceController} when the user clicks on the
 * menu item.
 * </p>
 * <h2>Action Methods for Domain Objects</h2>
 * <p>
 * {@insert DomainObjectActionMethod}
 * </p>
 * <h2>Action Methods for Domain Object Properties</h2>
 * <p>
 * {@insert DomainObjectPropertyActionMethod}
 * </p>
 * <h2>Action Methods for Service Objects</h2>
 * <p>
 * {@insert ServiceObjectActionMethod}
 * </p>
 * 
 * <h2>Action Methods Convention</h2>
 * <p>
 * Any method in a {@link DomainObject} or {@link ServiceObject} can be an
 * Action method, provided that it complies with the following convention:
 * </p>
 * <ul>
 * <li>The method has no parameter or a single {@link DomainObject} parameter</li>
 * <li>Its return type (if any) are types recognized by the {@link ReflectFramework}
 * (see below)</li>
 * <li>The method is not a getter method or a setter method (see
 * {@link DomainObjectProperty})</li>
 * <LI>The method is not a {@link BehavioralMethod}</li>
 * <li>The method is public (not private)</li>
 * <li>The method is NOT static</li>
 * </ul>
 * <h2>Action method names</h2>
 * <p>
 * The name of an {@link ActionMethod} should describe the action and match the
 * <a href="http://martinfowler.com/bliki/UbiquitousLanguage.html">Ubiquitous
 * Language</a> (in terms understood by both users and developers). Keep in mind
 * that the goal of a user is almost never to create, update or delete objects.
 * Method names like: createPerson(Person person), updatePerson(Person person) and removePerson(Person person) should
 * therefore be avoided where possible. Method names like addNewBorn(BirthCirtificate birthCirtificate),
 * addMarriage(Marriage marriage), deceased(DeathCertificate deathCertificate) would be better method names.
 * </p>
 * <h2>Action method parameter</h2>
 * <p>
 * An action method either has no parameter or a single {@link DomainObject} as
 * a parameter. If not, the {@link ReflectFramework} will not recognize a method as
 * an {@link ActionMethod}.
 * </p>
 * <h2>Action method return value</h2>
 * <p>
 * The {@link UserInterfaceController} renders the output of a method, depending
 * on the type of the action method return value:
 * <ul>
 * <li>No return value (void method): The {@link UserInterfaceController} will
 * display a short message when the method has been executed</li>
 * <li><a href=
 * "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">A
 * primitive data type</a>: The {@link UserInterfaceController} will display a
 * message dialog that displays the return value after the method is been
 * executed</li>
 * <li>A {@link DomainObject}: The {@link UserInterfaceController} displays the
 * {@link DomainObject} in a form on a new tab.</li>
 * <li>A <a
 * href="http://en.wikipedia.org/wiki/Java_collections_framework">collection</a>
 * of {@link DomainObject}s: The {@link UserInterfaceController} displays the
 * {@link DomainObject}s in table on a new tab.</li>
 * <li>A URI: The {@link UserInterfaceController} displays the contents of the
 * URI on a new tab.</li>
 * <li>A {@link DownloadStream}: The {@link UserInterfaceController} will open a
 * "Save as" dialog" so that the file can be down loaded.</li>
 * </ul>
 * </p>
 * <h2>Action execution modes</h2>
 * <p>
 * {@link ActionMethod}s can be annotated, so that the
 * {@link UserInterfaceController} knows how the {@link ActionMethod} needs to
 * be invoked after the user has clicked on the corresponding menu item. For
 * more information see {@link ExecutionMode}
 * </p>
 * 
 * <h2>Action behavior</h2>
 * <p>
 * You can specify certain things about both the behavior and presentation of
 * actions methods, such as their display name, icon, visibility, enabled state,
 * etc with help of annotations or behavior methods. See the sections on
 * {@link ObjectBehavior}.
 * </p>
 * 
 * @author nilsth
 *
 */
public interface ActionMethod extends ReflectDocumentationInterface {

}
