package org.vadere.gui.topographycreator.control.attribtable.cells.delegates;

import org.vadere.gui.topographycreator.control.attribtable.tree.AttributeTree;
import org.vadere.gui.topographycreator.control.attribtable.tree.FieldNode;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class SpinnerCellEditor extends AttributeEditor {
    private JSpinner spinner;

    public SpinnerCellEditor(AttributeTree.TreeNode model, JPanel contentPanel) {
        super(model, contentPanel);
    }

    @Override
    protected void initialize() {
        this.spinner = new JSpinner();
        initializeSpinnerModel();
        initializeSpinnerValue();
        initializeSpinnerListener();
        this.add(spinner);
    }

    private void initializeSpinnerModel() {
        JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }

    private void initializeSpinnerValue() {
        var value = ((FieldNode) model).getValueNode().getValue();
        if (value != null)
            this.spinner.setValue(((FieldNode) model).getValueNode().getValue());
    }

    private void initializeSpinnerListener() {
        this.spinner.addChangeListener(e -> updateModel(spinner.getValue()));
    }

    public void onModelChanged(Object value) {
        this.spinner.setValue(value);
    }
}
