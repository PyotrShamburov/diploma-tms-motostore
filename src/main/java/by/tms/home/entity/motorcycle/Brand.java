package by.tms.home.entity.motorcycle;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(exclude = "motorcycleModel")
@ToString(exclude = "motorcycleModel")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private long id;

    @Pattern(regexp = "[A-Za-z]{2,10}", message = "Only use letters between 2 and 10!")
    private String nameOfBrand;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<MotorcycleModel> motorcycleModel;
}
