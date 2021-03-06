package nth.reflect.fw.doc;

import nth.reflect.fw.ReflectFramework;
import nth.reflect.fw.documentation.ReflectDocumentation;

/**
 * <p>
 * The documentation of the {@link ReflectFramework} is made with help of
 * JavaDoc and the {@link SoftwareDocumentationGenerator} project. This
 * {@link SoftwareDocumentationGenerator} will parse this JavaDoc and generate
 * web pages and wiki pages on the GitHub server. Please read the javaDoc of the
 * {@link SoftwareDocumentationGenerator} itself to see the advantages of this
 * approach.
 * </p>
 * <h3>Updating the Reflect Documentation</h3>
 * <p>
 * The documentation needs to be republished once the JavaDoc of the
 * {@link ReflectFramework} has been updated. To do this, run the
 * {@link SoftwareDocumentationGenerator} application with the following
 * parameters:
 * <ul>
 * <li>createGitHubHtmlDocumentation "M:/My Git/Reflect-Framework"
 * "{@link ReflectDocumentation}" "ntenhoeve" "GithuB66^" "M:\My
 * Git\ntenhoeve.github.io"</li>
 * <li>createGitHubWikiDocumentation "M:/My Git/Reflect-Framework"
 * "{@link ReflectDocumentation}" "ntenhoeve" "GithuB66^" "M:\My
 * Git\Reflect-Framework.wiki"</li>
 * </ul>
 * Or run the {@link SoftwareDocumentationGenerator} with the
 * UpdateReflectDocumentation.commands file as parameter (located in this
 * package). This file does the same as the above.
 * </p>
 * <p>
 * The {@link SoftwareDocumentationGenerator} will then parse the JavaDoc of the
 * {@link ReflectFramework} and generate web pages and Wiki pages into the
 * local GitHub repositories. These GitHub repositories will ten be committed
 * and pushed onto the GitHub server (effectively publishing the documentation).
 * </p>
 * <h3>Reading the Reflect Documentation</h3>
 * <p>
 * When the GitHubPageGenerator has published/updated the documentation it can be found at:
 * </p>
 * <ul>
 * <li>Web page: <a
 * href="http://ntenhoeve.github.io">http://ntenhoeve.github.io</a></li>
 * <li>Wiki: <a
 * href="https://github.com/ntenhoeve/Reflect-Framework/wiki">https
 * ://github.com/ntenhoeve/Reflect-Framework/wiki</a></li>
 * </ul>
 * </p>
 * 
 * @author nilsth
 *
 */
public interface ReflectDocumentationInstructions {

}
