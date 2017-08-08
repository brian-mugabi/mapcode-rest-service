/*
 * Copyright (C) 2016-2017, Stichting Mapcode Foundation (http://www.mapcode.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mapcode.services.implementation;

import com.mapcode.services.MapcodeJsonResource;
import com.mapcode.services.MapcodeResource;
import com.tomtom.speedtools.apivalidation.exceptions.ApiIntegerOutOfRangeException;
import com.tomtom.speedtools.apivalidation.exceptions.ApiInvalidFormatException;
import com.tomtom.speedtools.apivalidation.exceptions.ApiNotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 * This class implements the REST API that handles mapcode conversions.
 */
public class MapcodeJsonResourceImpl implements MapcodeJsonResource {

    private final MapcodeResource mapcodeResource;

    @Inject
    public MapcodeJsonResourceImpl(@Nonnull final MapcodeResource mapcodeResource) {
        this.mapcodeResource = mapcodeResource;
    }

    @Override
    public void convertLatLonToMapcodeJson(
            @Suspended @Nonnull final AsyncResponse response) throws ApiInvalidFormatException {
        mapcodeResource.convertLatLonToMapcode(response);
    }

    @Override
    public void convertLatLonToMapcodeJson(
            final double paramLatDeg,
            final double paramLonDeg,
            final int paramPrecision,
            @Nullable final String paramTerritory,
            @Nullable final String paramContextMustBeNull,
            @Nullable final String paramAlphabet,
            @Nonnull final String paramInclude,
            @Nonnull final String paramClient,
            @Nonnull final String paramAllowLog,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiInvalidFormatException {
        mapcodeResource.convertLatLonToMapcode(paramLatDeg, paramLonDeg, paramPrecision, paramTerritory, paramContextMustBeNull,
                paramAlphabet, paramInclude, paramClient, paramAllowLog, response);
    }

    @Override
    public void convertLatLonToMapcodeJson(
            final double paramLatDeg,
            final double paramLonDeg,
            @Nullable final String paramType,
            final int paramPrecision,
            @Nullable final String paramTerritory,
            @Nullable final String paramContextMustBeNull,
            @Nullable final String paramAlphabet,
            @Nonnull final String paramInclude,
            @Nonnull final String paramClient,
            @Nonnull final String paramDebug,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiInvalidFormatException {
        mapcodeResource.convertLatLonToMapcode(paramLatDeg, paramLonDeg, paramType, paramPrecision, paramTerritory, paramContextMustBeNull,
                paramAlphabet, paramInclude, paramClient, paramDebug, response);
    }

    @Override
    public void convertMapcodeToLatLonJson(
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiNotFoundException, ApiInvalidFormatException {
        mapcodeResource.convertMapcodeToLatLon(response);
    }

    @Override
    public void convertMapcodeToLatLonJson(
            @Nonnull final String paramCode,
            @Nullable final String paramContext,
            @Nullable final String paramTerritoryMustBeNull,
            @Nonnull final String paramInclude,
            @Nonnull final String paramClient,
            @Nonnull final String paramDebug,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiNotFoundException, ApiInvalidFormatException {
        mapcodeResource.convertMapcodeToLatLon(paramCode, paramContext, paramTerritoryMustBeNull, paramInclude, paramClient, paramDebug, response);
    }

    @Override
    public void getTerritoriesJson(
            final int offset,
            final int count,
            @Nonnull final String paramClient,
            @Nonnull final String paramAllowLog,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiIntegerOutOfRangeException {
        mapcodeResource.getTerritories(offset, count, paramClient, paramAllowLog, response);
    }

    @Override
    public void getTerritoryJson(
            @Nonnull final String paramTerritory,
            @Nullable final String paramContext,
            @Nonnull final String paramClient,
            @Nonnull final String paramAllowLog,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiInvalidFormatException {
        mapcodeResource.getTerritory(paramTerritory, paramContext, paramClient, paramAllowLog, response);
    }

    @Override
    public void getAlphabetsJson(
            final int offset,
            final int count,
            @Nonnull final String paramClient,
            @Nonnull final String paramAllowLog,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiIntegerOutOfRangeException {
        mapcodeResource.getAlphabets(offset, count, paramClient, paramAllowLog, response);
    }

    @Override
    public void getAlphabetJson(
            @Nonnull final String paramAlphabet,
            @Nonnull final String paramClient,
            @Nonnull final String paramAllowLog,
            @Suspended @Nonnull final AsyncResponse response)
            throws ApiInvalidFormatException {
        mapcodeResource.getAlphabet(paramAlphabet, paramClient, paramAllowLog, response);
    }
}
