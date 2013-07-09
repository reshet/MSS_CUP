package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAbstractAsker;

import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class UserSettingsProjectCreator extends UserSettingsAbstractAsker<UserSettingsProjectCreator_Setting>{
	private Map<String, String> tasks;
	private Map<Integer, String> tags;
	private JPanel tags_panel,tasks_panel,base_panel;
	private ArrayList<TagPanel> tags_arr,base_setting_arr;
	private ArrayList<TaskPanel> tasks_arr;
	@SuppressWarnings("unused")
	private Map<Integer,String> tag_categories;
	@SuppressWarnings("unused")
	private ArrayList<String> predefined_tasks;
	public UserSettingsProjectCreator(Map<Integer,String> tag_categories,ArrayList<String> predefined_tasks) {
		super("Задайте параметры нового проекта.");
		panel.setLayout(new BorderLayout(3,3));
		this.tag_categories = tag_categories;
		this.predefined_tasks = predefined_tasks;
		
		
		tags_arr = new ArrayList<TagPanel>();
		tasks_arr = new ArrayList<TaskPanel>();
		
		
		
		tags_panel= new JPanel(new GridLayout(tag_categories.size(),1));
		tasks_panel= new JPanel(new GridLayout(predefined_tasks.size(),1));
		tags_panel.setPreferredSize(new Dimension(400,30*tag_categories.size()));
		tasks_panel.setPreferredSize(new Dimension(400,30*predefined_tasks.size()));
		
		base_panel= new JPanel(new GridLayout(2,1));
		base_setting_arr = new ArrayList<TagPanel>();
		TagPanel tag_name = new TagPanel("Название проекта:", 0);
		TagPanel tag_desc = new TagPanel("Описание проекта:", 1);
		base_setting_arr.add(tag_name);
		base_setting_arr.add(tag_desc);
		base_panel.add(tag_name);
		base_panel.add(tag_desc);
		
		
		for(Entry<Integer, String> entry:tag_categories.entrySet())
		{
			TagPanel tagPnl = new TagPanel(entry.getValue(), entry.getKey());
			tags_arr.add(tagPnl);
			tags_panel.add(tagPnl);
		}
		for(String task:predefined_tasks)
		{
			TaskPanel t_pnl = new TaskPanel(task);
			tasks_panel.add(t_pnl);
			tasks_arr.add(t_pnl);
		}
		JScrollPane pane_tags = new JScrollPane(tags_panel);
		//pane_tags.setLayout();
		IAppWidgetFactory.makeIAppScrollPane(pane_tags);
		JScrollPane pane_tasks = new JScrollPane(tasks_panel);
	//	pane_tasks.setLayout();
		IAppWidgetFactory.makeIAppScrollPane(pane_tasks);
		
		panel.add(base_panel,BorderLayout.NORTH);
		panel.add(pane_tags,BorderLayout.CENTER);
		panel.add(pane_tasks,BorderLayout.SOUTH);
		//panel.add(new JLabel("This is DEMO SETTINGS LABEL!"));
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4036252704300393935L;

	@Override
	public void processSettings() {
		tags= new HashMap<Integer, String>();
		tasks= new HashMap<String, String>();
		for(TagPanel t_pnl:tags_arr)
		{
			Setting_AssocPair<Integer, String> pair = t_pnl.getTag();
			tags.put(pair.getLeft(), pair.getRight());
		}
		for(TaskPanel t_pnl:tasks_arr)
		{
			Setting_AssocPair<String, String> pair = t_pnl.getTask();
			tasks.put(pair.getLeft(), pair.getRight());
		}
		_setting = new UserSettingsProjectCreator_Setting(base_setting_arr.get(0).getTag().getRight(),base_setting_arr.get(1).getTag().getRight(),tags, tasks);
	}
	class TagPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4591014385870552060L;
		private JLabel tag_name;
		private JTextField concrete_tag_value;
		private int category_ID;
		public TagPanel(String tag_category,Integer cat_ID) {
			category_ID = cat_ID;
			tag_name = new JLabel(tag_category);
			setLayout(new BorderLayout(5,5));
			add(tag_name,BorderLayout.WEST);
			concrete_tag_value = new JTextField();
			add(concrete_tag_value,BorderLayout.CENTER);
			setPreferredSize(new Dimension(400,40));
		}
		public Setting_AssocPair<Integer, String> getTag()
		{
			return new Setting_AssocPair<Integer, String>(category_ID, concrete_tag_value.getText());
		}
	}
	class TaskPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5882478214727060367L;
		private JLabel task_name;
		private JXDatePicker date_picker;
		public TaskPanel(String task_name) {
			this.task_name = new JLabel(task_name);
			setLayout(new BorderLayout(5,5));
			add(this.task_name,BorderLayout.WEST);
			date_picker = new JXDatePicker(new Date());
			add(date_picker,BorderLayout.CENTER);
			setPreferredSize(new Dimension(400,40));
		}
		public Setting_AssocPair<String, String> getTask()
		{
			return new Setting_AssocPair<String, String>(task_name.getText(), date_picker.getDate().toString());
		}
	}
}
