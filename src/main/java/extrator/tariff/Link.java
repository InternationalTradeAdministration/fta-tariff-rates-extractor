package extrator.tariff;

public class Link {

  private String linkUrl;
  private String linkText;

  public Link(String linkUrl, String linkText) {
    this.linkUrl = linkUrl;
    this.linkText = linkText;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  public String getLinkText() {
    return linkText;
  }

  public void setLinkText(String linkText) {
    this.linkText = linkText;
  }

}
