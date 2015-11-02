// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2015 Statens vegvesen
// ALL RIGHTS RESERVED
package restaccess.auth;

/**
 * Provides a valid authentication token for accessing NVDB REST-API
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
public class AuthTokenProvider {
    private final String loginServerUrl;
    private final String username;
    private final String password;
    private final AuthTokenCache cache;

    public AuthTokenProvider(String loginServerUrl, String username, String password) {
        this.loginServerUrl = loginServerUrl;
        this.username = username;
        this.password = password;
        this.cache = new AuthTokenCache(30*60*1000); // 30 min
    }

    public synchronized AuthToken getToken() {
        AuthToken token = cache.get();
        if (token.isEmpty()) {
            acquireToken();
            token = cache.get();
        }
        return token;
    }

    public synchronized void renewToken() {
        acquireToken();
    }

    private void acquireToken() {
        Authentication.AuthenticationResult result = Authentication.passwordCheck(username, password, loginServerUrl);
        if (!result.isSuccess()) {
            throw new RuntimeException("Failed to create authentication token for user " + username);
        }

        AuthToken token = AuthToken.of(result.getCookieName(), result.getCookieValue());
        cache.set(token);
    }
}
