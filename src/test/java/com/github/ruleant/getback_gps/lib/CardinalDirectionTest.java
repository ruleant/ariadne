/**
 * Unit tests for CardinalDirection class
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
 * Unit tests for CardinalDirection class.
 *
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
public class CardinalDirectionTest {
    /**
     * Expected Exception.
     */

    /**
     * Instance of the CardinalDirection class.
     */
    private CardinalDirection object;

    /**
     * Valid coordinate value : 4°.
     */
    private static final double VALID_COORDINATE = 4.0;

    /**
     * Out of range coordinate value: 100°.
     */
    private static final double OUT_OF_RANGE = 400.0;

    /**
     * Accuracy.
     */
    private static final double ACCURACY = 0.001;

    /**
     * Exception message when value is out of range.
     */
    private static final String MESSAGE_VALUE_RANGE
            = "newValue is not in range 0.0 .. 360.0";

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public final void setUp() {
        object = new CardinalDirection(0.0);
    }

    /**
     * Tests empty value.
     */
    @Test
    public final void testNoValue() {
        Assertions.assertEquals(0.0, object.getValue(), ACCURACY);
    }

    /**
     * Tests value.
     */
    @Test
    public final void testValue() {
        object.setValue(VALID_COORDINATE);
        Assertions.assertEquals(VALID_COORDINATE, object.getValue(), ACCURACY);

        object.setValue(CardinalDirection.SEGMENT_NW_HIGH);
        Assertions.assertEquals(
                CardinalDirection.SEGMENT_NW_HIGH, object.getValue(),
                ACCURACY);
    }

    /**
     * Tests out of range value, bigger than highest allowed value.
     */
    @Test
    public final void testOutOfRangeValueBigger() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            object.setValue(OUT_OF_RANGE);
        });
    }

    /**
     * Tests out of range value, smaller than lowest allowed value.
     */
    @Test
    public final void testOutOfRangeValueSmaller() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            object.setValue(-1 * OUT_OF_RANGE);
        });
    }

    /**
     * Tests getSegment.
     */
    @Test
    public final void testGetSegment() {
        Assertions.assertEquals(CardinalDirection.SEGMENT_NORTHEAST, object.getSegment());

        object.setValue(VALID_COORDINATE);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NORTHEAST, object.getSegment());

        object.setValue(CardinalDirection.SEGMENT_NE_HIGH);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NORTHEAST, object.getSegment());

        object.setValue(VALID_COORDINATE + CardinalDirection.SEGMENT_SE_LOW);
        Assertions.assertEquals(CardinalDirection.SEGMENT_SOUTHEAST, object.getSegment());

        object.setValue(CardinalDirection.SEGMENT_SE_HIGH);
        Assertions.assertEquals(CardinalDirection.SEGMENT_SOUTHEAST, object.getSegment());

        object.setValue(VALID_COORDINATE + CardinalDirection.SEGMENT_SW_LOW);
        Assertions.assertEquals(CardinalDirection.SEGMENT_SOUTHWEST, object.getSegment());

        object.setValue(CardinalDirection.SEGMENT_SW_HIGH);
        Assertions.assertEquals(CardinalDirection.SEGMENT_SOUTHWEST, object.getSegment());

        object.setValue(VALID_COORDINATE + CardinalDirection.SEGMENT_NW_LOW);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NORTHWEST, object.getSegment());

        object.setValue(CardinalDirection.SEGMENT_NW_HIGH);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NORTHWEST, object.getSegment());
    }

    /**
     * Tests getSegmentUnit.
     */
    @Test
    public final void testGetSegmentUnit() {
        Assertions.assertEquals(CardinalDirection.SEGMENT_NE_UNIT,
                object.getSegmentUnit());

        object.setValue(VALID_COORDINATE);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NE_UNIT,
                object.getSegmentUnit());

        object.setValue(VALID_COORDINATE + CardinalDirection.SEGMENT_SE_LOW);
        Assertions.assertEquals(CardinalDirection.SEGMENT_SE_UNIT,
                object.getSegmentUnit());

        object.setValue(VALID_COORDINATE + CardinalDirection.SEGMENT_SW_LOW);
        Assertions.assertEquals(CardinalDirection.SEGMENT_SW_UNIT,
                object.getSegmentUnit());

        object.setValue(VALID_COORDINATE + CardinalDirection.SEGMENT_NW_LOW);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NW_UNIT,
                object.getSegmentUnit());
    }

    /**
     * Tests getConvertedValue.
     */
    @Test
    public final void testGetConvertedValue() {
        Assertions.assertEquals(0.0, object.getConvertedValue(), ACCURACY);

        object.setValue(VALID_COORDINATE);
        Assertions.assertEquals(VALID_COORDINATE, object.getConvertedValue(), ACCURACY);

        object.setValue(CardinalDirection.SEGMENT_NW_HIGH);
        Assertions.assertEquals(CardinalDirection.SEGMENT_NW_HIGH,
                object.getConvertedValue(),
                ACCURACY);
    }
}
