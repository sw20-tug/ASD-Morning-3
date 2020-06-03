package com.asdmorning3.components;

import com.asdmorning3.basic.Edit;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class VocableOverview {

	private JFrame frame_;
	private JScrollPane pane_;
	private JTable table_;
	private JButton button_;
	private InterfaceLanguages.Languages interfaceLanguage_;
	private ArrayList<List<Vocable>> vocable_list_;
	private static int  WIDTH = 200;
	private Object[][] data_;
	private String[] columns_;
	private InterfaceLanguages languages;
	private JPopupMenu popupMenu_;
	private JMenuItem item_;
	private VocableDictionary dict_;
	private JPanel pane2_;

	public void changeLanguage(InterfaceLanguages.Languages interfaceLanguage) {
		if (interfaceLanguage == interfaceLanguage_)
			return;
		this.interfaceLanguage_ = interfaceLanguage;
		frame_.setName(languages.getString(interfaceLanguage_, "overview"));
		item_.setText(languages.getString(interfaceLanguage_, "edit"));
	}

	public VocableOverview(VocableDictionary dict, InterfaceLanguages.Languages interfaceLanguage)
	{
		languages = new InterfaceLanguages();
		interfaceLanguage_ = interfaceLanguage;
		frame_ = new JFrame(languages.getString(interfaceLanguage, "overview"));
		columns_ = new String[Vocable.Language.class.getEnumConstants().length + 1];
		popupMenu_ = new JPopupMenu();
		item_ = new JMenuItem();
		item_.setText(languages.getString(interfaceLanguage, "edit"));
		dict_ = dict;
		button_ = new JButton("filter");
		changeLanguage(interfaceLanguage);
		int i = 0;
		for (Vocable.Language language: Vocable.Language.class.getEnumConstants())
		{
			columns_[i] = Vocable.getLanguageWord(language);
			i++;
		}
		columns_[i] = "Difficulty";


		String data_[][] = dict.getTable(languages, interfaceLanguage_);
		CustomTableModel customTableModel = new CustomTableModel(data_, columns_);

		table_ = new JTable(customTableModel);
		table_.getTableHeader().setReorderingAllowed(false);

		// listener
		table_.getTableHeader().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				int headerCol = table_.columnAtPoint(mouseEvent.getPoint());
				String headerName = table_.getColumnModel().getColumn(headerCol).getHeaderValue().toString();

				Vocable.Language sortByLanguage = null;
				boolean sortAsc = true;
				boolean sortByRating = false;

				String HeaderUpdate;

				int rating_col = Vocable.Language.class.getEnumConstants().length;
				if(headerCol == rating_col)
					sortByRating = true;
				else
					sortByLanguage= Vocable.Language.class.getEnumConstants()[headerCol];

				if(headerName.contains(("▲")))
				{
					table_.getColumnModel().getColumn(headerCol).setHeaderValue(headerName.replace("▲", "▼"));
					HeaderUpdate = table_.getColumnModel().getColumn(headerCol).getHeaderValue().toString();
					sortAsc = false;
				}
				else if(headerName.contains(("▼")))
				{
					table_.getColumnModel().getColumn(headerCol).setHeaderValue(headerName.replace("▼", "▲"));
					HeaderUpdate = table_.getColumnModel().getColumn(headerCol).getHeaderValue().toString();
				}
				else
				{
					table_.getColumnModel().getColumn(headerCol).setHeaderValue(headerName + " ▲");
					HeaderUpdate = table_.getColumnModel().getColumn(headerCol).getHeaderValue().toString();
				}

				//sort: param for method: sortByLanguage, sortAsc, sortByRating
				String[][] newData = dict_.getTable(languages, interfaceLanguage_, sortByLanguage, sortAsc, sortByRating);
				CustomTableModel customTableModel = new CustomTableModel(newData, columns_);
				table_.setModel(customTableModel);
				table_.getColumnModel().getColumn(headerCol).setHeaderValue(HeaderUpdate);
				//table_.getTableHeader().setReorderingAllowed(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});

		table_.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				int rating_col = Vocable.Language.class.getEnumConstants().length;
				if(table_.getSelectedColumn() == rating_col)
				{
					int row = table_.getSelectedRow();
					String difficulty;
					ArrayList<String> row_strings = new ArrayList<>(rating_col);
					for (int i = 0 ; i < rating_col; i++)
					{
						row_strings.add(i,(String)table_.getValueAt(row, i));
					}
					if (SwingUtilities.isRightMouseButton(mouseEvent))
					{
						difficulty = dict.changeDifficulty(row_strings, false);
					}
					else if(SwingUtilities.isLeftMouseButton(mouseEvent))
					{
						difficulty = dict.changeDifficulty(row_strings, true);
					}
					else
					{
						return;
					}
					updateDifficulty(row, difficulty);
				}
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});

		item_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (table_.getSelectedColumn() == table_.getColumnCount() -1)
				{
					return;
				}
				if(!data_[table_.getSelectedRow()][table_.getSelectedColumn()].equals("")){
					Vocable v = dict_.findVocable(data_[table_.getSelectedRow()][table_.getSelectedColumn()],
							Vocable.Language.class.getEnumConstants()[table_.getSelectedColumn()]).get(0);
					Edit e = new Edit(frame_, dict_, v, interfaceLanguage_);
					dict_ = e.edit();
					String[] newData = dict_.getTable(languages, interfaceLanguage_)[table_.getSelectedRow()];
					for(int c = 0; c < Vocable.Language.class.getEnumConstants().length; c++) {
						table_.setValueAt(newData[c], table_.getSelectedRow(), c);
						data_[table_.getSelectedRow()][c] = newData[c];
					}
					assert((String[])data_[table_.getSelectedRow()] == newData);
				}
			}
		});

		pane_ = new JScrollPane(table_);
		pane2_ = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		pane2_.add(button_,c);
		pane_.add(pane2_);
		frame_.setSize(Vocable.Language.class.getEnumConstants().length * WIDTH, 400);
		//frame_.add(pane2_);
		frame_.add(pane_);
		popupMenu_.add(item_);
		table_.setComponentPopupMenu(popupMenu_);
		changeVisibility(false);
	}

	public void updateDifficulty(int row, String difficulty)
	{
		table_.setValueAt(languages.getString(interfaceLanguage_, difficulty), row, Vocable.Language.class.getEnumConstants().length);
	}

	public void changeVisibility(boolean visibility)
	{
		frame_.setVisible(visibility);
	}

	public JScrollPane getContent()
	{
		return pane_;

	}
/*
	public static void main(String args[])
	{
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("hallo", Vocable.Language.GER), new Vocable("salut", Vocable.Language.FRA));
		InterfaceLanguages.Languages lang = InterfaceLanguages.Languages.EN;
		VocableOverview v = new VocableOverview(d, lang);

	}

 */
	class CustomTableModel extends DefaultTableModel
	{
		public CustomTableModel(String[][] data, String[] columns) {
			super(data, columns);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == columns_.length -1)
				return false;
			return true;
		}
	}

}
