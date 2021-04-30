package by.tms.home.entity.motorcycle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotorcycleDTO {

    private String nameOfBrand;

    @Pattern(regexp = "[A-Za-z]{2,10}", message = "Only use letters between 2 and 10!")
    private String nameOfModel;
    private long typeId;
    private int yearOfIssue;
    private long conditionId;
    private int cylinderAmount;
    private int engineTypeId;
    private int engineVolume;
    private int driveTypeId;
    @Positive(message = "Can be only positive!")
    private int mileage;
}
