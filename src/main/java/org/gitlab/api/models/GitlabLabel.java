package org.gitlab.api.models;

/**
 * Models a Gitlab label.
 */
public class GitlabLabel {

    public static final String URL = "/labels";

    private String name;
    private String color;

    /**
     * Gets the name (text) of a label.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name (text) of a label.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the color of a label as six digit HTML hex value.
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of a label.
     * @param color A six digit HTML hex value including number sign (eg #ff0000)
     */
    public void setColor(String color) {
        this.color = color;
    }

}
