/*
 * JSettingsDialog.java
 *
 * Created on 16. November 2007, 23:54
 *
 *  Copyright (C) 16. November 2007  <reiner>
 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */


package jsudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;

import java.text.ParseException;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
    
import shared.dialog.JModalDialog;
import shared.layout.JGridBagPanel;
import shared.numberspinner.JNumberSpinner;

/**
 *
 * @author reiner
 */

public final class JSettingsDialog extends JModalDialog
{
    private JLabel m_backColorDisplay;
    private JLabel m_gridLineColorDisplay;
    private JLabel m_defaultTextColorDisplay;
    private JLabel m_userTextColorDisplay;
    private JLabel m_invalidTextColorDisplay;

    private JLabel m_printerBackColorDisplay;
    private JLabel m_printerGridLineColorDisplay;
    private JLabel m_printerDefaultTextColorDisplay;
    private JLabel m_printerUserTextColorDisplay;
    private JLabel m_printerInvalidTextColorDisplay;

    private JComboBox m_comboBox_language;
    private JCheckBox m_checkBox_checkUpdate;

    private JNumberSpinner m_spinner_min;
    private JNumberSpinner m_spinner_max;

    /**
	 * Creates a new instance of JSettingsDialog
	 */
    public JSettingsDialog(JMainFrame frame)
    {
		super(frame, Main.getString("settings_caption"), Main.getString("ok"), Main.getString("cancel"));
    }

	protected void doInit()
    {
        Dimension dim;

        setTitle(Main.getString("settings_caption"));
        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BorderLayout());

        JPanel tabPanelColors = new JPanel();
        tabPanelColors.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        tabPanelColors.setLayout(new BoxLayout(tabPanelColors, BoxLayout.X_AXIS));

        JGridBagPanel panelDisplayColors = new JGridBagPanel();
        panelDisplayColors.setBorder(new TitledBorder(new EtchedBorder(), Main.getString("settings_label_displayColors")));

        int row = 0;

