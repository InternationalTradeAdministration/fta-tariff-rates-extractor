package extrator.tariff;

public class RateAlt {

  private Integer year;
  private String value;

  public RateAlt(Integer year, String value) {
    this.year = year;
    this.value = value;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
