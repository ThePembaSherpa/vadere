package org.vadere.gui.topographycreator.control.attribtable.cells.delegates;

import org.vadere.gui.topographycreator.control.attribtable.tree.AttributeTree;

import javax.swing.*;
/**
 * @author Ludwig Jaeck
 * This class is a table delegate for editing boolean fields.
 */
public class CheckBoxCellEditor extends AttributeEditor {
    private JCheckBox checkBox;

    public CheckBoxCellEditor(AttributeTree.TreeNode model, JPanel contentPanel,Object initialValue) {
        super(model, contentPanel,initialValue);
    }


    @Override
    protected void initialize(Object initialValue) {
        this.checkBox = new JCheckBox();
        if(initialValue!=null)
            this.checkBox.setSelected((Boolean) initialValue);
        initializeCheckBoxListener();
        this.add(checkBox);
    }

    private void initializeCheckBoxListener() {
        this.checkBox.addItemListener(e -> updateModel(checkBox.isSelected()));
    }

    @Override
    public void onModelChanged(Object value) {
        this.checkBox.setSelected((Boolean) value);
    }
}
