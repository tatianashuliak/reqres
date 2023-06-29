package head_hunter;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Vacancy {
    private String name;
    private Salary salary;
    @SerializedName("alternate_url")
    private String alternateUrl;
}
