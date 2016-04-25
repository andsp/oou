package ru.andsp.oou.ui;

import ru.andsp.oou.ui.config.ConfigHelper;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class PropertyForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfHost;
    private JTextField tfPort;
    private JTextField tfDb;
    private JTextField tfUser;
    private JTextField tfDirectory;
    private JPasswordField pfPass;
    private JButton btSelectDir;


    private Instance instance;

    public PropertyForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        btSelectDir.addActionListener(e -> onSelectDir());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSelectDir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(tfDirectory.getText() != null ? new File(tfDirectory.getText()) : new File("."));
        chooser.setDialogTitle("Select dir result");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfDirectory.setText(chooser.getSelectedFile().getAbsolutePath());
        }
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
        result.setPass(new String(pfPass.getPassword()));
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
            pfPass.setText(instance.getPass());
        }
    }

}
