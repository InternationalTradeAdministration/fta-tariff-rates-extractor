package extrator.tariff;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TariffDocsMetadata {

  @JsonProperty("PublicationID")
  private String publicationId;

  @JsonProperty("metadata_storage_path")
  private String metadataStoragePath;

  @JsonProperty("Country")
  private String country;

  @JsonProperty("Industry")
  private String industry;

  @JsonProperty("Publication")
  private String publication;

  @JsonProperty("FileName")
  private String fileName;

  @JsonProperty("FTA_Publication_HS_Code")
  private String ftaPublicationHsCode;

  public TariffDocsMetadata() {

  }

  public String getPublicationId() {
    return publicationId;
  }

  public void setPublicationId(String publicationId) {
    this.publicationId = publicationId;
  }

  public String getMetadataStoragePath() {
    return metadataStoragePath;
  }

  public void setMetadataStoragePath(String metadataStoragePath) {
    this.metadataStoragePath = metadataStoragePath;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getPublication() {
    return publication;
  }

  public void setPublication(String publication) {
    this.publication = publication;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFtaPublicationHsCode() {
    return ftaPublicationHsCode;
  }

  public void setFtaPublicationHsCode(String ftaPublicationHsCode) {
    this.ftaPublicationHsCode = ftaPublicationHsCode;
  }
}
