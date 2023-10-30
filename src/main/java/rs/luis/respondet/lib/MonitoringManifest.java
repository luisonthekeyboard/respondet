package rs.luis.respondet.lib;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class MonitoringManifest {

    private final Map<Integer, String> callMap;

    public MonitoringManifest() {
        this.callMap = Map.of(
                11, "https://oracle.com",
                7, "https://aws.amazon.com",
                3, "https://github.com",
                33, "https://lobste.rs",
                27, "https://dev.java",
                17, "https://java.com");
    }

    public Map<Integer, String> getCallMap() {
        return callMap;
    }
}
