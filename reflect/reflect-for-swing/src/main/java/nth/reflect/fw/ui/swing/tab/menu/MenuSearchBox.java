package nth.reflect.fw.ui.swing.tab.menu;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import nth.reflect.fw.layer1userinterface.item.Item.Action;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.ui.item.HierarchicalItem;
import nth.reflect.fw.ui.item.method.MethodOwnerItem;
import nth.reflect.fw.ui.swing.accordion.ItemAccordion;
import nth.reflect.fw.ui.swing.textprompt.TextPrompt;

@SuppressWarnings("serial")
public class MenuSearchBox extends JComboBox {

	private final ItemAccordion itemAccordion;

	public MenuSearchBox(ItemAccordion itemAccordion, List<MethodOwnerItem> menuItems, LanguageProvider languageProvider) {
		this.itemAccordion = itemAccordion;
		setEditable(true);
		// create the model
		MenuSearchBoxModel model = new MenuSearchBoxModel(this, menuItems);
		// set the model on the combobox
		setModel(model);
		setRenderer(new MenuSearchBoxRenderer());
		// set the model as the item listener also
		new TextPrompt(languageProvider.getText("Search Menu (F3)"),(JTextComponent) getEditor().getEditorComponent());
	}

	public void onSelectionChanged(HierarchicalItem selectedItem) {
		System.out.println(">On Selecttion Changed:"+selectedItem.getTextPath(" - "));
		itemAccordion.select(selectedItem);
	}
	
	public void onEnter(HierarchicalItem selectedItem) {
		System.out.println(">On Enter:"+selectedItem.getTextPath(" - "));
		itemAccordion.select(selectedItem);
		Action action = selectedItem.getAction();
		if (action!=null) {
			action.run();
		}
		
	}
}
