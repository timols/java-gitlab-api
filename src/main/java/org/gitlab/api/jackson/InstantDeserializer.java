package org.gitlab.api.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A spezialized {@link Instant} deserializer that can parse different formats.
 */
public class InstantDeserializer extends StdDeserializer<Instant> {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER_WITH_SPACE_SEPARATOR = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE)
        .appendLiteral(' ')
        .append(DateTimeFormatter.ISO_LOCAL_TIME)
        .toFormatter(Locale.getDefault(Locale.Category.FORMAT));

    public InstantDeserializer() {
        super(Instant.class);
    }

    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (JsonToken.VALUE_NULL.equals(parser.getCurrentToken())) {
            return null;
        }

        final String text = parser.getText();

        if (null == text || text.trim().isEmpty()) {
            return null;
        }

        return getFormatters()
            .map(formatter -> {
                try {
                    return formatter.parse(text, Instant::from);
                } catch (DateTimeParseException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new JsonParseException("Unable to parse instant \"" + text + "\"", parser.getCurrentLocation()));
    }

    private static Stream<DateTimeFormatter> getFormatters() {
        return Stream.of(
            // English (Standard) Formats
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.LONG).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.MEDIUM).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withLocale(Locale.ENGLISH),

            // ISO Formats
            LOCAL_DATE_TIME_FORMATTER_WITH_SPACE_SEPARATOR,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_DATE_TIME
        );
    }

}
