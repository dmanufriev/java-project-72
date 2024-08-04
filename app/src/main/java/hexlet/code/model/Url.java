package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp checkedAt;
    private Integer statusCode;

    public Url(String name, Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
