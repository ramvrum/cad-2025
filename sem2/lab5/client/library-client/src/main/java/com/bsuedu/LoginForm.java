package com.bsuedu;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class LoginForm extends JFrame {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;

	public LoginForm() {
		setTitle("Login");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 2));

		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Login");

		add(new JLabel("Username:"));
		add(usernameField);
		add(new JLabel("Password:"));
		add(passwordField);
		add(new JPanel());
		add(loginButton);

		loginButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());
			String encoded = Base64.getEncoder()
					.encodeToString((username + ":" + password).getBytes());

			LibraryClientForm form = new LibraryClientForm(encoded);
			form.setVisible(true);
			dispose();
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
	}
}
