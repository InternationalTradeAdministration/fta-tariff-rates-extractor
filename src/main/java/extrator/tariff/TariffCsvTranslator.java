package extrator.tariff;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TariffCsvTranslator {

  public List<Tariff> translate(Reader reader) throws InvalidCsvFileException {
    CSVParser csvParser;
    List<Tariff> tariffs = new ArrayList<>();
    int i = 1;

    try {
      csvParser = new CSVParser(
        reader,
        CSVFormat.DEFAULT
          .withFirstRecordAsHeader()
          .withTrim()
          .withNullString("")
          .withIgnoreHeaderCase()
      );

      Map<String, Integer> headers = csvParser.getHeaderMap();

      for (CSVRecord csvRecord : csvParser) {
        Tariff tf = new Tariff(
          Long.parseLong(csvRecord.get("ID")),
          csvRecord.get("TL"),
          csvRecord.get("TL_Desc"),
          csvRecord.get("Sector_Code"),
          doubleParser(csvRecord.get("Base_Rate")),
          csvRecord.get("Base_Rate_Alt"),
          intParser(csvRecord.get("Final_Year")),
          intParser(csvRecord.get("TRQ_Quota")),
          csvRecord.get("TRQ_Note"),
          Boolean.parseBoolean(csvRecord.get("Tariff_Eliminated")),
          csvRecord.get("PartnerName"),
          csvRecord.get("ReporterName"),
          intParser(csvRecord.get("PartnerStartYear")),
          intParser(csvRecord.get("ReporterStartYear")),
          csvRecord.get("QuotaName"),
          csvRecord.get("Rule_Text"),
          csvRecord.get("HS6"),
          csvRecord.get("HS6_Desc"),
          csvRecord.get("StagingBasket"),
          csvRecord.get("ProductType"),
          new ArrayList<>(),
          new ArrayList<>(),
          new ArrayList<>()
        );

        List<Rate> rates = new ArrayList<>();
        headers.forEach((header, position) -> {
          if (header.substring(0, 1).equalsIgnoreCase("y") && !header.toLowerCase().contains("alt")) {
            Integer year = Integer.parseInt(removeNonNumericCharacters(header));
            Double value = doubleParser(csvRecord.get(header));
            if (value != null && value != 0) rates.add(new Rate(year, value));
          }
        });
        tf.setRates(rates);

        List<RateAlt> rateAlts = new ArrayList<>();
        headers.forEach((header, position) -> {
          if (header.substring(0, 1).equalsIgnoreCase("y") && header.toLowerCase().contains("alt")) {
            Integer year = Integer.parseInt(removeNonNumericCharacters(header));
            String value = csvRecord.get(header);
            if (value != null) rateAlts.add(new RateAlt(year, value));
          }
        });
        tf.setRateAlts(rateAlts);

        Map<Integer, Link> linkMap = new HashMap<>();
        headers.forEach((header, position) -> {
          if (header.toLowerCase().contains("link")) {
            int linkIndex = 0;
            String linkPosition = removeNonNumericCharacters(header);
            if (!linkPosition.isEmpty()) linkIndex = Integer.parseInt(linkPosition);

            Link link = linkMap.get(linkIndex);
            if (link != null) {
              if (header.toLowerCase().contains("text")) {
                link.setLinkText(csvRecord.get(header));
              } else {
                link.setLinkUrl(csvRecord.get(header));
              }
            } else {
              if (header.toLowerCase().contains("text")) {
                linkMap.put(linkIndex, new Link(null, csvRecord.get(header)));
              } else {
                linkMap.put(linkIndex, new Link(csvRecord.get(header), null));
              }
            }
          }
        });

        List<Link> links = linkMap.values().stream()
          .filter(link -> link.getLinkText() != null || link.getLinkUrl() != null).collect(Collectors.toList());
        tf.setLinks(links);

        tariffs.add(tf);
        i++;
      }

      reader.close();

    } catch (IllegalArgumentException e) {
      e.printStackTrace();

      if (e instanceof NumberFormatException) {
        throw new InvalidCsvFileException("Invalid number format; see record " + i);
      } else {
        throw new InvalidCsvFileException(e.getMessage() + "; see record " + i);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


    return tariffs;
  }

  private String removeNonNumericCharacters(String string) {
    return string.replaceAll("[^\\d]", "");
  }

  private Integer intParser(String potentialInteger) {
    if (potentialInteger == null) return null;
    try {
      return Integer.parseInt(potentialInteger);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private Double doubleParser(String potentialDouble) {
    if (potentialDouble == null) return null;
    return Double.parseDouble(potentialDouble);
  }

}
