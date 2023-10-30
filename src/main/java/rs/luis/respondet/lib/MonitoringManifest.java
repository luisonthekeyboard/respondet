package rs.luis.respondet.lib;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@Scope("singleton")
public class MonitoringManifest {

    private Map<Integer, Set<String>> callMap;

    public MonitoringManifest() {
        callMap = new HashMap<>();
    }

    public Map<Integer, Set<String>> getCallMap() {
        return callMap;
    }

    public void readFromCSV(String manifestFile) throws IOException {

        String[] HEADERS = { "url", "period"};
        var csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        CSVParser parser = csvFormat.parse(new FileReader(manifestFile));

        for (CSVRecord csvRecord : parser) {
            addToMap(csvRecord);
        }
    }

    private void addToMap(CSVRecord record) {

        int period = Integer.parseInt(record.get("period"));
        String url = record.get("url");

        if (!callMap.containsKey(period)) {
            callMap.put(period, new HashSet<>());
        }
        callMap.get(period).add(url);
    }
}
