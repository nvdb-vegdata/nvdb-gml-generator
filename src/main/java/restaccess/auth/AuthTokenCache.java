// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2015 Statens vegvesen
// ALL RIGHTS RESERVED
package restaccess.auth;

import static java.util.Objects.requireNonNull;

/**
 * Cache for an authentication token with limited life length
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
public class AuthTokenCache {
    private final long maxValidityMs;
    private AuthToken token = AuthToken.empty();
    private long expireTime;

    public AuthTokenCache(long maxValidityMs) {
        this.maxValidityMs = maxValidityMs;
    }

    public synchronized void set(AuthToken newToken) {
        requireNonNull(newToken, "token is null");
        token = newToken.clone();
        expireTime = System.currentTimeMillis() + maxValidityMs;
    }

    public synchronized AuthToken get() {
        if (isValid()) {
            return token.clone();
        } else {
            return AuthToken.empty();
        }
    }

    public synchronized void invalidate() {
        token = AuthToken.empty();
        expireTime = System.currentTimeMillis() - maxValidityMs; // A while ago
    }

    public synchronized boolean isValid() {
        return (!token.isEmpty()) && !isExpired();
    }

    private boolean isExpired()
    {
        return expireTime < System.currentTimeMillis();
    }
}
