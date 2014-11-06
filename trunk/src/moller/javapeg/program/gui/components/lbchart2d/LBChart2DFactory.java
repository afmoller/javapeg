package moller.javapeg.program.gui.components.lbchart2d;

import net.sourceforge.chart2d.Chart2DProperties;
import net.sourceforge.chart2d.Dataset;
import net.sourceforge.chart2d.GraphChart2DProperties;
import net.sourceforge.chart2d.GraphProperties;
import net.sourceforge.chart2d.LBChart2D;
import net.sourceforge.chart2d.LegendProperties;
import net.sourceforge.chart2d.MultiColorsProperties;
import net.sourceforge.chart2d.Object2DProperties;

public class LBChart2DFactory {

    public static LBChart2D create(LBChart2DFactoryConfiguration lbChart2DFactoryConfiguration) {
        //Configure object properties
        Object2DProperties object2DProps = new Object2DProperties();
        object2DProps.setObjectTitleText(lbChart2DFactoryConfiguration.getObjectTitleText());

        //Configure chart properties
        Chart2DProperties chart2DProps = new Chart2DProperties();
        chart2DProps.setChartDataLabelsPrecision(0);

        //Configure legend properties
        LegendProperties legendProps = new LegendProperties();
        legendProps.setLegendExistence(false);

        //Configure graph chart properties
        GraphChart2DProperties graphChart2DProps = new GraphChart2DProperties();
        graphChart2DProps.setLabelsAxisLabelsTexts(lbChart2DFactoryConfiguration.getLabelsAxisLabelsTexts());
        graphChart2DProps.setLabelsAxisTitleText(lbChart2DFactoryConfiguration.getLabelsAxisTitleText());
        graphChart2DProps.setNumbersAxisTitleText("Number of images");

        //Configure graph properties
        GraphProperties graphProps = new GraphProperties();
        graphProps.setGraphOutlineComponentsExistence(true);
        graphProps.setGraphBarsRoundingRatio(0.05f);

        //Configure dataset
        Dataset dataset = new Dataset (1, lbChart2DFactoryConfiguration.getLabelsAxisLabelsTexts().length, 1);
        for (int i = 0; i < lbChart2DFactoryConfiguration.getLabelsAxisLabelsTexts().length; i++) {
            dataset.set (0, i, 0, lbChart2DFactoryConfiguration.getNumberOfImages()[i]);
        }

        //Configure graph component colors
        MultiColorsProperties multiColorsProps = new MultiColorsProperties();

        //Configure chart
        LBChart2D chart2D = new LBChart2D();
        chart2D.setObject2DProperties(object2DProps);
        chart2D.setChart2DProperties(chart2DProps);
        chart2D.setLegendProperties(legendProps);
        chart2D.setGraphChart2DProperties(graphChart2DProps);
        chart2D.addGraphProperties(graphProps);
        chart2D.addDataset(dataset);
        chart2D.addMultiColorsProperties(multiColorsProps);
        chart2D.setName(lbChart2DFactoryConfiguration.getLabelsAxisTitleText());

        //Optional validation:  Prints debug messages if invalid only.
        if (!chart2D.validate(false)) {
            chart2D.validate(true);
        }
        return chart2D;
    }
}
