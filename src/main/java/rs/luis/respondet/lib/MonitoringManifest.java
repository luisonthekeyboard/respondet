package rs.luis.respondet.lib;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Scope("singleton")
public class MonitoringManifest {

    private Map<Integer, Set<String>> callMap;

    public MonitoringManifest(@Value("${monitoring_manifest}") String file) {
        callMap = new HashMap<>();
        try {
            readFromCSV(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, Set<String>> getCallMap() {
        return callMap;
    }

    public void readFromCSV(String manifestFile) throws IOException {

        String[] HEADERS = {"url", "period"};
        var csvFormat = CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build();

        try (CSVParser parser = csvFormat.parse(new FileReader(manifestFile))) {

            callMap = parser.stream().collect(
                    Collectors.toMap(
                            (r) -> Integer.parseInt(r.get("period")),
                            (r) -> new TreeSet<>(Set.of(r.get("url"))),
                            (t, u) -> Stream.of(t, u)
                                    .flatMap(Set::stream)
                                    .collect(Collectors.toCollection(TreeSet::new))));
        }
    }
}
