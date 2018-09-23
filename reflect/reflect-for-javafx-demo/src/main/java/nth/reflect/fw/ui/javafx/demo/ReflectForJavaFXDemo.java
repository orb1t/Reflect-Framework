package nth.reflect.fw.ui.javafx.demo;

import java.util.Arrays;
import java.util.List;

import nth.reflect.example.domain.test.TestsService;
import nth.reflect.example.domain2.account.AccountRepository;
import nth.reflect.example.domain2.account.AccountService;
import nth.reflect.example.domain2.tag.TagService;
import nth.reflect.example.domain2.vault.VaultService;
import nth.reflect.fw.javafx.ReflectApplicationForJavaFX;
import nth.reflect.fw.layer5provider.reflection.behavior.displayname.DisplayName;
import nth.reflect.infra.generic.xml.XmlConverter;

@DisplayName(englishName="Reflect for JavaFX Demo")
public class ReflectForJavaFXDemo extends ReflectApplicationForJavaFX {

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public List<Class<?>> getServiceClasses() {
		return Arrays.asList(VaultService.class, AccountService.class,TagService.class, TestsService.class);
	}

	@Override
	public List<Class<?>> getInfrastructureClasses() {
		return Arrays.asList(AccountRepository.class, XmlConverter.class);
	}

}
