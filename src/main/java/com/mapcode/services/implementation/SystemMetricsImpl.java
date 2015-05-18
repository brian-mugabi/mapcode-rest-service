/*
 * Copyright (C) 2015 Stichting Mapcode Foundation (http://www.mapcode.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mapcode.services.implementation;

import com.mapcode.services.SystemMetrics;
import com.mapcode.services.SystemMetricsCollector;
import com.tomtom.speedtools.metrics.MultiMetricsCollector;
import com.tomtom.speedtools.metrics.MultiMetricsData;
import com.tomtom.speedtools.time.UTCTime;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.EnumMap;


/**
 * This class is purposely not an actor. It interacts with JMX and should therefore have as little messaging delay as
 * possible. It does, however, use the ActorSystem to provide it with a periodic timer event to store its data.
 */
public class SystemMetricsImpl implements SystemMetrics, SystemMetricsCollector {
    private static final Logger LOG = LoggerFactory.getLogger(SystemMetricsImpl.class);

    private final MultiMetricsCollector allMapcodeToLatLonRequests = MultiMetricsCollector.all();
    private final MultiMetricsCollector validMapcodeToLatLonRequests = MultiMetricsCollector.all();
    private final MultiMetricsCollector allLatLonToMapcodeRequests = MultiMetricsCollector.all();
    private final MultiMetricsCollector validLatLonToMapcodeRequests = MultiMetricsCollector.all();
    private final MultiMetricsCollector warningsAndErrors = MultiMetricsCollector.all();

    @Nonnull
    private final EnumMap<Metric, MultiMetricsCollector> all =
            new EnumMap<Metric, MultiMetricsCollector>(Metric.class) {{
                put(Metric.ALL_MAPCODE_TO_LATLON_REQUESTS, allMapcodeToLatLonRequests);
                put(Metric.VALID_MAPCODE_TO_LATLON_REQUESTS, validMapcodeToLatLonRequests);
                put(Metric.ALL_LATLON_TO_MAPCODE_REQUESTS, allLatLonToMapcodeRequests);
                put(Metric.VALID_LATLON_TO_MAPCODE_REQUESTS, validLatLonToMapcodeRequests);
                put(Metric.WARNINGS_AND_ERRORS, warningsAndErrors);
            }};

    @Inject
    public SystemMetricsImpl() {

        // Listen for log errors and warnings.
        org.apache.log4j.Logger.getRootLogger().addAppender(new Log4jAppender());
    }

    /**
     * Appender used to count warnings and errors.
     */
    private class Log4jAppender extends AppenderSkeleton {
        @Override
        protected void append(@Nonnull final LoggingEvent event) {
            if (event.getLevel().equals(Level.WARN) ||
                    event.getLevel().equals(Level.ERROR) ||
                    event.getLevel().equals(Level.FATAL)) {
                // Increase counter first, then log - on the off chance that another errors occurs...
                warningsAndErrors.addValue(1);
                LOG.debug("Log4jAppender::append: {} in last 24 hours", warningsAndErrors.getLastDay().getCount());
            } else {
                // OK. Do NOT use LOG.debug (would be recursive).
            }
        }

        @Override
        public void close() {
        }

        @Override
        public boolean requiresLayout() {
            return false;
        }
    }

    @Nonnull
    @Override
    public MultiMetricsData getMetricData(@Nonnull final Metric metric) {
        assert metric != null;
        return all.get(metric);
    }

    @Override
    public void allMapcodeToLatLonRequests() {
        LOG.debug("allMapcodeToLatLonRequests: {} in last 24 hours", allMapcodeToLatLonRequests.getLastDay().getCount());
        allMapcodeToLatLonRequests.addValueNow(1, UTCTime.now());
    }

    @Override
    public void validMapcodeToLatLonRequests() {
        LOG.debug("validMapcodeToLatLonRequests: {} in last 24 hours", validMapcodeToLatLonRequests.getLastDay().getCount());
        validMapcodeToLatLonRequests.addValueNow(1, UTCTime.now());
    }

    @Override
    public void allLatLonToMapcodeRequests() {
        LOG.debug("allLatLonToMapcodeRequests: {} in last 24 hours", allLatLonToMapcodeRequests.getLastDay().getCount());
        allLatLonToMapcodeRequests.addValueNow(1, UTCTime.now());
    }

    @Override
    public void validLatLonToMapcodeRequests() {
        LOG.debug("validLatLonToMapcodeRequests: {} in last 24 hours", validLatLonToMapcodeRequests.getLastDay().getCount());
        validLatLonToMapcodeRequests.addValueNow(1, UTCTime.now());
    }
}
