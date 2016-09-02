package org.gitlab.api.models;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests for {@link GitlabGroup}
 */
public class GitlabGroupTest {

    @Test
    public void setLdapAccessHandlesNull() {
        GitlabGroup group = new GitlabGroup();
        group.setLdapAccess(null);
    }

    @Test
    public void getLdapAccessHandlesNull() {
        GitlabGroup group = new GitlabGroup();
        group.setLdapAccess(null);
        assertNull( group.getLdapAccess() );
    }
}
