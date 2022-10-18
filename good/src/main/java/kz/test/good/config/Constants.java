package kz.test.good.config;

import org.springframework.data.jpa.repository.query.EscapeCharacter;

public class Constants {
    public static final String API_REST_ENDPOINT = "/api";
    public static final String OPEN_API_REST_ENDPOINT = "/open-api";
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer";
    public static final String CONTAINING_LIKE = "%%%s%%";
    public static final char ESCAPE_CHAR = EscapeCharacter.DEFAULT.getEscapeCharacter();
}
