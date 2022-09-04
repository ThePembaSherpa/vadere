package org.vadere.gui.components.view;

import javax.swing.JToolBar;

import org.vadere.gui.components.view.IActionContainer;
import org.vadere.gui.topographycreator.control.ActionOpenDrawOptionMenu;
import org.vadere.util.config.VadereConfig;

import java.awt.*;

/**
 * Toolbar for the topographycreator.
 * 
 * 
 */
public class ScenarioToolBar extends JToolBar implements IActionContainer {

	private static final long serialVersionUID = 6939811771684567978L;

	private boolean initialized;

	public ScenarioToolBar(final String name) {
		super(name);
		this.initialized = true;
		int toolbarSize = VadereConfig.getConfig().getInt("Gui.toolbar.size");
		Dimension prefSize = new Dimension(toolbarSize, toolbarSize);
		//toolbar.setPreferredSize(prefSize);
		setBorderPainted(true);
		setFloatable(true);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setAlignmentY(Component.TOP_ALIGNMENT);
	}

	public void addSection(ScenarioToolBarSection section){
		if(!initialized){
			addSeparator(new Dimension(5, 50));
		}
		for(var action : section.getActions()){
			var btn = add(action);
			if(action instanceof ActionOpenDrawOptionMenu){
				((ActionOpenDrawOptionMenu) action).setParent(btn);
			}
			btn.setBorderPainted(false);
		}
		this.initialized = false;
	}

	public void addSections(ScenarioToolBarSection ...sections) {
		for(var section : sections){
			addSection(section);
		}
	}
}
