package salesReport;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.category.CategoryDataset;
import java.awt.*;


public class Display extends ApplicationFrame {

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Top Selling Albums", null, "Sales", dataset);
        //chart.setBackgroundPaint(Color.WHITE);
        //CategoryPlot plot = (CategoryPlot) chart.getPlot();

        //NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //BarRenderer renderer = (BarRenderer) plot.getRenderer();
        //renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }


    public Display(String title) {
        super(title);
        CategoryDataset dataset = DataAccess.readChinookDataset();//read data
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        Display display = new Display("Top 5 selling Albums");
        display.pack();
        UIUtils.centerFrameOnScreen(display);
        display.setVisible(true);
    }
}
