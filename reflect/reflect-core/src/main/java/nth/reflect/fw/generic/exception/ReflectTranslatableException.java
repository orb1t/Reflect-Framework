package nth.reflect.fw.generic.exception;

import nth.reflect.fw.generic.translatablestring.TranslatableString;
import nth.reflect.fw.layer1userinterface.UserInterfaceContainer;
import nth.reflect.fw.layer5provider.language.LanguageProvider;

public class ReflectTranslatableException extends ReflectException {

	private static final long serialVersionUID = 6799738456356756550L;
	private final LanguageProvider languageProvider;
	private final TranslatableString message;


	public ReflectTranslatableException(UserInterfaceContainer userInterfaceContainer, String messageInEnglish, Object ...parameters) {
		super();
		this.languageProvider = userInterfaceContainer.get(LanguageProvider.class);
		this.message = new TranslatableString(messageInEnglish, parameters);
	}
	
	public ReflectTranslatableException(UserInterfaceContainer userInterfaceContainer, Throwable cause, String messageInEnglish, Object ...parameters) {
		super(cause);
		this.languageProvider = userInterfaceContainer.get(LanguageProvider.class);;
		this.message = new TranslatableString(messageInEnglish, parameters);
	}
	
	public ReflectTranslatableException(LanguageProvider languageProvider, String messageInEnglish, Object ...parameters) {
		super();
		this.languageProvider = languageProvider;
		this.message = new TranslatableString(messageInEnglish, parameters);
	}
	
	public ReflectTranslatableException(LanguageProvider languageProvider, Throwable cause, String messageInEnglish, Object ...parameters) {
		super(cause);
		this.languageProvider = languageProvider;
		this.message = new TranslatableString(messageInEnglish, parameters);
	}
	
	@Override
	public String getMessage() {
		return  message.translateToEnglish();
	}
	
	@Override
	public String getLocalizedMessage() {
		return  message.translate(languageProvider);
	}
}
