package ru.andsp.oou.ui;

import ru.andsp.oou.ui.config.ConfigHelper;

import javax.swing.*;
import java.awt.event.*;

public class PropertyForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfHost;
    private JTextField tfPort;
    private JTextField tfDb;
    private JTextField tfUser;
    private JTextField tfDirectory;
    private JTextField tfPass;


    private Instance instance;

    public PropertyForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private Instance getInstance() {
        Instance result = new Instance();
        if (instance != null) {
            result.setId(instance.getId());
        }
        result.setPath(tfDirectory.getText());
        result.setHost(tfHost.getText());
        result.setPort(tfPort.getText());
        result.setDb(tfDb.getText());
        result.setUser(tfUser.getText());
        result.setPass(tfPass.getText());
        return result;
    }

    private void onOK() {
        ConfigHelper.getInstance().save(getInstance());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void start(Instance instance) {
        PropertyForm dialog = new PropertyForm();
        dialog.bindData(instance);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void bindData(Instance instance) {
        this.instance = instance;
        if (instance != null) {
            tfHost.setText(instance.getHost());
            tfPort.setText(instance.getPort());
            tfDirectory.setText(instance.getPath());
            tfUser.setText(instance.getUser());
            tfDb.setText(instance.getDb());
            tfPass.setText(instance.getPass());
        }
    }

}
