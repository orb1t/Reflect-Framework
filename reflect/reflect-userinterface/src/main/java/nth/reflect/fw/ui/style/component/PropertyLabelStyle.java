package nth.reflect.fw.ui.style.component;

import nth.reflect.fw.ui.style.MaterialFont;
import nth.reflect.fw.ui.style.ReflectColors;
import nth.reflect.fw.ui.style.basic.Color;
import nth.reflect.fw.ui.style.basic.Font;

/**
 * A {@link ReflectStyleClass} for a {@link PropertyLabelStyle}
 * @author nilsth
 *
 */
public class PropertyLabelStyle implements ReflectStyleClass {

	public static Font getFont() {
		return MaterialFont.getRobotoRegular(12);
	}
	
	public static Color getForeground1(ReflectColors reflectColors) {
		return reflectColors.getContentColors().getForeground();
	}

}
