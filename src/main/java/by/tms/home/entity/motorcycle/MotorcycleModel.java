package by.tms.home.entity.motorcycle;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@ToString(exclude = "brand")
@EqualsAndHashCode(exclude = "brand")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MotorcycleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private long id;
    private String nameOfModel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
