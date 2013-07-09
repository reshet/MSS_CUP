package app_service_SARS_Projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListAgregatedAnketsElementData
{
	private String name;
	private ArrayList<Integer> anketIDs;
	private JTable meta_agr_tbl,cases_agr_tbl;
	private ArrayList<SProject_InstrumentDesk.unfold_anket_Action> unfolders;
	public ArrayList<Integer> getAnketIDs() {
		return anketIDs;
	}
	public JTable getMeta_agr_tbl() {
		return meta_agr_tbl;
	}
	public JTable getCases_agr_tbl() {
		return cases_agr_tbl;
	}
	public ListAgregatedAnketsElementData(String name,ArrayList<Integer> ankets)
	{
		anketIDs = ankets;
		this.name = name;
		unfolders = new ArrayList<SProject_InstrumentDesk.unfold_anket_Action>();
		SProject_InstrumentDesk plumber = new SProject_InstrumentDesk(-1);
		for(Integer anketID:anketIDs)
		{
			SProject_InstrumentDesk.unfold_anket_Action action = plumber.getUnfoldAction(anketID);
			unfolders.add(action);
		}
		constructMeta();
		prepareCasesTable();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private void agregateMetaModel(DefaultTableModel base_model,DefaultTableModel agregee_model)
	{
		ArrayList<String> base_labels = new ArrayList<String>();
		for(int i = 0; i < base_model.getRowCount();i++)
		{
			base_labels.add((String)base_model.getValueAt(i, 0));
		}
		for(int i = 0; i < agregee_model.getRowCount();i++)
		{
			String curr_label = (String)agregee_model.getValueAt(i, 0);
			if (!base_labels.contains(curr_label))
			{
				base_model.addRow(new String[base_model.getColumnCount()]);
				for(int j = 0; j < base_model.getColumnCount();j++)
				{
					base_model.setValueAt(agregee_model.getValueAt(i, j), base_model.getRowCount()-1, j);
				}
			}
		}
	}
	private void agregateCasesModel(DefaultTableModel base_model,DefaultTableModel agregee_model)
	{
		if (base_model == null || agregee_model == null)return;
		Map<String,Integer> base_labels = new HashMap<String,Integer>();
		for(int i = 0; i < base_model.getColumnCount();i++)
		{
			base_labels.put(base_model.getColumnName(i),i);
		}
		ArrayList<Integer> agregee_labels = new ArrayList<Integer>();
		for(int i = 0; i < agregee_model.getColumnCount();i++)
		{
			//if (base_labels.containsKey((String)agregee_model.getColumnName(i)))
				agregee_labels.add(base_labels.get(agregee_model.getColumnName(i)));
		}
		for(int i = 0; i < agregee_model.getRowCount();i++)
		{
			base_model.addRow(new String[base_model.getColumnCount()]);
			for(int j = 0; j < agregee_model.getColumnCount();j++)
			{
				base_model.setValueAt(agregee_model.getValueAt(i, j), base_model.getRowCount()-1, agregee_labels.get(j));
			}
		}
	}
	private void prepareCasesTable()
	{
		DefaultTableModel meta_model = (DefaultTableModel)meta_agr_tbl.getModel();
		String[] col_idents = new String[meta_model.getRowCount()+1];
		col_idents[0]="¹";
		for(int i = 0; i < meta_model.getRowCount();i++)
		{
			col_idents[i+1] = (String)meta_model.getValueAt(i, 0);
		}
		DefaultTableModel cases_model = new DefaultTableModel(0,col_idents.length);
		cases_model.setColumnIdentifiers(col_idents);
		cases_agr_tbl = new JTable(cases_model);
	}
	private void constructMeta()
	{
		DefaultTableModel agr_meta_model = new DefaultTableModel();
		int counter = 0;
		for(SProject_InstrumentDesk.unfold_anket_Action unfolder:unfolders)
		{
			JTable meta_tbl = new JTable();
			JTable cases_tbl = new JTable();
			unfolder.unfoldTask();
			unfolder.constructMetadata(cases_tbl, meta_tbl);
			if(counter ==0)
			{
				agr_meta_model = (DefaultTableModel)meta_tbl.getModel();
			}else
			{
				agregateMetaModel(agr_meta_model,(DefaultTableModel)meta_tbl.getModel());
			}
			counter++;
		}
		meta_agr_tbl = new JTable(agr_meta_model);
	}
	public void refreshCasesTable()
	{
		DefaultTableModel agr_cases_model = (DefaultTableModel)cases_agr_tbl.getModel();
		for(SProject_InstrumentDesk.unfold_anket_Action unfolder:unfolders)
		{
			unfolder.constructCases();
			DefaultTableModel cases_model = (DefaultTableModel)unfolder.getCases_tbl().getModel();
			agregateCasesModel(agr_cases_model,cases_model);
		}
		cases_agr_tbl.setModel(agr_cases_model);
		SProject_InstrumentDesk.fillWithMissingValues(cases_agr_tbl, meta_agr_tbl);
	}
}
