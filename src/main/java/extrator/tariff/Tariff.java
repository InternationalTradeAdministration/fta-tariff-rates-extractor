package extrator.tariff;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tariff {
  private Long id;
  private String tariffLine;
  private String description;
  private String sectorCode;
  private Double baseRate;
  private String baseRateAlt;
  private Integer finalYear;
  private Integer tariffRateQuota;
  private String tariffRateQuotaNote;
  private Boolean tariffEliminated;
  private String partnerName;
  private String reporterName;
  private Integer partnerStartYear;
  private Integer reporterStartYear;
  private String quotaName;
  private String ruleText;
  private String hs6;
  private String hs6Description;
  private String stagingBasket;
  private String productType;
  private List<Link> links;
  private List<Rate> rates;
  private List<RateAlt> rateAlts;

  public Tariff(Long id,
                String tariffLine,
                String description,
                String sectorCode,
                Double baseRate,
                String baseRateAlt,
                Integer finalYear,
                Integer tariffRateQuota,
                String tariffRateQuotaNote,
                Boolean tariffEliminated,
                String partnerName,
                String reporterName,
                Integer partnerStartYear,
                Integer reporterStartYear,
                String quotaName,
                String ruleText,
                String hs6,
                String hs6Description,
                String stagingBasket,
                String productType,
                List<Link> links,
                List<Rate> rates,
                List<RateAlt> rateAlts) {
    this.id = id;
    this.tariffLine = tariffLine;
    this.description = description;
    this.sectorCode = sectorCode;
    this.baseRate = baseRate;
    this.baseRateAlt = baseRateAlt;
    this.finalYear = finalYear;
    this.tariffRateQuota = tariffRateQuota;
    this.tariffRateQuotaNote = tariffRateQuotaNote;
    this.tariffEliminated = tariffEliminated;
    this.partnerName = partnerName;
    this.reporterName = reporterName;
    this.partnerStartYear = partnerStartYear;
    this.reporterStartYear = reporterStartYear;
    this.quotaName = quotaName;
    this.ruleText = ruleText;
    this.hs6 = hs6;
    this.hs6Description = hs6Description;
    this.stagingBasket = stagingBasket;
    this.productType = productType;
    this.links = links;
    this.rates = rates;
    this.rateAlts = rateAlts;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTariffLine() {
    return tariffLine;
  }

  public void setTariffLine(String tariffLine) {
    this.tariffLine = tariffLine;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSectorCode() {
    return sectorCode;
  }

  public void setSectorCode(String sectorCode) {
    this.sectorCode = sectorCode;
  }

  public Double getBaseRate() {
    return baseRate;
  }

  public void setBaseRate(Double baseRate) {
    this.baseRate = baseRate;
  }

  public String getBaseRateAlt() {
    return baseRateAlt;
  }

  public void setBaseRateAlt(String baseRateAlt) {
    this.baseRateAlt = baseRateAlt;
  }

  public Integer getFinalYear() {
    return finalYear;
  }

  public void setFinalYear(Integer finalYear) {
    this.finalYear = finalYear;
  }

  public Integer getTariffRateQuota() {
    return tariffRateQuota;
  }

  public void setTariffRateQuota(Integer tariffRateQuota) {
    this.tariffRateQuota = tariffRateQuota;
  }

  public String getTariffRateQuotaNote() {
    return tariffRateQuotaNote;
  }

  public void setTariffRateQuotaNote(String tariffRateQuotaNote) {
    this.tariffRateQuotaNote = tariffRateQuotaNote;
  }

  public Boolean getTariffEliminated() {
    return tariffEliminated;
  }

  public void setTariffEliminated(Boolean tariffEliminated) {
    this.tariffEliminated = tariffEliminated;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public String getReporterName() {
    return reporterName;
  }

  public void setReporterName(String reporterName) {
    this.reporterName = reporterName;
  }

  public Integer getPartnerStartYear() {
    return partnerStartYear;
  }

  public void setPartnerStartYear(Integer partnerStartYear) {
    this.partnerStartYear = partnerStartYear;
  }

  public Integer getReporterStartYear() {
    return reporterStartYear;
  }

  public void setReporterStartYear(Integer reporterStartYear) {
    this.reporterStartYear = reporterStartYear;
  }

  public String getQuotaName() {
    return quotaName;
  }

  public void setQuotaName(String quotaName) {
    this.quotaName = quotaName;
  }

  public String getRuleText() {
    return ruleText;
  }

  public void setRuleText(String ruleText) {
    this.ruleText = ruleText;
  }

  public String getHs6() {
    return hs6;
  }

  public void setHs6(String hs6) {
    this.hs6 = hs6;
  }

  public String getHs6Description() {
    return hs6Description;
  }

  public void setHs6Description(String hs6Description) {
    this.hs6Description = hs6Description;
  }

  public String getStagingBasket() {
    return stagingBasket;
  }

  public void setStagingBasket(String stagingBasket) {
    this.stagingBasket = stagingBasket;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public List<Rate> getRates() {
    return rates;
  }

  public void setRates(List<Rate> rates) {
    this.rates = rates;
  }

  public List<RateAlt> getRateAlts() {
    return rateAlts;
  }

  public void setRateAlts(List<RateAlt> rateAlts) {
    this.rateAlts = rateAlts;
  }

  @JsonProperty("annualRates")
  public Map<String, Double> getAnnualRates() {
    Map<String, Double> annualRates = new HashMap<>();
    rates.forEach(rate -> annualRates.put("Y" + rate.getYear(), rate.getValue()));
    return annualRates;
  }

  @JsonProperty("annualRateAlts")
  public Map<String, String> getAnnualRateAlts() {
    Map<String, String> annualRateAlts = new HashMap<>();
    rateAlts.forEach(rate -> annualRateAlts.put("Y" + rate.getYear(), rate.getValue()));
    return annualRateAlts;
  }

  @JsonProperty("rulesOfOrigin")
  public Map<String, String> getRulesOfOrigin() {
    Map<String, String> rulesOfOrigin = new HashMap<>();
    for (int i = 1; i < links.size(); i++) {
      rulesOfOrigin.put("link_url_" + i, links.get(i - 1).getLinkUrl());
    }
    return rulesOfOrigin;
  }

}
