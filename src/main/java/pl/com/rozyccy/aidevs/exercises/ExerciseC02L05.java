package pl.com.rozyccy.aidevs.exercises;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExerciseC02L05 extends Exercise {
    @Override
    public int executeTask(String... parameters) throws IOException {
        AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
        AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("functions", parameters[0]);
        AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(token.token());
        logger.info("Your task is: {}", taskApiResponse.msg());

        Map<String, Property> mapOfProperties = new HashMap<>();
        mapOfProperties.put("name", Property.builder().type("string").build());
        mapOfProperties.put("surname", Property.builder().type("string").build());
        mapOfProperties.put("year", Property.builder().type("integer").build());
        Parameters answerParameters = Parameters.builder().type("object").properties(mapOfProperties).build();
        FunctionCalling functionCalling = FunctionCalling.builder().name("addUser").description("Function to add user").parameters(answerParameters).build();
        FunctionCallingAnswer answer = new FunctionCallingAnswer(functionCalling);

        logger.info("Input task answer is: " + answer);

        int responseCode = AIDevsApiExecutor.postAnswer(token.token(), answer);
        checkResponseCode(responseCode);
        return responseCode;
    }
}
