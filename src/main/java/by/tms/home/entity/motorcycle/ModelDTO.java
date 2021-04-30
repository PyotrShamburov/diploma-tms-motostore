package by.tms.home.entity.motorcycle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    private long brandId;

    @Pattern(regexp = "\\w{1,10}", message = "Letters and digits between 1 and 10!")
    private String nameOfModel;
}
