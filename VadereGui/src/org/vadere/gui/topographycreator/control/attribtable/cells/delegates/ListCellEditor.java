package org.vadere.gui.topographycreator.control.attribtable.cells.delegates;

import org.vadere.gui.topographycreator.control.attribtable.JAttributeTable;
import org.vadere.gui.topographycreator.control.attribtable.cells.ButtonColumnRenderer;
import org.vadere.gui.topographycreator.control.attribtable.cells.ListValueDelegateWrapper;
import org.vadere.gui.topographycreator.control.attribtable.tree.ArrayNode;
import org.vadere.gui.topographycreator.control.attribtable.tree.AttributeTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

public class ListCellEditor extends ChildObjectCellEditor {
    private JAttributeTable table;


    private Object instanceOfSelected;

    public ListCellEditor(AttributeTree.TreeNode model, JPanel contentPanel,Object initialValue) {
        super(model, contentPanel,initialValue);
    }


    @Override
    protected void createInternalPropertyPane(Object newObject) {
        table = new JAttributeTable(model, new JAttributeTable.Styler() {
            private boolean contentVisible = true;
            @Override
            public JTable rowDelegateStyle(String id, AttributeEditor editor) {
                JTable style = new JTable();
                DefaultTableModel modelt = new DefaultTableModel(new Object[]{"attr", "btn"}, 1){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column != 1;
                    }
                };
                style.setModel(modelt);
                style.setRowHeight(28);
                style.setIntercellSpacing(new Dimension(0, 4));
                style.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                style.setBackground(UIManager.getColor("Panel.background"));

                var attrDelegate = new ListValueDelegateWrapper(editor,id);

                style.getColumn("attr").setCellRenderer(attrDelegate);
                style.getColumn("attr").setCellEditor(attrDelegate);
                style.getColumn("btn").setCellRenderer(new ButtonColumnRenderer());
                style.getColumn("btn").setPreferredWidth(30);

                style.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var col = style.columnAtPoint(e.getPoint());
                        if (col == 1) {
                            try {
                                ((ArrayNode) model).remove(id);
                            } catch (IllegalAccessException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            contentVisible = !contentVisible;
                            editor.getContentPanel().setVisible(contentVisible );
                        }

                    }
                });
                style.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        super.componentResized(e);
                        var width = style.getWidth();
                        style.getColumn("attr").setPreferredWidth(width - 30);
                        style.getColumn("btn").setPreferredWidth(30);
                    }
                });

                style.setEditingColumn(0);
                table.setVisible(true);
                return style;
            }
        });

        this.contentPanel.add(table, BorderLayout.CENTER);
        var addBtn = new JButton("+");
        addBtn.setPreferredSize(new Dimension(20,20));
        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    ((ArrayNode) model).addElement();
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.contentPanel.add(addBtn, BorderLayout.SOUTH);
    }

    @Override
    protected void constructNewObject() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //to not create an instance
    }
}
