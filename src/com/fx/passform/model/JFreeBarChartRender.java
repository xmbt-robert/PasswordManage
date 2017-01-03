package com.fx.passform.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.io.PrintWriter;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.ImageMapUtilities;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;

public class JFreeBarChartRender {

	/**
	 * 柱状图
	 */
	private static final long serialVersionUID = 8580430565963006921L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String year;
	private String map;
	private String filename;

	public JFreeBarChartRender(HttpServletRequest request,
			HttpServletResponse response, String year) {
		super();
		this.request = request;
		this.response = response;
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void render() {

		String flag = "年度";
		String getYear = this.year + flag;

		try {

			// 从数据库中获得数据集
			DefaultCategoryDataset data = createChartData(this.year);

			// 使用ChartFactory创建3D柱状图，不想使用3D，直接使用createBarChart
			JFreeChart chart = ChartFactory.createBarChart3D("密码服务短信年发送量情况", // 图表标题
					getYear, // 目录轴的显示标签
					"短信发送量", // 数值轴的显示标签
					data, // 数据集
					PlotOrientation.VERTICAL, // 图表方向，此处为垂直方向
					// PlotOrientation.HORIZONTAL, //图表方向，此处为水平方向
					true, // 是否显示图例
					true, // 是否生成工具
					true // 是否生成URL链接
					);

			// 设置消除字体的锯齿渲染（解决中文问题）
			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			chart.setTextAntiAlias(false);

			// 设置整个图片的背景色
			chart.setBackgroundPaint(new Color(253, 157, 33));
			// 设置图片边框
			chart.setBorderVisible(false);
			Font kfont = new Font("宋体", Font.PLAIN, 12); // 底部
			Font titleFont = new Font("宋体", Font.CENTER_BASELINE, 25); // 图片标题
			// 图片标题
			chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
			// 底部
			chart.getLegend().setItemFont(kfont);
			// 得到坐标设置字体解决乱码
			CategoryPlot categoryplot = (CategoryPlot) chart.getCategoryPlot();
			categoryplot.setDomainGridlinesVisible(true);
			categoryplot.setRangeCrosshairVisible(true);
			categoryplot.setRangeCrosshairPaint(Color.blue);
			categoryplot.setNoDataMessage("NO DATA!");
			// categoryplot.setBackgroundPaint(null); //数据区的背景图片背景色
			categoryplot
					.setInsets(new RectangleInsets(10.0D, 5.0D, 5.0D, 5.0D));
			categoryplot.setOutlinePaint(Color.black); // 数据区的边界线条颜色
			// categoryplot.setRangeGridlinesVisible(true); //数据轴网格是否可见
			// categoryplot.setRangeGridlinePaint(Color.white); //数据轴网格线条颜色
			categoryplot.setRangeGridlineStroke(new BasicStroke(1.0F)); // 数据轴网格线条笔触
			categoryplot.setForegroundAlpha(0.6f); // 设置柱子透明度

			BarRenderer3D barRenderer3D = (BarRenderer3D) categoryplot.getRenderer();
			barRenderer3D.setWallPaint(new Color(255, 200, 80));
			barRenderer3D.setBarPainter(new StandardBarPainter());
			barRenderer3D.setDrawBarOutline(true);
			barRenderer3D.setGradientPaintTransformer(new StandardGradientPaintTransformer(
							GradientPaintTransformType.CENTER_HORIZONTAL));

			Paint[] arrayOfPaint = createPaint(); // 绘画渐变色彩
			// 设置柱子边框颜色
			barRenderer3D.setBaseOutlinePaint(Color.white);
			barRenderer3D.setDrawBarOutline(true);
			// barRenderer3D.setItemMargin(0.8); //设置平行柱子直接的距离
			barRenderer3D.setMaximumBarWidth(0.08); // 设置柱子宽度
			barRenderer3D.setMinimumBarLength(0.1); // 设置柱子高度

			// 生成柱子链接，实现穿透功能
			barRenderer3D.setBaseItemLabelsVisible(true);
			StandardCategoryURLGenerator urlGenerator = new StandardCategoryURLGenerator(
					"/detailInfo", "smsCount", "selectYearMonth");
			barRenderer3D.setBaseItemURLGenerator(urlGenerator);

			barRenderer3D.setSeriesPaint(0, Color.decode("#24F4DB")); // 给series1
																		// Bar
			barRenderer3D.setSeriesPaint(1, Color.decode("#7979FF")); // 给series2
																		// Bar
			barRenderer3D.setSeriesPaint(2, Color.decode("#FF5555")); // 给series3
																		// Bar
			barRenderer3D.setSeriesPaint(3, Color.decode("#F8D661")); // 给series4
																		// Bar
			barRenderer3D.setSeriesPaint(4, Color.decode("#F284DC")); // 给series5
																		// Bar
			barRenderer3D.setSeriesPaint(5, Color.decode("#00BF00")); // 给series6
																		// Bar
			barRenderer3D.setSeriesPaint(3, Color.decode("#F8D661")); // 给series7
																		// Bar
			barRenderer3D.setSeriesPaint(4, Color.decode("#F284DC")); // 给series8
																		// Bar
			barRenderer3D.setSeriesPaint(5, Color.decode("#00BF00")); // 给series9
																		// Bar
			barRenderer3D.setSeriesPaint(3, Color.decode("#F8D661")); // 给series10
																		// Bar
			barRenderer3D.setSeriesPaint(4, Color.decode("#F284DC")); // 给series11
																		// Bar
			barRenderer3D.setSeriesPaint(5, Color.decode("#00BF00")); // 给series12
																		// Bar
			categoryplot.setRenderer(barRenderer3D);

			// barRenderer3D.setSeriesPaint(0,arrayOfPaint[0]); //设置柱的颜色
			// barRenderer3D.setSeriesPaint(1,arrayOfPaint[1]);
			// barRenderer3D.setSeriesPaint(2,arrayOfPaint[2]);
			// barRenderer3D.setSeriesPaint(3,arrayOfPaint[3]);
			// barRenderer3D.setSeriesPaint(4,arrayOfPaint[4]);
			// barRenderer3D.setSeriesPaint(5,arrayOfPaint[5]);
			// barRenderer3D.setSeriesPaint(6,arrayOfPaint[6]);
			// barRenderer3D.setSeriesPaint(7,arrayOfPaint[7]);
			// barRenderer3D.setSeriesPaint(8,arrayOfPaint[8]);
			// barRenderer3D.setSeriesPaint(9,arrayOfPaint[9]);
			// barRenderer3D.setSeriesPaint(10,arrayOfPaint[10]);
			// barRenderer3D.setSeriesPaint(11,arrayOfPaint[11]);

			// NumberAxis类，用于处理图表的两个坐标轴，使纵坐标的最小单位格为整数
			NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
			numberaxis
					.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			numberaxis.setRange(0.0D, 500.0D); //设置纵坐标值
			numberaxis.setTickMarkPaint(Color.black);

			BarRenderer3D barrenderer = (BarRenderer3D) categoryplot
					.getRenderer();
			barrenderer.setBaseItemLabelFont(new Font("宋体", Font.PLAIN, 12));
			barrenderer.setSeriesItemLabelFont(1,
					new Font("宋体", Font.PLAIN, 12));

			// 柱子上显示对应的数值
			barrenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
			// 将显示数字偏离开柱子显示
			barrenderer.setItemLabelAnchorOffset(10D);
			barrenderer
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barrenderer.setItemLabelPaint(Color.RED);
			barrenderer.setItemLabelsVisible(true);

			CategoryAxis domainAxis = categoryplot.getDomainAxis();

			// 设置字体角度展示
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			domainAxis.setUpperMargin(0.15);
			domainAxis.setLowerMargin(0.1);
			domainAxis.setTickLabelPaint(Color.blue);// X轴的标题文字颜色
			domainAxis.setTickLabelsVisible(true);// X轴的标题文字是否显示
			domainAxis.setAxisLinePaint(Color.blue);// X轴横线颜色
			domainAxis.setTickMarksVisible(true);// 标记线是否显示
			domainAxis.setTickMarkOutsideLength(3);// 标记线向外长度
			domainAxis.setTickMarkInsideLength(3);// 标记线向内长度
			domainAxis.setTickMarkPaint(Color.blue);// 标记线颜色

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
			int height = 300;
			int width = 900;
			// 生成图片并输出
			// ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,
			// chart, 900, 300, null);
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			String filename = ServletUtilities.saveChartAsPNG(chart, width,
					height, info, null);

			PrintWriter printWriter = new PrintWriter(System.out);
			// ChartUtilities.writeImageMap(printWriter, filename, info, true);

			// String map = ImageMapUtilities.getImageMap(filename, info,
			// null,
			// new StandardURLTagFragmentGenerator());

			String map = ImageMapUtilities.getImageMap(filename, info,
					new StandardToolTipTagFragmentGenerator(),
					new StandardURLTagFragmentGenerator());

			printWriter.flush();

			this.filename = filename;
			this.map = map;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 创建数据集方式1
	public static DefaultCategoryDataset createChartData(String year) {

		ArrayList<Object> list = Sms.getSelectYearSms(year);
		long smscount = Sms.getSelectYearSmsCount(year);
		DefaultCategoryDataset chartDate = new DefaultCategoryDataset();
		for (Object object : list) {
			// Map map = (Map)object;
			// //sms.get(attr)
			// String strcount = (String)map.get("count");
			// Number count = Integer.parseInt(strcount);

			// Date date = (Date)map.get("date");
			Object[] array = (Object[]) object;
			chartDate.addValue((Number) array[1], "该年度已发送"
					+ smscount + "条短信", (String) array[0]);
		}
		return chartDate;
	}

	private static Paint[] createPaint() {
		Paint[] arrayOfPaint = new Paint[12];
		arrayOfPaint[0] = new GradientPaint(0.0F, 0.0F,
				new Color(255, 200, 80), 0.0F, 0.0F, new Color(255, 255, 40));
		arrayOfPaint[1] = new GradientPaint(0.0F, 0.0F, new Color(50, 255, 50),
				0.0F, 0.0F, new Color(100, 255, 100));
		arrayOfPaint[2] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 255, 130), 0.0F, 0.0F, new Color(100, 230, 188));
		arrayOfPaint[3] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 255, 130), 0.0F, 0.0F, new Color(100, 255, 130));
		arrayOfPaint[4] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 255, 120), 0.0F, 0.0F, new Color(30, 255, 130));
		arrayOfPaint[5] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 225, 110), 0.0F, 0.0F, new Color(100, 215, 130));
		arrayOfPaint[6] = new GradientPaint(0.0F, 0.0F,
				new Color(130, 255, 230), 0.0F, 0.0F, new Color(100, 255, 100));
		arrayOfPaint[7] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 255, 40), 0.0F, 0.0F, new Color(130, 255, 130));
		arrayOfPaint[8] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 225, 60), 0.0F, 0.0F, new Color(23, 255, 130));
		arrayOfPaint[9] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 255, 110), 0.0F, 0.0F, new Color(100, 34, 130));
		arrayOfPaint[10] = new GradientPaint(0.0F, 0.0F,
				new Color(100, 255, 90), 0.0F, 0.0F, new Color(100, 255, 190));
		arrayOfPaint[11] = new GradientPaint(0.0F, 0.0F,
				new Color(80, 255, 130), 0.0F, 0.0F, new Color(12, 255, 130));
		return arrayOfPaint;
	}
}
