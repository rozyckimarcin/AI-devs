package pl.com.rozyccy.aidevs.database.qdrant;


import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Points.PointStruct;
import pl.com.rozyccy.aidevs.readers.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QdrantInserter {
    QdrantClient client = new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false).build());

    public void insertData(List<Point> pointsList) throws ExecutionException, InterruptedException {
        List<PointStruct> points = new ArrayList<>();
        for (Point p : pointsList) {
            points.add(p.getPointStruct());
        }

        client.upsertAsync("archiwum", points).get();

    }
}
