package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Olga Maciaszek-Sharma
 */
public class GitlabBadge {

	public static final String URL = "/badges";

	private Integer id;
	@JsonProperty("link_url")
	private String linkUrl;
	@JsonProperty("image_url")
	private String imageUrl;
	@JsonProperty("rendered_link_url")
	private String renderedLinkUrl;
	@JsonProperty("rendered_image_url")
	private String renderedImageUrl;
	private String kind;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRenderedLinkUrl() {
		return renderedLinkUrl;
	}

	public void setRenderedLinkUrl(String renderedLinkUrl) {
		this.renderedLinkUrl = renderedLinkUrl;
	}

	public String getRenderedImageUrl() {
		return renderedImageUrl;
	}

	public void setRenderedImageUrl(String renderedImageUrl) {
		this.renderedImageUrl = renderedImageUrl;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
