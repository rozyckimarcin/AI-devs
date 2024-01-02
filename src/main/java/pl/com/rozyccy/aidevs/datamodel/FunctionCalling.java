package pl.com.rozyccy.aidevs.datamodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FunctionCalling {
    private String name;
    private String description;
    private Parameters parameters;
}
