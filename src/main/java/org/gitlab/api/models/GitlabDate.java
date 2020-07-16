package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * A date, with no time or timezone. This is, given the lack of
 * timezone, an object whose meaning is somewhat ill-defined, but we
 * must use the API that the hexarchs^W^W Gitlab gives us.  We're
 * going to make this immutable, because that's less error-prone.  And
 * we won't provide a constructor from Date, because Date is an
 * instant in time rather than a calendar period.
 */
public final class GitlabDate {
    private int year;
    private int month;
    private int day;

    /**
     * @param month and day are 1-based.
     */
    public GitlabDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }

    public String toString() {
        // Gitlab requires this specific format
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public int hashCode() {
        return this.year * 31 * 12 + this.month * 31 + this.day;
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GitlabDate that = (GitlabDate) o;
        return this.year == that.year && this.month == that.month && this.day == that.day;
    }
}
