package extrator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import extrator.tariff.*;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class Function {

  private TariffCsvTranslator tariffCsvTranslator = new TariffCsvTranslator();
  private ObjectMapper objectMapper = new ObjectMapper();

  @FunctionName("HttpTrigger-Java")
  public HttpResponseMessage run(
    @HttpTrigger(
      name = "req",
      methods = {HttpMethod.GET},
      authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request, final ExecutionContext context) {
    context.getLogger().info("Java HTTP trigger processed a request.");

    String tariffRatesCsvUrl = request.getQueryParameters().get("tariffRatesCsvUrl");
    if (tariffRatesCsvUrl == null)
      return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
        .body("Please pass a tariffRatesCsvUrl on the query string").build();

    HttpURLConnection con;
    try {
      context.getLogger().info("Retrieving tariff rates CSV file.");
      con = (HttpURLConnection) new URL(tariffRatesCsvUrl).openConnection();
    } catch (IOException e) {
      e.printStackTrace();
      return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
        .body("Error: Could not retrieve tariff rates csv. " + e.getMessage()).build();
    }

    try {
      context.getLogger().info("Processing tariff rates CSV file.");
      InputStream inputStream = con.getInputStream();
      Reader reader = new InputStreamReader(inputStream);
      List<Tariff> tariffs = tariffCsvTranslator.translate(reader);
      reader.close();
      // TODO: Uncomment when tariff docs are available / finalized by the publishing team
      // List<TariffDocsMetadata> tariffDocsMetadata = getTariffDocsMetadata();
      // applyRulesOfOrigin(tariffs, tariffDocsMetadata);
      String tariffRatesJson = objectMapper.writeValueAsString(tariffs);
      return request.createResponseBuilder(HttpStatus.OK)
        .header("Content-Type", "application/json")
        .body(tariffRatesJson).build();
    } catch (IOException | InvalidCsvFileException e) {
      e.printStackTrace();
      return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
        .body("Error: Could not process tariff rates csv file. " + e.getMessage()).build();
    }
  }

  private void applyRulesOfOrigin(List<Tariff> tariffs, List<TariffDocsMetadata> tariffDocsMetadata) {
    //TODO: append document link urls using hs6 and country variables
    tariffDocsMetadata.forEach(metadata -> {
      System.out.println(metadata.getMetadataStoragePath());
    });
  }

  private List<TariffDocsMetadata> getTariffDocsMetadata() throws IOException {
    String tariffDocsAccessTokenUrl = System.getenv("TARIFF_DOCS_ACCESS_TOKEN_URL");
    String clientId = System.getenv("TARIFF_DOCS_CLIENT_ID");
    String clientSecret = System.getenv("TARIFF_DOCS_CLIENT_SECRET");
    String tariffDocsMetadataUrl = System.getenv("TARIFF_DOCS_METADATA_URL");

    //Get Access Token
    OkHttpClient client = new OkHttpClient();
    String bodyContent =
      String.format("grant_type=client_credentials&client_secret=%s&client_id=%s&Resource=%s", clientSecret, clientId, clientId);
    RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), bodyContent);
    Request accessTokenRequest = new Request.Builder()
      .url(tariffDocsAccessTokenUrl)
      .post(body)
      .addHeader("grant_type", "client_credentials")
      .addHeader("Content-Type", "application/x-www-form-urlencoded")
      .addHeader("cache-control", "no-cache").build();

    Response accessTokenResponse = client.newCall(accessTokenRequest).execute();
    AccessTokenResponse accessTokenResponseEntity = objectMapper.readValue(accessTokenResponse.body().bytes(), AccessTokenResponse.class);

    //Get Tariff Docs Metadata
    RequestBody tariffDocsMetadataRequestBody =
      RequestBody.create(MediaType.parse("application/json"), "{\"query\": \"$filter=Publication eq 'FTA Publication'\"}");
    Request tariffDocsMetadataRequest = new Request.Builder()
      .url(tariffDocsMetadataUrl)
      .post(tariffDocsMetadataRequestBody)
      .addHeader("Content-Type", "application/json")
      .addHeader("Authorization", "Bearer " + accessTokenResponseEntity.getAccessToken())
      .addHeader("cache-control", "no-cache")
      .addHeader("Postman-Token", "db9451e9-6695-4f9e-90f9-ebd2f7fbeec8")
      .build();

    Response tariffDocsMetadataResponse = client.newCall(tariffDocsMetadataRequest).execute();
    return objectMapper.readValue(tariffDocsMetadataResponse.body().bytes(), new TypeReference<List<TariffDocsMetadata>>() {
    });
  }

}
