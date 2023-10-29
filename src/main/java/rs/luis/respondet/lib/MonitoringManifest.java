package rs.luis.respondet.lib;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class MonitoringManifest {

    private Map<Integer, String> callMap;

    public MonitoringManifest() {
        this.callMap = Map.of(
                11, "oracle.com",
                7, "aws.amazon.com",
                3, "github.com",
                5, "lobste.rs",
                27, "dev.java",
                17, "java.com");

    }

    public Map<Integer, String> getCallMap() {
        return callMap;
    }
}
