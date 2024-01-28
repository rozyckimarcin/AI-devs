package pl.com.rozyccy.aidevs.readers;

import static io.qdrant.client.PointIdFactory.id;
import static io.qdrant.client.ValueFactory.value;
import static io.qdrant.client.VectorsFactory.vectors;

import io.qdrant.client.grpc.Points.PointStruct;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class Point {
    private final PointStruct pointStruct;

    public Point(UnknownFacts payload, List<Double> vector) {
        this.pointStruct =
                PointStruct.newBuilder()
                        .setId(id(UUID.randomUUID()))
                        .setVectors(vectors(convertDoubleToFloatList(vector)))
                        .putAllPayload(Map.of("unknownFact", value(payload.toString())))
                        .build();
    }

    private List<Float> convertDoubleToFloatList(List<Double> doubleList) {
        List<Float> floatList = new ArrayList<>();
        for (Double d : doubleList) {
            floatList.add(d.floatValue());
        }
        return floatList;
    }
}
