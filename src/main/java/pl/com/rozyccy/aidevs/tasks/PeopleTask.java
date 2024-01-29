package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.People;
import pl.com.rozyccy.aidevs.datamodel.PeopleExtended;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.ChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PeopleTask extends Task {

  public PeopleTask(String openAIKey) {
    super(openAIKey);
  }

  public StringAnswer getAnswer(String question)
      throws IOException, InterruptedException, URISyntaxException {
    String system =
        """
                Wyciągnij imię i nazwisko z pytania.

                Przykład:
                pytanie: "Ulubiony kolor Agnieszki Rozkaz, to?"
                odpowiedź: "{"imie":"Agnieszka", "nazwisko": "Rozkaz"}"

                pytanie: "co lubi jeść Tomek Bzik?"
                odpowiedź: "{"imie":"Tomasz", "nazwisko": "Bzik"}\"""";
    logger.info("Content from input page: {}", system);

    HttpResponse response =
        executeRequestToOpenAIAPI(
            "chat/completions",
            new RequestChatCompletion(
                OpenAIModelEnum.GPT_3_5_TURBO.getModelName(),
                List.of(
                    new RequestMessage("system", system), new RequestMessage("user", question))));

    ChatCompletion responseStr =
        new ObjectMapper()
            .readValue(EntityUtils.toString(response.getEntity()), ChatCompletion.class);
    logger.info("Response from OpenAI API: {}", responseStr);

    People people =
        new ObjectMapper()
            .readValue(responseStr.choices().getFirst().message().content(), People.class);
    logger.info("We talking about: {}", people);

    List<PeopleExtended> peopleList = readDataFromFileAndReduceNumber(people);
    logger.info("Returned {} people", peopleList.size());

    HttpResponse response2 =
        executeRequestToOpenAIAPI(
            "chat/completions",
            new RequestChatCompletion(
                OpenAIModelEnum.GPT_3_5_TURBO.getModelName(),
                List.of(
                    new RequestMessage("system", peopleList.toString()),
                    new RequestMessage("user", question))));

    ChatCompletion responseStr2 =
        new ObjectMapper()
            .readValue(EntityUtils.toString(response2.getEntity()), ChatCompletion.class);
    logger.info("Response from OpenAI API: {}", responseStr2);

    return new StringAnswer(responseStr2.choices().getFirst().message().content());
  }

  public List<PeopleExtended> readDataFromFileAndReduceNumber(People people) {
    // Specify the path to the JSON file
    String filePath = ".//data//people.json";

    // Read the content of the file
    String jsonString = readJsonFile(filePath);

    // Parse JSON array
    JSONArray jsonArray = new JSONArray(jsonString);

    // List to store instances of PeopleExtended
    List<PeopleExtended> peopleList = new ArrayList<>();

    // Iterate through each object in the array
    for (int i = 0; i < jsonArray.length(); i++) {
      // Get individual JSON object
      JSONObject jsonObject = jsonArray.getJSONObject(i);

      // Extract data from the JSON object
      String imie = jsonObject.getString("imie");
      String nazwisko = jsonObject.getString("nazwisko");
      int wiek = jsonObject.getInt("wiek");
      String o_mnie = jsonObject.getString("o_mnie");
      String ulubiona_postac_z_kapitana_bomby =
          jsonObject.getString("ulubiona_postac_z_kapitana_bomby");
      String ulubiony_serial = jsonObject.getString("ulubiony_serial");
      String ulubiony_film = jsonObject.getString("ulubiony_film");
      String ulubiony_kolor = jsonObject.getString("ulubiony_kolor");

      // Create an instance of PeopleExtended and add it to the list
      PeopleExtended person =
          new PeopleExtended(
              imie,
              nazwisko,
              wiek,
              o_mnie,
              ulubiona_postac_z_kapitana_bomby,
              ulubiony_serial,
              ulubiony_film,
              ulubiony_kolor);
      peopleList.add(person);
    }

    return peopleList.stream()
        .filter(p -> p.imie().equals(people.imie()))
        .filter(p -> p.nazwisko().equals(people.nazwisko()))
        .collect(Collectors.toList());
  }

  private static String readJsonFile(String filePath) {
    try {
      Path path = Paths.get(filePath);
      return Files.readString(path);
    } catch (IOException e) {
      logger.error("Error while read in from file {}", filePath, e);
    }
    return "";
  }
}
