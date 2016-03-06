import java.awt.Color;
import java.util.Stack;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class SpectrumRenderV2 extends ApplicationFrame {
	
	Stack<Double> pointstoplot;
	
	final XYDataset data;
	final JFreeChart chart;
	final ChartPanel chartpanel;
	
	public SpectrumRenderV2(Stack<Double> xpoints, String title)
	{
		super(title);
		
		pointstoplot = xpoints;	
		
		data = createData();
		
		chart = createChart();
                
                
		
		chartpanel = new ChartPanel(chart);
		
		chartpanel.setPreferredSize(new java.awt.Dimension(1000, 500));
		
		setContentPane(chartpanel);
		
	}
	
	private XYDataset createData()

	{
		final XYSeries series1 = new XYSeries("First");
		
		for(double i = 0; i <+ 14; i = i + .01 )
		{
			series1.add(i, 0);
		}
		
		Stack<Double> pointstoplotcopy = pointstoplot;
		
		while(pointstoplotcopy.isEmpty()!= true)
		{
			Double currentdouble = pointstoplotcopy.pop();
			
			Double peak = 1.0;
			
			series1.add(currentdouble, peak );
		}
		
		 final XYSeriesCollection dataset = new XYSeriesCollection();
		 
		 dataset.addSeries(series1);
		 
		 return dataset;
		
		
	}
	
	private JFreeChart createChart()
	{
		final JFreeChart thechart = ChartFactory.createXYLineChart("NMR Spectrum", "PPM", "Arbitrary Units", data, PlotOrientation.VERTICAL,false,false,false);
		thechart.setBackgroundPaint(Color.white);
	
		
		final XYPlot plot = thechart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.red);
		plot.setRangeGridlinePaint(Color.red);
		
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		
		renderer.setSeriesLinesVisible(0, true);
		
		renderer.setSeriesPaint(0, Color.blue);
		
		renderer.setSeriesShapesVisible(0, false);
		
		
		
		
		plot.setRenderer(renderer);
		
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		
		final NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		
		xAxis.setInverted(true);
		
		xAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		
		return thechart;
		
	}
        
        public JFreeChart getchart()
        {
            return chart;
        }
	
	public static void main(String[] args)
	{
		Stack<Double> thepoints = new Stack<Double>();
		
		thepoints.add(1.1);
		thepoints.add(7.2);
				
		SpectrumRenderV2 myplot = new SpectrumRenderV2(thepoints,"NMR");
		
		myplot.pack();
		
		RefineryUtilities.centerFrameOnScreen(myplot);
		
		myplot.setVisible(true);
	}

}
