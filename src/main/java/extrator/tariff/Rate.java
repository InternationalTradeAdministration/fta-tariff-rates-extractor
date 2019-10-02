package extrator.tariff;

public class Rate {

  private Integer year;
  private Double value;

  public Rate(Integer year, Double value) {
    this.year = year;
    this.value = value;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }
}
