package by.tms.home.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {

    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$",
            message = "Wrong format! Only characters(3 - 15)!")
    private String name;

    @Pattern(regexp = "^(\\+\\d{12})|(\\d{11})$", message = "Invalid phone number format!")
    private String phoneNumber;

    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String city;
}
