package pl.com.rozyccy.aidevs.database.qdrant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static io.qdrant.client.WithPayloadSelectorFactory.enable;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.WithVectorsSelectorFactory;
import io.qdrant.client.grpc.Points;
import io.qdrant.client.grpc.Points.SearchPoints;

public class QdrantSearcher {
    QdrantClient client =
            new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false).build());

    public List<Points.ScoredPoint> search(List<Double> vector) throws ExecutionException, InterruptedException {
        return client.searchAsync(
                SearchPoints.newBuilder()
                        .setCollectionName("archiwum")
                        .addAllVector(convertDoubleToFloatList(vector))
                        .setWithPayload(enable(true))
                        .setWithVectors(WithVectorsSelectorFactory.enable(true))
                        .setLimit(3)
                        .build())
                .get();
    }

    private List<Float> convertDoubleToFloatList(List<Double> doubleList) {
        List<Float> floatList = new ArrayList<>();
        for (Double d : doubleList) {
            floatList.add(d.floatValue());
        }
        return floatList;
    }
}
