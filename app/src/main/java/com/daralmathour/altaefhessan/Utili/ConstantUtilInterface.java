package com.daralmathour.altaefhessan.Utili;

import android.location.Location;

/**
 * Created by amiraelsayed on 3/12/2018.
 */

public interface  ConstantUtilInterface
{// Tag that is used for Log class
        public static final String NAMAZ_LOG_TAG = "namaz";
        public static final String NAMAZ_QIBLA_LOG_TAG = "namaz qibla Manager";
        // Minimum distance or time to update the location of cell phone with GPS
        public static final long MIN_LOCATION_TIME = 60000;
        public static final long MIN_LOCATION_DISTANCE = 2000;

        // These Constants are here for usage of Qibla Activity

        public static final int MENU_HELP = 35;
        public static final int MENU_PREFS = 34;
        public static final int MENU_ABOUT = 33;

        public static final int ROTATE_IMAGES_MESSAGE = 1;
        public static final String QIBLA_BUNDLE_DELTA_KEY = "qiblaDelta";
        public static final String COMPASS_BUNDLE_DELTA_KEY = "compassDelta";
        public static final String IS_QIBLA_CHANGED = "qibla";
        public static final String IS_COMPASS_CHANGED = "compass";
        // end of QiblaActivity Constants

        // QibaManager's constant
        public static final int MESSAGE_ON_DEVICE_HEAD_DIRECTION = 2;
        public static final int MESSAGE_ON_QIBLA_DIRECTION = 3;
        public static final String MESSAGE_NORTH_DIRECTION_ANGEL_KEY = "northDirection";
        public static final String MESSAGE_DEVICE_LOCATION_KEY = "deviceLocation";
        public static final String QIBLA_CHANGED_MAP_KEY = "qibla";
        public static final String NORTH_CHANGED_MAP_KEY = "north";
        public static final double MIN_DEGREE_FROM_NORTH = 3;
        public static final Location previousLocation = null;
        public static final double MIN_DISTANCE_BETWEEN_LOCATIONS = 10000;

}
