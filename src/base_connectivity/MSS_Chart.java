/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author RX
 */
public class MSS_Chart {

    private  DefaultCategoryDataset  dataset;
    private JFreeChart chart;
    @SuppressWarnings("unused")
	private String chartDesc;
    private ChartPanel chartPanel;
    private JPanel rootPanel;
    public MSS_Chart(DefaultCategoryDataset  dataset,String chartDesc, JPanel rootPanel)
    {
        this.dataset = dataset;
        this.chartDesc = chartDesc;
        this.rootPanel = rootPanel;

        chart = ChartFactory.createBarChart3D("",
            "", "", this.dataset, PlotOrientation.HORIZONTAL, false, false, false );

        CategoryPlot p = chart.getCategoryPlot(); // Get the Plot object for a bar graph
        p.setBackgroundPaint(Color.black); // Modify the plot background
        p.setRangeGridlinePaint(Color.CYAN);
        chart.setBackgroundPaint(Color.LIGHT_GRAY); // Set the background colour of the chart
        chartPanel = new ChartPanel(chart);
        chartPanel.setSize(this.rootPanel.getWidth(), this.rootPanel.getHeight());
        this.rootPanel.add(chartPanel);
        //chartPanel.setVisible(true);
    }
    public void ShowChartPanel()
    {
        chartPanel.setVisible(true);
    }
    public void HideChartPanel()
    {
        chartPanel.setVisible(false);
    }
    public void upDataset(DefaultCategoryDataset  dataset)
    {
        this.dataset = dataset;
         chart = ChartFactory.createBarChart3D("",
        		 "", "", this.dataset, PlotOrientation.HORIZONTAL, false, false, false );
         CategoryPlot p = chart.getCategoryPlot(); // Get the Plot object for a bar graph
        p.setBackgroundPaint(Color.black); // Modify the plot background
        p.setRangeGridlinePaint(Color.CYAN);
        chart.setBackgroundPaint(Color.LIGHT_GRAY); // Set the background colour of the chart
        chartPanel = new ChartPanel(chart);
        chartPanel.setSize(this.rootPanel.getWidth(), this.rootPanel.getHeight());
        this.rootPanel.add(chartPanel);
    }
}
