package com.daralmathour.altaefhessan.Logic;


/**
 * Created by amiraelsayed on 3/12/2018.
 */

public class QiblaDirectionCalculator
{

    public static final double QIBLA_LATITUDE = Math.toRadians(21.422487);
    public static final double QIBLA_LONGITUDE = Math.toRadians(39.826206);

    public static double getQiblaDirectionFromNorth(double degLatitude,
                                                    double degLongitude) {
        double latitude2 = Math.toRadians(degLatitude);
        double longitude = Math.toRadians(degLongitude);

        double soorat = Math.sin(QIBLA_LONGITUDE - longitude);
        double makhraj = Math.cos(latitude2) * Math.tan(QIBLA_LATITUDE)
                - Math.sin(latitude2) * Math.cos(QIBLA_LONGITUDE - longitude);
        double returnValue = Math.toDegrees(Math.atan(soorat / makhraj));
        // Math.atan will return value between -90...90 but arc tan of +180
        // degree plus is also the same
        // Never remove thes if..else segments or you will get qibla direction
        // with 180 degree difference
        // Until ***
        if (latitude2 > QIBLA_LATITUDE) {
            if ((longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > 0 && returnValue <= 90)) {

                returnValue += 180;

            } else if (!(longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > -90 && returnValue < 0)) {

                returnValue += 180;

            }

        }
        if (latitude2 < QIBLA_LATITUDE) {

            if ((longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > 0 && returnValue < 90)) {

                returnValue += 180;

            }
            if (!(longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > -90 && returnValue <= 0)) {

                returnValue += 180;
            }

        }
        // ***
        return returnValue - 10;
    }
}
