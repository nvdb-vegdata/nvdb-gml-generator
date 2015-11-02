// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2015 Statens vegvesen
// ALL RIGHTS RESERVED
package restaccess.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * A Jersey client filter that:
 *
 * 1) Decorates request with valid authentication token.
 * 2) If remote service invocation responds with 302 FOUND, acquires a new token
 *    and resends the request. NB! Only works if FOLLOW_REDIRECTS is disabled in Client.
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
@Provider
public class NvdbApiAuthFilter implements ClientRequestFilter, ClientResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NvdbApiAuthFilter.class);

    private final AuthTokenProvider tokenProvider;

    public NvdbApiAuthFilter(AuthTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        AuthToken token = tokenProvider.getToken();
        LOGGER.debug("Adding token as cookie: {}", token);
        requestContext.getHeaders().add(HttpHeaders.COOKIE, token.asCookie());
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        System.out.println("OPSI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! to much deletion");
    }

    private Response retryRequest(ClientRequestContext requestContext) {
        Client client = ClientBuilder.newClient(requestContext.getConfiguration());
        String method = requestContext.getMethod();
        MediaType mediaType = requestContext.getMediaType();
        URI lUri = requestContext.getUri();

        WebTarget resourceTarget = client.target(lUri);

        Invocation.Builder builder = resourceTarget.request(mediaType);
        MultivaluedMap<String, Object> requestHeaders = requestContext.getHeaders();
        requestHeaders.remove(HttpHeaders.COOKIE);
        builder.headers(requestHeaders);
        Invocation invocation = builder.build(method);

        return invocation.invoke();
    }
}
