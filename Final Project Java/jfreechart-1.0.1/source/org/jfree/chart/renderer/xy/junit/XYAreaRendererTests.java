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
 * ------------------------
 * AreaXYRendererTests.java
 * ------------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: XYAreaRendererTests.java,v 1.5.2.2 2005/12/02 11:20:27 mungady Exp $
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 * 22-Oct-2003 : Added hashCode test (DG);
 *
 */

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.JFreeChart;
import NumberAxis;
import XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import DefaultTableXYDataset;
import XYSeries;

/**
 * Tests for the {@link XYAreaRenderer} class.
 */
public class XYAreaRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYAreaRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYAreaRendererTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        XYAreaRenderer r1 = new XYAreaRenderer();
        XYAreaRenderer r2 = new XYAreaRenderer();
        assertEquals(r1, r2);
        
        r1 = new XYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES);
        assertFalse(r1.equals(r2));
        r2 = new XYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES);
        assertTrue(r1.equals(r2));

        r1 = new XYAreaRenderer(XYAreaRenderer.AREA);
        assertFalse(r1.equals(r2));
        r2 = new XYAreaRenderer(XYAreaRenderer.AREA);
        assertTrue(r1.equals(r2));

        r1 = new XYAreaRenderer(XYAreaRenderer.LINES);
        assertFalse(r1.equals(r2));
        r2 = new XYAreaRenderer(XYAreaRenderer.LINES);
        assertTrue(r1.equals(r2));

        r1 = new XYAreaRenderer(XYAreaRenderer.SHAPES);
        assertFalse(r1.equals(r2));
        r2 = new XYAreaRenderer(XYAreaRenderer.SHAPES);
        assertTrue(r1.equals(r2));

        r1 = new XYAreaRenderer(XYAreaRenderer.SHAPES_AND_LINES);
        assertFalse(r1.equals(r2));
        r2 = new XYAreaRenderer(XYAreaRenderer.SHAPES_AND_LINES);
        assertTrue(r1.equals(r2));

        r1.setOutline(true);
        assertFalse(r1.equals(r2));
        r2.setOutline(true);
        assertTrue(r1.equals(r2));
        
        r1.setLegendArea(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertFalse(r1.equals(r2));
        r2.setLegendArea(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertTrue(r1.equals(r2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashcode() {
        XYAreaRenderer r1 = new XYAreaRenderer();
        XYAreaRenderer r2 = new XYAreaRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }
    
    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYAreaRenderer r1 = new XYAreaRenderer();
        XYAreaRenderer r2 = null;
        try {
            r2 = (XYAreaRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        XYAreaRenderer r1 = new XYAreaRenderer();
        XYAreaRenderer r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            r2 = (XYAreaRenderer) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(r1, r2);

    }

    /**
     * Draws the chart with a <code>null</code> info object to make sure that 
     * no exceptions are thrown (particularly by code in the renderer).
     */
    public void testDrawWithNullInfo() {
        boolean success = false;
        try {
            DefaultTableXYDataset dataset = new DefaultTableXYDataset();
        
            XYSeries s1 = new XYSeries("Series 1", true, false);
            s1.add(5.0, 5.0);
            s1.add(10.0, 15.5);
            s1.add(15.0, 9.5);
            s1.add(20.0, 7.5);
            dataset.addSeries(s1);
        
            XYSeries s2 = new XYSeries("Series 2", true, false);
            s2.add(5.0, 5.0);
            s2.add(10.0, 15.5);
            s2.add(15.0, 9.5);
            s2.add(20.0, 3.5);
            dataset.addSeries(s2);
            XYPlot plot = new XYPlot(dataset, 
                    new NumberAxis("X"), new NumberAxis("Y"), 
                    new XYAreaRenderer());
            JFreeChart chart = new JFreeChart(plot);
            /* BufferedImage image = */ chart.createBufferedImage(300, 200, 
                    null);
            success = true;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            success = false;
        }
        assertTrue(success);
    }

}