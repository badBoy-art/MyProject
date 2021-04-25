package com.spatial4j;

import static org.junit.Assert.assertEquals;
import static org.locationtech.spatial4j.distance.DistanceUtils.DEG_TO_KM;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceCalculator;
import org.locationtech.spatial4j.shape.Point;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-11
 * @Description 计算两个经纬度的距离
 * {link https://github.com/locationtech/spatial4j/blob/master/src/test/java/org/locationtech/spatial4j/distance/TestDistances.java}
 */
public class Spatial4jStudy {

    //NOTE!  These are sometimes modified by tests.
    private SpatialContext ctx;
    private double EPS;

    @Before
    public void beforeTest() {
        ctx = SpatialContext.GEO;
        EPS = 10e-4;//delta when doing double assertions. Geo eps is not that small.
    }

    private DistanceCalculator dc() {
        return ctx.getDistCalc();
    }

    @Test
    public void testSomeDistances() {
        //See to verify: from http://www.movable-type.co.uk/scripts/latlong.html
        Point ctr = pLL(0, 100);
        assertEquals(11100, dc().distance(ctr, pLL(10, 0)) * DEG_TO_KM, 3);
        double deg = dc().distance(ctr, pLL(10, -160));
        assertEquals(11100, deg * DEG_TO_KM, 3);

        assertEquals(314.40338, dc().distance(pLL(1, 2), pLL(3, 4)) * DEG_TO_KM, EPS);
    }

    private Point pLL(double lat, double lon) {
        return ctx.getShapeFactory().pointXY(lat, lon);
    }

}
