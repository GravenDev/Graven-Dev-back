package fr.gravendev.gravendevback.model.authtoken;

import lombok.Getter;

import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Token {

    public static final int DEFAULT_LENGTH = 16;

    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private static final String ALPHANUMERICS = LOWER_CASE + UPPER_CASE + DIGITS;

    private final int length;
    private final Random random;
    @Getter private String token;

    public static Token create() {
        Token token = new Token(DEFAULT_LENGTH);
        token.generate();
        return token;
    }

    public static Token create(int length) {
        Token token = new Token(length);
        token.generate();
        return token;
    }

    public static Token create(int length, Random random) {
        Token token = new Token(length, random);
        token.generate();
        return token;
    }

    protected Token() {
        this(DEFAULT_LENGTH);
    }

    protected Token(int length) {
        this(length, new SecureRandom());
    }

    protected Token(int length, Random random) {
        this.length = length;
        this.random = random;
    }

    protected void generate() {
        this.token = Stream.generate(this::randomChar)
                .limit(length)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    protected char randomChar() {
        return ALPHANUMERICS.charAt(random.nextInt(ALPHANUMERICS.length()));
    }
}
