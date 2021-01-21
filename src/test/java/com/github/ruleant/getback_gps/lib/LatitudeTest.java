/**
 * Unit tests for Latitude class
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

/**
 * Unit tests for Latitude class.
 *
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
public class LatitudeTest {
    /**
     * Instance of the latitude class.
     */
    private Latitude latitude;

    /**
     * Valid coordinate value : 4°.
     */
    private static final double VALID_COORDINATE = 4.0;

    /**
     * Out of range coordinate value: 100°.
     */
    private static final double OUT_OF_RANGE = 100.0;

    /**
     * Accuracy.
     */
    private static final double ACCURACY = 0.00001;

    /**
     * Exception message when value is out of range.
     */
    private static final String MESSAGE_VALUE_RANGE
            = "newValue is not in range -90.0 .. 90.0";

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public final void setUp() {
        latitude = new Latitude(0.0);
    }

    /**
     * Tests empty value.
     */
    @Test
    public final void testNoValue() {
        Assertions.assertEquals(0.0, latitude.getValue(), ACCURACY);
    }

    /**
     * Tests value.
     */
    @Test
    public final void testValue() {
        latitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(VALID_COORDINATE, latitude.getValue(), ACCURACY);

        latitude.setValue(Latitude.SEGMENT_NORTH_HIGH);
        Assertions.assertEquals(
                Latitude.SEGMENT_NORTH_HIGH,
                latitude.getValue(),
                ACCURACY);
    }

    /**
     * Tests negative value.
     */
    @Test
    public final void testNegValue() {
        latitude.setValue(-1.0 * VALID_COORDINATE);
        Assertions.assertEquals(-1.0 * VALID_COORDINATE, latitude.getValue(), ACCURACY);

        latitude.setValue(Latitude.SEGMENT_SOUTH_LOW);
        Assertions.assertEquals(
                Latitude.SEGMENT_SOUTH_LOW,
                latitude.getValue(),
                ACCURACY);
    }

    /**
     * Tests out of range value, bigger than highest allowed value.
     */
    @Test
    public final void testOutOfRangeValueBigger() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            latitude.setValue(OUT_OF_RANGE);
        });
    }

    /**
     * Tests out of range value, smaller than lowest allowed value.
     */
    @Test
    public final void testOutOfRangeValueSmaller() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            latitude.setValue(-1 * OUT_OF_RANGE);
        });
    }

    /**
     * Tests getSegment.
     */
    @Test
    public final void testGetSegment() {
        Assertions.assertEquals(Latitude.SEGMENT_NORTH, latitude.getSegment());

        latitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(Latitude.SEGMENT_NORTH, latitude.getSegment());

        latitude.setValue(Latitude.SEGMENT_NORTH_HIGH);
        Assertions.assertEquals(Latitude.SEGMENT_NORTH, latitude.getSegment());

        latitude.setValue(-1 * VALID_COORDINATE);
        Assertions.assertEquals(Latitude.SEGMENT_SOUTH, latitude.getSegment());

        latitude.setValue(Latitude.SEGMENT_SOUTH_LOW);
        Assertions.assertEquals(Latitude.SEGMENT_SOUTH, latitude.getSegment());
    }

    /**
     * Tests getSegmentUnit.
     */
    @Test
    public final void testGetSegmentUnit() {
        Assertions.assertEquals(Latitude.SEGMENT_NORTH_UNIT, latitude.getSegmentUnit());

        latitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(Latitude.SEGMENT_NORTH_UNIT, latitude.getSegmentUnit());

        latitude.setValue(Latitude.SEGMENT_NORTH_HIGH);
        Assertions.assertEquals(Latitude.SEGMENT_NORTH_UNIT, latitude.getSegmentUnit());

        latitude.setValue(-1 * VALID_COORDINATE);
        Assertions.assertEquals(Latitude.SEGMENT_SOUTH_UNIT, latitude.getSegmentUnit());

        latitude.setValue(Latitude.SEGMENT_SOUTH_LOW);
        Assertions.assertEquals(Latitude.SEGMENT_SOUTH_UNIT, latitude.getSegmentUnit());
    }

    /**
     * Tests getConvertedValue.
     */
    @Test
    public final void testGetConvertedValue() {
        Assertions.assertEquals(0.0, latitude.getConvertedValue(), ACCURACY);

        latitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(VALID_COORDINATE, latitude.getConvertedValue(), ACCURACY);

        latitude.setValue(Latitude.SEGMENT_NORTH_HIGH);
        Assertions.assertEquals(
                Latitude.SEGMENT_NORTH_HIGH,
                latitude.getConvertedValue(),
                ACCURACY);

        latitude.setValue(-1 * VALID_COORDINATE);
        Assertions.assertEquals(VALID_COORDINATE, latitude.getConvertedValue(), ACCURACY);

        latitude.setValue(Latitude.SEGMENT_SOUTH_LOW);
        Assertions.assertEquals(
                Math.abs(Latitude.SEGMENT_SOUTH_LOW),
                latitude.getConvertedValue(),
                ACCURACY);
    }
}
