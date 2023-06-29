package head_hunter;

import lombok.Data;

@Data
public class Salary {
    private int from;
    private int to;
    private String currency;
    private boolean gross;
}
