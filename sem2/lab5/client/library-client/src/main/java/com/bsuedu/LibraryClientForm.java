package com.bsuedu;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LibraryClientForm extends JFrame {

	private DefaultListModel<String> model;
	private JList<String> list;
	private String auth;

	private long[] ids;

	public LibraryClientForm(String authHeader) {
		this.auth = authHeader;

		setTitle("Library Client");
		setSize(550, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		model = new DefaultListModel<>();
		list = new JList<>(model);

		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton refresh = new JButton("Обновить");
		JButton deleteBtn = new JButton("Удалить книгу");
		JButton addBtn = new JButton("Добавить книгу");

		top.add(refresh);
		top.add(deleteBtn);
		top.add(addBtn);

		add(top, BorderLayout.NORTH);
		add(new JScrollPane(list), BorderLayout.CENTER);

		refresh.addActionListener(e -> loadBooks());
		deleteBtn.addActionListener(e -> deleteSelectedBook());
		addBtn.addActionListener(e -> addNewBook());

		loadBooks();
	}

	private void addNewBook() {
		JTextField t1 = new JTextField();
		JTextField t2 = new JTextField();
		JTextField t3 = new JTextField();

		Object[] msg = {
				"Название:", t1,
				"Автор:", t2,
				"Жанр:", t3
		};

		int ok = JOptionPane.showConfirmDialog(
				this, msg, "Добавление книги", JOptionPane.OK_CANCEL_OPTION);

		if (ok != JOptionPane.OK_OPTION)
			return;

		String title = t1.getText().trim();
		String author = t2.getText().trim();
		String genre = t3.getText().trim();

		if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Заполните все поля!");
			return;
		}

		try {
			URL url = new URL("http://localhost:8080/api/books");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Basic " + auth);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);

			String data = "title=" + title + "&author=" + author + "&genre=" + genre;

			conn.getOutputStream().write(data.getBytes());
			conn.getOutputStream().close();

			conn.getInputStream().close();

			loadBooks();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteSelectedBook() {
		int index = list.getSelectedIndex();

		if (index == -1) {
			JOptionPane.showMessageDialog(this, "Выберите книгу!");
			return;
		}

		long id = ids[index];

		int confirm = JOptionPane.showConfirmDialog(
				this,
				"Удалить выбранную книгу?",
				"Подтверждение",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION)
			return;

		try {
			URL url = new URL("http://localhost:8080/api/books/" + id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Authorization", "Basic " + auth);
			conn.setInstanceFollowRedirects(false);

			int code = conn.getResponseCode();

			if (code == 403 || code == 302) {
				JOptionPane.showMessageDialog(this,
						"Недостаточно прав! Удалять может только библиотекарь.");
			}

			loadBooks();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadBooks() {
		try {
			URL url = new URL("http://localhost:8080/api/books");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + auth);

			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			StringBuilder json = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				json.append(line);
			}

			br.close();
			parseBooks(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseBooks(String json) {
		model.clear();

		json = json.replace("[", "").replace("]", "");
		if (json.isEmpty())
			return;

		String[] arr = json.split("},\\{");

		ids = new long[arr.length];

		for (int i = 0; i < arr.length; i++) {
			String obj = arr[i].replace("{", "").replace("}", "");

			long id = Long.parseLong(extract(obj, "id"));
			String title = extract(obj, "title");
			String author = extract(obj, "author");
			String genre = extract(obj, "genre");
			String available = extract(obj, "available");

			ids[i] = id;

			model.addElement(
					title + " — " + author + " (" + genre + ")" +
							(available.equals("true") ? " [Доступна]" : " [Выдана]"));
		}
	}

	private String extract(String obj, String key) {
		for (String part : obj.split(",")) {
			String[] kv = part.split(":");
			if (kv.length == 2) {
				if (kv[0].replace("\"", "").trim().equals(key)) {
					return kv[1].replace("\"", "").trim();
				}
			}
		}
		return "";
	}
}