package report;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
import org.bson.Document;

import java.io.IOException;

public class Report {
    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        final MongoCollection<Document> collection = mongoClient.getDatabase("test")
                .getCollection("chatMessageModel");

        int count = 0;
        try(MongoCursor<Document> cursor = collection.find().iterator()) {
            System.out.println("CHAT REPORT");
            System.out.println("----------------");
            while (cursor.hasNext()) {
                final Document document = cursor.next();
                System.out.println(document.get("createDate") + " " + document.get("author") + " " + document.get("text"));
                count += 1;
            }
            System.out.println("----------------");
            System.out.println("Total messages: " + count);
        }
        CollectorRegistry registry = new CollectorRegistry();
        Gauge g = Gauge.build()
                .name("total_messages_count")
                .help("Total message count in DB")
                .register(registry);
        g.set(count);
        PushGateway pushGateway = new PushGateway("localhost:9091");
        pushGateway.push(g, "JavaBatch");
    }
}
