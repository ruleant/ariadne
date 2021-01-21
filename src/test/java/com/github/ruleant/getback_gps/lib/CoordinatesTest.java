/**
 * Unit tests for Coordinates class
 *
 * Copyright (C) 2012-2021 Dieter Adriaenssens
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @package com.github.ruleant.getback_gps
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
package com.github.ruleant.getback_gps.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for Coordinates class.
 *
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
public class CoordinatesTest {
    /**
     * Instance of the coordinates class.
     */
    private Coordinates coordinates;

    /**
     * Mock for objectConverter class.
     */
    private CoordinateConverterInterface converter;

    /**
     * Test coordinate 0 (0, 0).
     */
    private Coordinate coordinate0;

    /**
     * Test coordinate 1 (0, 20).
     */
    private Coordinate coordinate1;

    /**
     * Test coordinate 2 (30, 40).
     */
    private Coordinate coordinate2;

    /**
     * 3 POINTS.
     */
    public static final int NUM_POINTS_3 = 3;

    /**
     * Cartesian accuracy.
     */
    public static final double CARTESIAN_ACCURACY = 0.01;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public final void setUp() {
        coordinates = new Coordinates();

        coordinate0 = new Coordinate(0, 0);
        coordinate1 = new Coordinate(0, CoordinateTest.UNIT_20);
        coordinate2 = new Coordinate(CoordinateTest.UNIT_30,
                CoordinateTest.UNIT_40);

        Coordinate convertedCoordinate1
                = new Coordinate(-1 * CoordinateTest.UNIT_20, 0);
        Coordinate convertedCoordinate2
                = new Coordinate(-1 * CoordinateTest.UNIT_40,
                -1 * CoordinateTest.UNIT_30);

        // create mock object
        converter = mock(CoordinateConverterInterface.class);

        // setup mock object
        when(converter.getConvertedCoordinate(coordinate1))
                .thenReturn(convertedCoordinate1);
        when(converter.getConvertedCoordinate(coordinate2))
                .thenReturn(convertedCoordinate2);
    }

    /**
     * Tests empty collection.
     */
    @Test
    public final void testEmpty() {
        Assertions.assertEquals(0, coordinates.getSize());
        Assertions.assertEquals(0, coordinates.toArray().length);
        Assertions.assertEquals(0, coordinates.toLinesArray().length);
    }

    /**
     * Tests adding Coordinate with polar coordinates.
     */
    @Test
    public final void testAddPolarCoordinate() {
        coordinates.addCoordinate(CoordinateTest.RADIUS_20,
                                    CoordinateTest.ANGLE_45);
        Assertions.assertEquals(1, coordinates.getSize());

        Object[] coordinatesArray = coordinates.toArray();
        Assertions.assertEquals(1, coordinatesArray.length);

        Coordinate coordinate = (Coordinate) coordinatesArray[0];
        Assertions.assertEquals(
                CoordinateTest.ANGLE_45,
                coordinate.getPolarAngle(),
                CoordinateTest.POLAR_ACCURACY);
        Assertions.assertEquals(
                CoordinateTest.RADIUS_20,
                coordinate.getPolarRadius(),
                CoordinateTest.POLAR_ACCURACY);
    }

    /**
     * Tests adding Coordinate with Cartesian coordinates.
     */
    @Test
    public final void testAddCartesianCoordinate() {
        coordinates.addCoordinate(CoordinateTest.UNIT_20,
                                    CoordinateTest.UNIT_30);
        Assertions.assertEquals(1, coordinates.getSize());

        Object[] coordinatesArray = coordinates.toArray();
        Assertions.assertEquals(1, coordinatesArray.length);

        Coordinate coordinate = (Coordinate) coordinatesArray[0];
        Assertions.assertEquals(CoordinateTest.UNIT_20, coordinate.getCartesianX());
        Assertions.assertEquals(CoordinateTest.UNIT_30, coordinate.getCartesianY());
    }

    /**
     * Tests adding Coordinate with Coordinate instance.
     */
    @Test
    public final void testAddCoordinate() {
        coordinates.addCoordinate(new Coordinate(CoordinateTest.RADIUS_20,
                                    CoordinateTest.ANGLE_45));
        Assertions.assertEquals(1, coordinates.getSize());

        Object[] coordinatesArray = coordinates.toArray();
        Assertions.assertEquals(1, coordinatesArray.length);

        Coordinate coordinate = (Coordinate) coordinatesArray[0];
        Assertions.assertEquals(
                CoordinateTest.ANGLE_45,
                coordinate.getPolarAngle(),
                CoordinateTest.POLAR_ACCURACY);
        Assertions.assertEquals(
                CoordinateTest.RADIUS_20,
                coordinate.getPolarRadius(),
                CoordinateTest.POLAR_ACCURACY);
    }

    /**
     * Tests null value for new coordinate in setCoordinate.
     */
    @Test
    public final void testSetCoordinateNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            coordinates.addCoordinate(null);
        });
    }

    /**
     * Tests adding multiple Coordinates.
     */
    @Test
    public final void testAddMultipleCoordinates() {
        coordinates.addCoordinate(CoordinateTest.RADIUS_20,
                                    CoordinateTest.ANGLE_45);
        Assertions.assertEquals(1, coordinates.getSize());

        coordinates.addCoordinate(CoordinateTest.RADIUS_50,
                                    CoordinateTest.ANGLE_90);

        Assertions.assertEquals(2, coordinates.getSize());

        Object[] coordinatesArray = coordinates.toArray();
        Assertions.assertEquals(2, coordinatesArray.length);

        Coordinate coordinate = (Coordinate) coordinatesArray[0];
        Assertions.assertEquals(
                CoordinateTest.ANGLE_45,
                coordinate.getPolarAngle(),
                CoordinateTest.POLAR_ACCURACY);
        Assertions.assertEquals(
                CoordinateTest.RADIUS_20,
                coordinate.getPolarRadius(),
                CoordinateTest.POLAR_ACCURACY);

        coordinate = (Coordinate) coordinatesArray[1];
        Assertions.assertEquals(
                CoordinateTest.ANGLE_90,
                coordinate.getPolarAngle(),
                CoordinateTest.POLAR_ACCURACY);
        Assertions.assertEquals(
                CoordinateTest.RADIUS_50,
                coordinate.getPolarRadius(),
                CoordinateTest.POLAR_ACCURACY);
    }

    /**
     * Tests toLinesArray, one line.
     */
    @Test
    public final void testToLinesArray() {
        coordinates.addCoordinate(coordinate1);
        // array should be empty in only 1 point is added,
        // two lines are needed to draw a line
        Assertions.assertEquals(0, coordinates.toLinesArray().length);

        coordinates.addCoordinate(coordinate2);
        float[] coordinatesArray = coordinates.toLinesArray();
        Assertions.assertEquals(Coordinates.NUM_COORD_LINE, coordinatesArray.length);
        Assertions.assertEquals(
                0.0f,
                coordinatesArray[Coordinates.POS_START_X],
                CARTESIAN_ACCURACY);
        assertCoordinates(coordinatesArray, 0);
    }

    /**
     * Tests toLinesArray, multiple lines.
     */
    @Test
    public final void testToLinesArrayMulti() {
        coordinates.addCoordinate(coordinate0);
        coordinates.addCoordinate(coordinate1);
        coordinates.addCoordinate(coordinate2);
        float[] coordinatesArray = coordinates.toLinesArray();
        Assertions.assertEquals(NUM_POINTS_3 * Coordinates.NUM_COORD_LINE,
                coordinatesArray.length);
        // first line
        Assertions.assertEquals(0f,
                coordinatesArray[Coordinates.POS_START_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals(0f,
                coordinatesArray[Coordinates.POS_START_Y],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals(0f,
                coordinatesArray[Coordinates.POS_END_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) CoordinateTest.UNIT_20,
                coordinatesArray[Coordinates.POS_END_Y],
                CARTESIAN_ACCURACY);

        // second line
        assertCoordinates(coordinates.toLinesArray(), 1);

        // closing line
        Assertions.assertEquals((float) CoordinateTest.UNIT_30,
                coordinatesArray[(NUM_POINTS_3 - 1)
                        * Coordinates.NUM_COORD_LINE
                        + Coordinates.POS_START_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) CoordinateTest.UNIT_40,
                coordinatesArray[(NUM_POINTS_3 - 1)
                        * Coordinates.NUM_COORD_LINE
                        + Coordinates.POS_START_Y],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) 0,
                coordinatesArray[(NUM_POINTS_3 - 1)
                        * Coordinates.NUM_COORD_LINE
                        + Coordinates.POS_END_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) 0,
                coordinatesArray[(NUM_POINTS_3 - 1)
                        * Coordinates.NUM_COORD_LINE
                        + Coordinates.POS_END_Y],
                CARTESIAN_ACCURACY);
    }

    /**
     * Tests toLinesArray, multiple lines, not closing the line.
     */
    @Test
    public final void testToLinesArrayMultiNoClosing() {
        coordinates.addCoordinate(coordinate0);
        coordinates.addCoordinate(coordinate1);
        coordinates.addCoordinate(coordinate2);
        coordinates.setCloseLine(false);
        float[] coordinatesArray = coordinates.toLinesArray();
        Assertions.assertEquals(2 * Coordinates.NUM_COORD_LINE,
                coordinatesArray.length);
        // first line
        Assertions.assertEquals(0f, coordinatesArray[Coordinates.POS_START_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals(0f, coordinatesArray[Coordinates.POS_START_Y],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals(0f, coordinatesArray[Coordinates.POS_END_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) CoordinateTest.UNIT_20,
                coordinatesArray[Coordinates.POS_END_Y],
                CARTESIAN_ACCURACY);

        // second line
        assertCoordinates(coordinates.toLinesArray(), 1);
    }

    /**
     * Test closing the line.
     */
    @Test
    public final void testToLinesArrayClosing() {
        coordinates.addCoordinate(coordinate0);
        coordinates.addCoordinate(coordinate1);
        coordinates.addCoordinate(coordinate2);

        // testing default value (= closing the line)
        // 3 lines expected
        Assertions.assertEquals(NUM_POINTS_3 * Coordinates.NUM_COORD_LINE,
                coordinates.toLinesArray().length);

        // test not closing the line
        coordinates.setCloseLine(false);
        // 2 lines expected
        Assertions.assertEquals(2 * Coordinates.NUM_COORD_LINE,
                coordinates.toLinesArray().length);

        // test closing the line
        coordinates.setCloseLine(true);
        // 3 lines expected
        Assertions.assertEquals(NUM_POINTS_3 * Coordinates.NUM_COORD_LINE,
                coordinates.toLinesArray().length);
    }

    /**
     * Tests setting CoordinateConverter class.
     */
    @Test
    public final void testSetCoordinateConverter() {
        coordinates.setCoordinateConverter(converter);

        coordinates.addCoordinate(coordinate1);
        coordinates.addCoordinate(coordinate2);
        assertConvertedCoordinates(coordinates.toLinesArray());
    }

    /**
     * Tests constructor taking CoordinateConverter as a parameter.
     */
    @Test
    public final void testConstructorCoordinateConverter() {
        coordinates = new Coordinates(converter);

        coordinates.addCoordinate(coordinate1);
        coordinates.addCoordinate(coordinate2);
        assertConvertedCoordinates(coordinates.toLinesArray());
    }

    /**
     * Assert unconverted coordinates.
     *
     * @param coordinatesArray array with Cartesian coordinates
     * @param lineNumber number of the line
     *                   (to determine offset in coordinate array)
     */
    private void assertCoordinates(final float[] coordinatesArray,
                                   final int lineNumber) {
        int offset = Coordinates.NUM_COORD_LINE * lineNumber;
        Assertions.assertEquals(0f,
                coordinatesArray[offset + Coordinates.POS_START_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) CoordinateTest.UNIT_20,
                coordinatesArray[offset + Coordinates.POS_START_Y],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) CoordinateTest.UNIT_30,
                coordinatesArray[offset + Coordinates.POS_END_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) CoordinateTest.UNIT_40,
                coordinatesArray[offset + Coordinates.POS_END_Y],
                CARTESIAN_ACCURACY);
    }

    /**
     * Assert converted coordinates.
     *
     * @param coordinatesArray array with Cartesian coordinates
     */
    private void assertConvertedCoordinates(final float[] coordinatesArray) {
        Assertions.assertEquals(Coordinates.NUM_COORD_LINE, coordinatesArray.length);
        Assertions.assertEquals((float) -1 * CoordinateTest.UNIT_20,
                coordinatesArray[Coordinates.POS_START_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals(0f, coordinatesArray[Coordinates.POS_START_Y],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) -1 * CoordinateTest.UNIT_40,
                coordinatesArray[Coordinates.POS_END_X],
                CARTESIAN_ACCURACY);
        Assertions.assertEquals((float) -1 * CoordinateTest.UNIT_30,
                coordinatesArray[Coordinates.POS_END_Y],
                CARTESIAN_ACCURACY);
    }

    /**
     * Tests null value for new converter in setCoordinateConverter.
     */
    @Test
    public final void testSetCoordinateConverterNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            coordinates.setCoordinateConverter(null);
        });
    }
}
