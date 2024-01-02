package pl.com.rozyccy.aidevs.datamodel;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Parameters {
    private String type;
    private Map<String, Property> properties;
}