        row++;
        panelDisplayColors.addComponent(new JLabel(Main.getString("settings_label_backColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_backColorDisplay = new JLabel();
        m_backColorDisplay.setOpaque(true);
        m_backColorDisplay.setBackground(Main.m_settings.getDisplayBackColor());
        m_backColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelDisplayColors.addComponent(m_backColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton backColor = new JButton(Main.getString("settings_label_backColor_change"));
        panelDisplayColors.addComponent(backColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        dim = backColor.getPreferredSize();
        dim = new Dimension((int)dim.getHeight(), (int)dim.getHeight());
        m_backColorDisplay.setPreferredSize(dim);
        m_backColorDisplay.setMinimumSize(dim);
        backColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(backColor, Main.getString("settings_label_defaultTextColor_title"), m_backColorDisplay.getBackground());
                if (col != null)
                    m_backColorDisplay.setBackground(col);
            }
        });

        row++;
        panelDisplayColors.addComponent(new JLabel(Main.getString("settings_label_gridLineColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_gridLineColorDisplay = new JLabel();
        m_gridLineColorDisplay.setOpaque(true);
        m_gridLineColorDisplay.setBackground(Main.m_settings.getDisplayGridLineColor());
        m_gridLineColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelDisplayColors.addComponent(m_gridLineColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton gridLineColor = new JButton(Main.getString("settings_label_gridLineColor_change"));
        panelDisplayColors.addComponent(gridLineColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_gridLineColorDisplay.setPreferredSize(dim);
        m_gridLineColorDisplay.setMinimumSize(dim);
        gridLineColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(gridLineColor, Main.getString("settings_label_gridLineColor_title"), m_gridLineColorDisplay.getBackground());
                if (col != null)
                    m_gridLineColorDisplay.setBackground(col);
            }
        });

        row++;
        panelDisplayColors.addComponent(new JLabel(Main.getString("settings_label_defaultTextColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        
        m_defaultTextColorDisplay = new JLabel();
        m_defaultTextColorDisplay.setOpaque(true);
        m_defaultTextColorDisplay.setBackground(Main.m_settings.getDisplayInitTextColor());
        m_defaultTextColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelDisplayColors.addComponent(m_defaultTextColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton defaultTextColor = new JButton(Main.getString("settings_label_defaultTextColor_change"));
        panelDisplayColors.addComponent(defaultTextColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_defaultTextColorDisplay.setPreferredSize(dim);
        m_defaultTextColorDisplay.setMinimumSize(dim);
        defaultTextColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(defaultTextColor, Main.getString("settings_label_defaultTextColor_title"), m_defaultTextColorDisplay.getBackground());
                if (col != null)
                    m_defaultTextColorDisplay.setBackground(col);
            }
        });

        row++;
        panelDisplayColors.addComponent(new JLabel(Main.getString("settings_label_userTextColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_userTextColorDisplay = new JLabel();
        m_userTextColorDisplay.setOpaque(true);
        m_userTextColorDisplay.setBackground(Main.m_settings.getDisplayUserTextColor());
        m_userTextColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelDisplayColors.addComponent(m_userTextColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton userTextColor = new JButton(Main.getString("settings_label_userTextColor_change"));
        panelDisplayColors.addComponent(userTextColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_userTextColorDisplay.setPreferredSize(dim);
        m_userTextColorDisplay.setMinimumSize(dim);
        userTextColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(userTextColor, Main.getString("settings_label_userTextColor_title"), m_userTextColorDisplay.getBackground());
                if (col != null)
                    m_userTextColorDisplay.setBackground(col);
            }
        });

        row++;
        panelDisplayColors.addComponent(new JLabel(Main.getString("settings_label_invalidTextColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_invalidTextColorDisplay = new JLabel();
        m_invalidTextColorDisplay.setOpaque(true);
        m_invalidTextColorDisplay.setBackground(Main.m_settings.getDisplayInvalidTextColor());
        m_invalidTextColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelDisplayColors.addComponent(m_invalidTextColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton invalidTextColor = new JButton(Main.getString("settings_label_invalidTextColor_change"));
        panelDisplayColors.addComponent(invalidTextColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_invalidTextColorDisplay.setPreferredSize(dim);
        m_invalidTextColorDisplay.setMinimumSize(dim);
        invalidTextColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(invalidTextColor, Main.getString("settings_label_invalidTextColor_title"), m_invalidTextColorDisplay.getBackground());
                if (col != null)
                    m_invalidTextColorDisplay.setBackground(col);
            }
        });

        tabPanelColors.add(panelDisplayColors);

        // Printer
        JGridBagPanel panelPrintColors = new JGridBagPanel();
        panelPrintColors.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panelPrintColors.setBorder(new TitledBorder(new EtchedBorder(), Main.getString("settings_label_printColors")));

        row = 0;

        row++;
        panelPrintColors.addComponent(new JLabel(Main.getString("settings_label_backColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_printerBackColorDisplay = new JLabel();
        m_printerBackColorDisplay.setOpaque(true);
        m_printerBackColorDisplay.setBackground(Main.m_settings.getPrintBackColor());
        m_printerBackColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelPrintColors.addComponent(m_printerBackColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton printerBackColor = new JButton(Main.getString("settings_label_backColor_change"));
        panelPrintColors.addComponent(printerBackColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_printerBackColorDisplay.setPreferredSize(dim);
        m_printerBackColorDisplay.setMinimumSize(dim);
        printerBackColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(printerBackColor, Main.getString("settings_label_printerDefaultTextColor_title"), m_printerBackColorDisplay.getBackground());
                if (col != null)
                    m_printerBackColorDisplay.setBackground(col);
            }
        });

        row++;
        panelPrintColors.addComponent(new JLabel(Main.getString("settings_label_gridLineColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_printerGridLineColorDisplay = new JLabel();
        m_printerGridLineColorDisplay.setOpaque(true);
        m_printerGridLineColorDisplay.setBackground(Main.m_settings.getPrintGridLineColor());
        m_printerGridLineColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelPrintColors.addComponent(m_printerGridLineColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton printerGridLineColor = new JButton(Main.getString("settings_label_gridLineColor_change"));
        panelPrintColors.addComponent(printerGridLineColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_printerGridLineColorDisplay.setPreferredSize(dim);
        m_printerGridLineColorDisplay.setMinimumSize(dim);
        printerGridLineColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(printerGridLineColor, Main.getString("settings_label_printerGridLineColor_title"), m_printerGridLineColorDisplay.getBackground());
                if (col != null)
                    m_printerGridLineColorDisplay.setBackground(col);
            }
        });

        row++;
        panelPrintColors.addComponent(new JLabel(Main.getString("settings_label_defaultTextColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_printerDefaultTextColorDisplay = new JLabel();
        m_printerDefaultTextColorDisplay.setOpaque(true);
        m_printerDefaultTextColorDisplay.setBackground(Main.m_settings.getPrintInitTextColor());
        m_printerDefaultTextColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelPrintColors.addComponent(m_printerDefaultTextColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton printerDefaultTextColor = new JButton(Main.getString("settings_label_defaultTextColor_change"));
        panelPrintColors.addComponent(printerDefaultTextColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_printerDefaultTextColorDisplay.setPreferredSize(dim);
        m_printerDefaultTextColorDisplay.setMinimumSize(dim);
        printerDefaultTextColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(printerDefaultTextColor, Main.getString("settings_label_printerDefaultTextColor_title"), m_printerDefaultTextColorDisplay.getBackground());
                if (col != null)
                    m_printerDefaultTextColorDisplay.setBackground(col);
            }
        });

        row++;
        panelPrintColors.addComponent(new JLabel(Main.getString("settings_label_userTextColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_printerUserTextColorDisplay = new JLabel();
        m_printerUserTextColorDisplay.setOpaque(true);
        m_printerUserTextColorDisplay.setBackground(Main.m_settings.getPrintUserTextColor());
        m_printerUserTextColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelPrintColors.addComponent(m_printerUserTextColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton printerUserTextColor = new JButton(Main.getString("settings_label_userTextColor_change"));
        panelPrintColors.addComponent(printerUserTextColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_printerUserTextColorDisplay.setPreferredSize(dim);
        m_printerUserTextColorDisplay.setMinimumSize(dim);
        printerUserTextColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(printerUserTextColor, Main.getString("settings_label_printerUserTextColor_title"), m_printerUserTextColorDisplay.getBackground());
                if (col != null)
                    m_printerUserTextColorDisplay.setBackground(col);
            }
        });

        row++;
        panelPrintColors.addComponent(new JLabel(Main.getString("settings_label_invalidTextColor")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        m_printerInvalidTextColorDisplay = new JLabel();
        m_printerInvalidTextColorDisplay.setOpaque(true);
        m_printerInvalidTextColorDisplay.setBackground(Main.m_settings.getPrintInvalidTextColor());
        m_printerInvalidTextColorDisplay.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        panelPrintColors.addComponent(m_printerInvalidTextColorDisplay, GridBagConstraints.RELATIVE, row, GridBagConstraints.NORTHWEST, new Insets(5, 0, 0, 10), 1, 1);
        final JButton printerInvalidTextColor = new JButton(Main.getString("settings_label_invalidTextColor_change"));
        panelPrintColors.addComponent(printerInvalidTextColor, 1, row, GridBagConstraints.WEST, new Insets(5, 0, 10, 0), GridBagConstraints.REMAINDER, 1);
        m_printerInvalidTextColorDisplay.setPreferredSize(dim);
        m_printerInvalidTextColorDisplay.setMinimumSize(dim);
        printerInvalidTextColor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Color col = JColorChooser.showDialog(printerInvalidTextColor, Main.getString("settings_label_printerInvalidTextColor_title"), m_printerInvalidTextColorDisplay.getBackground());
                if (col != null)
                    m_printerInvalidTextColorDisplay.setBackground(col);
            }
        });

        tabPanelColors.add(Box.createHorizontalStrut(5));
        tabPanelColors.add(panelPrintColors);

        JGridBagPanel tabOtherPanel = new JGridBagPanel();

        // Other
        row = 0;
        // Language
        m_comboBox_language = new JComboBox();
        String lang[] = Locale.getISOLanguages();
        m_comboBox_language.addItem(String.format(Main.getString("settings_language_default"), Main.m_sysLocale.getLanguage()));
        //comboBox_language.addItem(String.format(Main.getString("settings_language_default"), "en"));

        for (String item : lang)
        {
            try
            {
                ResourceBundle resBundle = ResourceBundle.getBundle("JSudokuLang_" + item);
                m_comboBox_language.addItem(item);
            }
            catch(MissingResourceException ex)
            {}
        }
        String loc = Main.m_settings.getLocale();
        if (loc.length() != 0)
            m_comboBox_language.setSelectedItem(loc);
        else
            m_comboBox_language.setSelectedIndex(0);

        row++;
        tabOtherPanel.addComponent(new JLabel(Main.getString("settings_label_language")), GridBagConstraints.RELATIVE, row, GridBagConstraints.WEST, new Insets(15, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;
        tabOtherPanel.addComponent(m_comboBox_language, 0, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 0), GridBagConstraints.REMAINDER, 1);
        row++;

        m_checkBox_checkUpdate = new JCheckBox(Main.getString("settings_checkbox_checkupdate"), Main.m_settings.isCheckUpdate());
        tabOtherPanel.addComponent(m_checkBox_checkUpdate, 0, row, GridBagConstraints.WEST, new Insets(20, 0, 0, 0), GridBagConstraints.REMAINDER, 1);

        JGridBagPanel tabSudokuPanel = new JGridBagPanel();

        // Sudoku
        row = 0;
        tabSudokuPanel.addComponent(new JLabel(Main.getString("settings_label_min")), 0, row, GridBagConstraints.WEST, new Insets(5, 0, 0, 20));
        tabSudokuPanel.addComponent(new JLabel(Main.getString("settings_label_max")), 1, row, GridBagConstraints.WEST, new Insets(5, 20, 0, 0));
        m_spinner_min = new JNumberSpinner(new SpinnerNumberModel(Main.m_settings.getMinFreeFields(), 40, 65, 1));
        m_spinner_max = new JNumberSpinner(new SpinnerNumberModel(Main.m_settings.getMaxFreeFields(), 40, 65, 1));
        row++;
        tabSudokuPanel.addComponent(m_spinner_min, 0, row, GridBagConstraints.WEST, new Insets(2, 0, 0, 20));
        tabSudokuPanel.addComponent(m_spinner_max, 1, row, GridBagConstraints.WEST, new Insets(2, 20, 0, 0));


        JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tab.addTab(Main.getString("settings_tab_sudoku"), tabSudokuPanel);
        tab.addTab(Main.getString("settings_tab_color"), tabPanelColors);
        tab.addTab(Main.getString("settings_tab_other"), tabOtherPanel);

        tab.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tab.setFocusable(false);

        setPreferredSize(new Dimension(650, 450));
        ctrlPanel.add(tab, BorderLayout.NORTH);
        addCtrlPanel(ctrlPanel);
    }

    protected void doGetData()
	{
        Main.m_settings.setDisplayBackColor(m_backColorDisplay.getBackground());
        Main.m_settings.setDisplayGridLineColor(m_gridLineColorDisplay.getBackground());
        Main.m_settings.setDisplayInitTextColor(m_defaultTextColorDisplay.getBackground());
        Main.m_settings.setDisplayUserTextColor(m_userTextColorDisplay.getBackground());
        Main.m_settings.setDisplayInvalidTextColor(m_invalidTextColorDisplay.getBackground());

        Main.m_settings.setPrintBackColor(m_printerBackColorDisplay.getBackground());
        Main.m_settings.setPrintGridLineColor(m_printerGridLineColorDisplay.getBackground());
        Main.m_settings.setPrintInitTextColor(m_printerDefaultTextColorDisplay.getBackground());
        Main.m_settings.setPrintUserTextColor(m_printerUserTextColorDisplay.getBackground());
        Main.m_settings.setPrintInvalidTextColor(m_printerInvalidTextColorDisplay.getBackground());

        // Other
        // Language
        if (m_comboBox_language.getSelectedIndex() != 0)
            Main.m_settings.setLocale((String)m_comboBox_language.getSelectedItem());
        else Main.m_settings.setLocale("");
        Main.m_settings.setCheckUpdate(m_checkBox_checkUpdate.isSelected());

        // Sudoku
        try
        {
            m_spinner_min.commitEdit();
        }
        catch (ParseException ex)
        {}
        try
        {
            m_spinner_max.commitEdit();
        }
        catch (ParseException ex)
        {}
        int min = (Integer)m_spinner_min.getValue();
        int max = (Integer)m_spinner_max.getValue();
        if (min > max)
        {
            int h = min;
            min = max;
            max = h;
        }
        Main.m_settings.setMinFreeFields(min);
        Main.m_settings.setMaxFreeFields(max);
    }
}
