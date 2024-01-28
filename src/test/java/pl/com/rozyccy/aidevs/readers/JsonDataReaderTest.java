package pl.com.rozyccy.aidevs.readers;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataReaderTest {

    @Test
    void readInFile() {
        assertDoesNotThrow(() -> {
            JsonDataReader jsonDataReader = new JsonDataReader();
            jsonDataReader.readInData(new File(".//data//archiwum.json"));
        });
    }
}