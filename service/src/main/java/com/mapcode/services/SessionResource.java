/**
 * Copyright (C) 2017, TomTom International BV (http://www.tomtom.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapcode.services;


import com.mapcode.services.dto.AppLoginDTO;
import com.mapcode.services.dto.LoginDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * This resource offers method to manage user and application login sessions. A session is defined by a valid login or
 * or authentication procedure up to a logout or expiry of the sessions.
 * 
 * Sessions can be referenced by a session ID.
 * 
 * Requests requiring input parameters that miss or have invalid parameters will get a HTTP 400 Bad Request response.
 * 
 * Requests requiring specific authorization levels that are not met will get a HTTP 401 Unauthorized response.
 * 
 * Requests might in rare exception cases be answered with a HTTP 500 Internal Server Error.
 */
@Path("/sessions")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface SessionResource {

    String PARAM_SESSION_ID = "sessionId";

    /**
     * This method starts a user's session.
     *
     * Returns HTTP status code 201 when session is created.
     *
     * Returns HTTP status code 401 when login failed.
     * 
     * Returns HTTP status code 201 when login succeeds, session is created.
     * 
     * Returns HTTP status code 401 when login failed.
     *
     * @param loginDTO Login data passed by the client.
     * @param response {@link AsyncResponse} of type {@link com.mapcode.services.dto.SessionCreatedDTO}.
     */
    @POST
    @Path("username")
    void loginUserName(
            @Nullable LoginDTO loginDTO,
            @Suspended @Nonnull AsyncResponse response);

    /**
     * This method starts a user's session.
     * 
     * Returns HTTP status code 201 when login succeeds, session is created.
     * 
     * Returns HTTP status code 401 when login failed.
     *
     * @param appLoginDTO Login data passed by the client.
     * @param response    {@link AsyncResponse} of type {@link com.mapcode.services.dto.SessionCreatedDTO}.
     */
    @POST
    @Path("token")
    void loginAppToken(
            @Nullable AppLoginDTO appLoginDTO,
            @Suspended @Nonnull AsyncResponse response);

    /**
     * This method terminates a user's session.
     * 
     * Returns HTTP status code 204 when session is terminated, even if session to be terminated did not exist.
     *
     * @param sessionId       Session id that is requested to be terminated.
     * @param response        {@link AsyncResponse} which is empty.
     * @param securityContext The {@link SecurityContext} contains information about the authenticated {@link
     *                        java.security.Principal} making the call.
     */
    @DELETE
    @Path('{' + PARAM_SESSION_ID + '}')
    void logout(
            @Nonnull @PathParam(PARAM_SESSION_ID) String sessionId,
            @Suspended @Nonnull AsyncResponse response,
            @Nonnull @Context SecurityContext securityContext);
}

