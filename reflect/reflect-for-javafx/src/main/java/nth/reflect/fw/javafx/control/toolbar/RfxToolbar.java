package nth.reflect.fw.javafx.control.toolbar;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import nth.reflect.fw.javafx.control.RfxControl;
import nth.reflect.fw.javafx.control.style.RfxStyleSelector;
import nth.reflect.fw.javafx.control.style.RfxStyleSheet;
import nth.reflect.fw.ui.style.ReflectColorName;

public abstract class RfxToolbar extends HBox implements RfxControl {//ToolBar {


	public RfxToolbar()  {
		getStyleClass().add(RfxStyleSheet.createStyleClassName(RfxToolbar.class));
	}
	
	public void addSpacer() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		getChildren().add(spacer);
	}

	public static void appendStyleGroups(RfxStyleSheet styleSheet) {
		
		styleSheet.addStyleGroup(RfxStyleSelector.createFor(RfxToolbar.class)).getProperties()
		.setBackground(ReflectColorName.CONTENT.BACKGROUND_20())
		.setHeight(60)
		.setMinWidth(300)
		.setPadding(0)
		.setAlignment(Pos.CENTER_LEFT);
	}
	

}
