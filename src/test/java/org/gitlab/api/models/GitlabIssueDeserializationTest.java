package org.gitlab.api.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gitlab.api.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GitlabIssueDeserializationTest {
    @Test
    void deserializationTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        Assertions.assertDoesNotThrow(() -> objectMapper.readValue(
                TestUtils.readDataFromResource("IssueExample.json"), GitlabIssue.class));
    }
}
