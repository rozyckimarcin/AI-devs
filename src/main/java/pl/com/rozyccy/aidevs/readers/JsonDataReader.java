package pl.com.rozyccy.aidevs.readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataReader {
    private static final Logger logger = LogManager.getLogger(JsonDataReader.class);

    public List<UnknownFacts> readInData(File jsonFile) {
        List<UnknownFacts> data = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON array from file
            data = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, UnknownFacts.class));

            // Process the data
            for (UnknownFacts entry : data) {
                logger.trace("Title: {}\nURL: {}\nInfo: {}\nDate: {}\n", entry.title(), entry.url(), entry.info(), entry.date());
            }

        } catch (IOException e) {
            logger.error("Error while read in file {}\nError message: {}", jsonFile.getAbsoluteFile(), e.getMessage(), e);
        }
        return data;
    }
}
