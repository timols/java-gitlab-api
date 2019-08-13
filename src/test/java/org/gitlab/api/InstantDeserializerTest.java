package org.gitlab.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gitlab.api.jackson.InstantDeserializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class InstantDeserializerTest {

    @Test
    void deserialize() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();

        final InstantDeserializer deserializer = new InstantDeserializer();
        final JsonParser parser = objectMapper.treeAsTokens(objectMapper.readTree("\"2016-08-11T11:28:34.085Z\""));
        parser.nextToken();
        final Instant instant = deserializer.deserialize(parser, objectMapper.getDeserializationContext());

        assertEquals(Instant.from(
            ZonedDateTime.of(
                LocalDate.of(2016, 8, 11),
                LocalTime.of(11, 28, 34, 85),
                ZoneOffset.UTC
            )
        ), instant);
    }

}