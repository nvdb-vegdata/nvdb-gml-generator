// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2015 Statens vegvesen
// ALL RIGHTS RESERVED
package restaccess.auth;

import static java.util.Objects.isNull;

/**
 * The authentication token as delivered by OpenAM
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
public class AuthToken implements Cloneable {
    private final String name;
    private final String value;

    public static AuthToken empty() {
        return new AuthToken(null, null);
    }

    public static AuthToken of(String name, String value) {
        return new AuthToken(name, value);
    }

    private AuthToken(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public boolean isEmpty() {
        return isNull(name) && isNull(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String asCookie() {
        return getName() + "=" + getValue();
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public AuthToken clone() {
        return new AuthToken(this.getName(), this.getValue());
    }
}
