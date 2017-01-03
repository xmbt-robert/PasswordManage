package com.fx.passform.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component.BaselineResizeBehavior;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;

import com.fx.passform.util.GetDate;
import com.jfinal.render.Render;

public class JFreeLineChartRender extends Render {

	/**
	 * 柱状图
	 */
	private static final long serialVersionUID = 8580430565963006921L;
	private String year_Month;
	public JFreeLineChartRender(String year_Month) {
		this.year_Month = year_Month;
	}

	public String getYear_Month() {
		return year_Month;
	}

	public void setYear_Month(String year_Month) {
		this.year_Month = year_Month;
	}

	private String year;
		
	@Override
	public void render() {
		
		String flag = "年度";
		String getYear = this.year+flag;
		
		try {
			// 从数据库中获得数据集
			DefaultCategoryDataset data = createChartData(this.year_Month);
			String Xlable = year_Month +"月份"; //X轴标题
			// 使用ChartFactory创建3D柱状图，不想使用3D，直接使用createBarChart
			JFreeChart chart = ChartFactory.createLineChart3D("密码服务短信月发送量情况", // 图表标题
					Xlable, // 目录轴的显示标签
					"数量（条）", // 数值轴的显示标签
					data, // 数据集
					PlotOrientation.VERTICAL, // 图表方向，此处为垂直方向
					// PlotOrientation.HORIZONTAL, //图表方向，此处为水平方向
					true, // 是否显示图例
					true, // 是否生成工具
					true // 是否生成URL链接
					);
			
			//设置消除字体的锯齿渲染（解决中文问题）
			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			chart.setTextAntiAlias(false);
			
			// 设置整个图片的背景色
			chart.setBackgroundPaint(new Color(253,157,33));
			// 设置图片边框
			chart.setBorderVisible(false);
			Font kfont = new Font("宋体", Font.PLAIN, 12); // 底部
			Font titleFont = new Font("宋体", Font.CENTER_BASELINE, 25); // 图片标题
			// 图片标题
			chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
			// 底部
			chart.getLegend().setItemFont(kfont);
			
			//获取绘图区对象
			CategoryPlot categoryplot = (CategoryPlot) chart.getCategoryPlot();
			categoryplot.setDomainGridlinesVisible(true);
			categoryplot.setRangeCrosshairVisible(true);
			categoryplot.setRangeCrosshairPaint(Color.blue);
			categoryplot.setNoDataMessage("NO DATA!");
		    //categoryplot.setBackgroundPaint(null);  //数据区的背景图片背景色
		    categoryplot.setInsets(new RectangleInsets(10.0D, 5.0D, 5.0D, 5.0D));
		    categoryplot.setOutlinePaint(Color.black); //数据区的边界线条颜色
		    //categoryplot.setRangeGridlinesVisible(true);  //数据轴网格是否可见
		    //categoryplot.setRangeGridlinePaint(Color.white); //数据轴网格线条颜色
		    categoryplot.setRangeGridlineStroke(new BasicStroke(1.0F)); //数据轴网格线条笔触
			categoryplot.setForegroundAlpha(0.6f); //设置柱子透明度
		    
			LineRenderer3D lineRenderer3D = (LineRenderer3D)categoryplot.getRenderer();
			lineRenderer3D.setWallPaint(new Color(255,200,80));
		    //lineRenderer3D.setBarPainter(new StandardBarPainter());
		    lineRenderer3D.setDrawOutlines(true);
		    
		    //设置柱子边框颜色 
		    lineRenderer3D.setBaseOutlinePaint(Color.white);
		    //lineRenderer3D.setDrawBarOutline(true);
			//lineRenderer3D.setMaximumBarWidth(0.08); //设置柱子宽度 
			//lineRenderer3D.setMinimumBarLength(0.1); //设置柱子高度 
			
			lineRenderer3D.setSeriesPaint(0, Color.decode("#24F4DB")); // 给series1 Bar 
			lineRenderer3D.setSeriesPaint(1, Color.decode("#7979FF")); // 给series2 Bar 
			lineRenderer3D.setSeriesPaint(2, Color.decode("#FF5555")); // 给series3 Bar 
			lineRenderer3D.setSeriesPaint(3, Color.decode("#F8D661")); // 给series4 Bar 
			lineRenderer3D.setSeriesPaint(4, Color.decode("#F284DC")); // 给series5 Bar 
			lineRenderer3D.setSeriesPaint(5, Color.decode("#00BF00")); // 给series6 Bar 
			lineRenderer3D.setSeriesPaint(3, Color.decode("#F8D661")); // 给series7 Bar 
			lineRenderer3D.setSeriesPaint(4, Color.decode("#F284DC")); // 给series8 Bar 
			lineRenderer3D.setSeriesPaint(5, Color.decode("#00BF00")); // 给series9 Bar 
			lineRenderer3D.setSeriesPaint(3, Color.decode("#F8D661")); // 给series10 Bar 
			lineRenderer3D.setSeriesPaint(4, Color.decode("#F284DC")); // 给series11 Bar 
			lineRenderer3D.setSeriesPaint(5, Color.decode("#00BF00")); // 给series12 Bar
			
		    categoryplot.setRenderer(lineRenderer3D);
		    
		    
			//NumberAxis类，数据轴属性，用于处理图表的两个坐标轴，使纵坐标的最小单位格为整数
			NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
			numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			numberaxis.setRange(0.0D, 100.0D);   //设置纵坐标值
		    numberaxis.setTickMarkPaint(Color.black);
		    numberaxis.setAutoRangeIncludesZero(true); //自动生成
		    numberaxis.setUpperMargin(0.20);
		    numberaxis.setLabelAngle(Math.PI / 2.0); 
		    numberaxis.setAutoRange(false);
		    
		    
			CategoryAxis domainAxis = categoryplot.getDomainAxis();
			//设置字体角度展示
			 domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			 domainAxis.setUpperMargin(0.15);
			 domainAxis.setLowerMargin(0.1);
			 domainAxis.setTickLabelPaint(Color.blue);//X轴的标题文字颜色 
			 domainAxis.setTickLabelsVisible(true);//X轴的标题文字是否显示 
		     domainAxis.setAxisLinePaint(Color.blue);//X轴横线颜色 
		     domainAxis.setTickMarksVisible(true);//标记线是否显示 
		     domainAxis.setTickMarkOutsideLength(3);//标记线向外长度 
		     domainAxis.setTickMarkInsideLength(3);//标记线向内长度 
		     domainAxis.setTickMarkPaint(Color.blue);//标记线颜色 
		     
		     //ValueAxis rangeAxis = categoryplot.getRangeAxis();//设置Y轴
		     
		     //获取折线对象，对折线进行数据渲染
		     LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer)categoryplot.getRenderer();
		     lineRenderer.setBaseItemLabelsVisible(true);
		    // renderer.setSeriesPaint(0, Color.blue); //设置折线颜色
		     lineRenderer.setBaseShapesFilled(true);
		     lineRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		     lineRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		     
		     BasicStroke realLine = new BasicStroke(1.8f); //设置实线
		     //设置虚线
		     float dashes[] = {5.0f};
		     BasicStroke brokenLine = new BasicStroke(2.2f, //线条粗细
		    		 BasicStroke.CAP_ROUND, //端点风格
		    		 BasicStroke.JOIN_ROUND, //折点风格
		    		 8f, 
		    		 dashes, 
		    		 0.6f);
		     for(int i=0; i<data.getRowCount();i++){
		    	 if(i%2 == 0)
		    		 lineRenderer.setSeriesStroke(i, realLine); //利用实线绘制
		    	 else
		    		 lineRenderer.setSeriesStroke(i, brokenLine); //利用虚线绘制
		     }
		     
		     
			/*------设置X轴坐标上的文字-----------*/
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
			/*------设置X轴的标题文字------------*/
			domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 20));
			/*------设置Y轴坐标上的文字-----------*/
			numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			/*------设置Y轴的标题文字------------*/
			numberaxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
			/*------这句代码解决了底部汉字乱码的问题-----------*/
			chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
			
			response.setContentType("text/html");
			// 生成图片并输出
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,
					chart, 1200, 500, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//创建数据集
	public static DefaultCategoryDataset createChartData(String year_Month){
		ArrayList<Object> list = Sms.getToYearToMonthSms(year_Month);
		//Integer totalDays = Sms.getSelectYearMonth(year_Month);
		DefaultCategoryDataset chartDate = new DefaultCategoryDataset();
		
	     // 曲线名称
		  String series = "短信(条)";  // series指的就是报表里的那条数据线
                        //因此 对数据线的相关设置就需要联系到serise
                        //比如说setSeriesPaint 设置数据线的颜色

		// 横轴名称(列名称) 
        String[] time = new String[31];
        String[] timeValue = { "1日", "2日", "3日", "4日", "5日", "6日",
                      "7日", "8日", "9日", "10日", "11日", "12日", "13日",
                      "14日", "15日", "16日", "17日", "18日", "19日", "20日", "21日", "22日", "23日",
                      "24日", "25日", "26日", "27日", "28日", "29日", "30日", "31日"};
        for (int i = 0; i < 31; i++) {
               time[i] = timeValue[i];
        }
        for (Object object : list) {
			Object[] array = (Object[]) object;
			chartDate.addValue((Number) array[1], series , (String) array[0]);
		}
        
        return chartDate;
		
	}
	
}
