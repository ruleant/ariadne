/**
 * Unit tests for Longitude class
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
 * Unit tests for Longitude class.
 *
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
public class LongitudeTest {
    /**
     * Instance of the longitude class.
     */
    private Longitude longitude;

    /**
     * Valid coordinate value : 4°.
     */
    private static final double VALID_COORDINATE = 4.0;

    /**
     * Out of range coordinate value: 190°.
     */
    private static final double OUT_OF_RANGE = 190.0;

    /**
     * Accuracy.
     */
    private static final double ACCURACY = 0.00001;

    /**
     * Exception message when value is out of range.
     */
    private static final String MESSAGE_VALUE_RANGE
            = "newValue is not in range -180.0 .. 180.0";

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public final void setUp() {
        longitude = new Longitude(0.0);
    }

    /**
     * Tests empty value.
     */
    @Test
    public final void testNoValue() {
        Assertions.assertEquals(0.0, longitude.getValue(), ACCURACY);
    }

    /**
     * Tests value.
     */
    @Test
    public final void testValue() {
        longitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(VALID_COORDINATE, longitude.getValue(), ACCURACY);

        longitude.setValue(Longitude.SEGMENT_EAST_HIGH);
        Assertions.assertEquals(
                Longitude.SEGMENT_EAST_HIGH,
                longitude.getValue(),
                ACCURACY);
    }

    /**
     * Tests negative value.
     */
    @Test
    public final void testNegValue() {
        longitude.setValue(-1.0 * VALID_COORDINATE);
        Assertions.assertEquals(-1.0 * VALID_COORDINATE, longitude.getValue(), ACCURACY);

        longitude.setValue(Longitude.SEGMENT_WEST_LOW);
        Assertions.assertEquals(
                Longitude.SEGMENT_WEST_LOW,
                longitude.getValue(),
                ACCURACY);
    }

    /**
     * Tests out of range value, bigger than highest allowed value.
     */
    @Test
    public final void testOutOfRangeValueBigger() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            longitude.setValue(OUT_OF_RANGE);
        });
    }

    /**
     * Tests out of range value, smaller than lowest allowed value.
     */
    @Test
    public final void testOutOfRangeValueSmaller() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            longitude.setValue(-1 * OUT_OF_RANGE);
        });
    }

    /**
     * Tests getSegment.
     */
    @Test
    public final void testGetSegment() {
        Assertions.assertEquals(Longitude.SEGMENT_EAST, longitude.getSegment());

        longitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(Longitude.SEGMENT_EAST, longitude.getSegment());

        longitude.setValue(Longitude.SEGMENT_EAST_HIGH);
        Assertions.assertEquals(Longitude.SEGMENT_EAST, longitude.getSegment());

        longitude.setValue(-1 * VALID_COORDINATE);
        Assertions.assertEquals(Longitude.SEGMENT_WEST, longitude.getSegment());

        longitude.setValue(Longitude.SEGMENT_WEST_LOW);
        Assertions.assertEquals(Longitude.SEGMENT_WEST, longitude.getSegment());
    }

    /**
     * Tests getSegmentUnit.
     */
    @Test
    public final void testGetSegmentUnit() {
        Assertions.assertEquals(Longitude.SEGMENT_EAST_UNIT, longitude.getSegmentUnit());

        longitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(Longitude.SEGMENT_EAST_UNIT, longitude.getSegmentUnit());

        longitude.setValue(Longitude.SEGMENT_EAST_HIGH);
        Assertions.assertEquals(Longitude.SEGMENT_EAST_UNIT, longitude.getSegmentUnit());

        longitude.setValue(-1 * VALID_COORDINATE);
        Assertions.assertEquals(Longitude.SEGMENT_WEST_UNIT, longitude.getSegmentUnit());

        longitude.setValue(Longitude.SEGMENT_WEST_LOW);
        Assertions.assertEquals(Longitude.SEGMENT_WEST_UNIT, longitude.getSegmentUnit());
    }

    /**
     * Tests getConvertedValue.
     */
    @Test
    public final void testGetConvertedValue() {
        Assertions.assertEquals(0.0, longitude.getConvertedValue(), ACCURACY);

        longitude.setValue(VALID_COORDINATE);
        Assertions.assertEquals(
                VALID_COORDINATE,
                longitude.getConvertedValue(),
                ACCURACY);

        longitude.setValue(Longitude.SEGMENT_EAST_HIGH);
        Assertions.assertEquals(
                Longitude.SEGMENT_EAST_HIGH,
                longitude.getConvertedValue(),
                ACCURACY);

        longitude.setValue(-1 * VALID_COORDINATE);
        Assertions.assertEquals(
                VALID_COORDINATE,
                longitude.getConvertedValue(),
                ACCURACY);

        longitude.setValue(Longitude.SEGMENT_WEST_LOW);
        Assertions.assertEquals(
                Math.abs(Longitude.SEGMENT_WEST_LOW),
                longitude.getConvertedValue(),
                ACCURACY);
    }
}
