/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * -------------------
 * AreaChartTests.java
 * -------------------
 * (C) Copyright 2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: AreaChartTests.java,v 1.1.2.1 2005/10/25 20:43:31 mungady Exp $
 *
 * Changes:
 * --------
 * 12-Apr-2005 : Version 1 (DG);
 *
 */

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import ValueAxis;
import ChartChangeEvent;
import ChartChangeListener;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import CategoryPlot;
import PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import CategoryURLGenerator;
import StandardCategoryURLGenerator;
import Range;
import CategoryDataset;
import DatasetUtilities;

/**
 * Tests for an area chart.
 */
public class AreaChartTests extends TestCase {

    /** A chart. */
    private JFreeChart chart;

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(AreaChartTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public AreaChartTests(String name) {
        super(name);
    }

    /**
     * Common test setup.
     */
    protected void setUp() {
        this.chart = createAreaChart();
    }

    /**
     * Check that setting a tool tip generator for a series does override the
     * default generator.
     */
    public void testSetSeriesToolTipGenerator() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        StandardCategoryToolTipGenerator tt
            = new StandardCategoryToolTipGenerator();
        renderer.setSeriesToolTipGenerator(0, tt);
        CategoryToolTipGenerator tt2 = renderer.getToolTipGenerator(0, 0);
        assertTrue(tt2 == tt);
    }
    
    /**
     * Check that setting a URL generator for a series does override the
     * default generator.
     */
    public void testSetSeriesURLGenerator() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        StandardCategoryURLGenerator url1
            = new StandardCategoryURLGenerator();
        renderer.setSeriesItemURLGenerator(0, url1);
        CategoryURLGenerator url2 = renderer.getItemURLGenerator(0, 0);
        assertTrue(url2 == url1);
    }
    
    /**
     * Draws the chart with a null info object to make sure that no exceptions 
     * are thrown (a problem that was occurring at one point).
     */
    public void testDrawWithNullInfo() {
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(
                200 , 100, BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2 = image.createGraphics();
            this.chart.draw(
                g2, new Rectangle2D.Double(0, 0, 200, 100), null, null
            );
            g2.dispose();
            success = true;
        }
        catch (Exception e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * Replaces the chart's dataset and then checks that the new dataset is OK.
     */
    public void testReplaceDataset() {
        Number[][] data = new Integer[][]
            {{new Integer(-30), new Integer(-20)},
             {new Integer(-10), new Integer(10)},
             {new Integer(20), new Integer(30)}};

        CategoryDataset newData = DatasetUtilities.createCategoryDataset(
            "S", "C", data
        );
        LocalListener l = new LocalListener();
        this.chart.addChangeListener(l);
        this.chart.getCategoryPlot().setDataset(newData);
        assertEquals(true, l.flag);
        ValueAxis axis = this.chart.getCategoryPlot().getRangeAxis();
        Range range = axis.getRange();
        assertTrue("Expecting the lower bound of the range to be around -30: "
                   + range.getLowerBound(), range.getLowerBound() <= -30);
        assertTrue("Expecting the upper bound of the range to be around 30: "
                   + range.getUpperBound(), range.getUpperBound() >= 30);

    }

    /**
     * Create an area chart with sample data in the range -3 to +3.
     *
     * @return The chart.
     */
    private static JFreeChart createAreaChart() {
        Number[][] data = new Integer[][]
            {{new Integer(-3), new Integer(-2)},
             {new Integer(-1), new Integer(1)},
             {new Integer(2), new Integer(3)}};
        CategoryDataset dataset 
            = DatasetUtilities.createCategoryDataset("S", "C", data);
        return ChartFactory.createAreaChart(
            "Area Chart",
            "Domain", "Range",
            dataset,
            PlotOrientation.HORIZONTAL,
            true,     // include legend
            true,
            true
        );

    }

    /**
     * A chart change listener.
     */
    static class LocalListener implements ChartChangeListener {

        /** A flag. */
        private boolean flag = false;

        /**
         * Event handler.
         *
         * @param event  the event.
         */
        public void chartChanged(ChartChangeEvent event) {
            this.flag = true;
        }

    }

}