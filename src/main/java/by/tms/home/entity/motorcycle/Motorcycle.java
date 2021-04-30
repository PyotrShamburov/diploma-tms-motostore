package by.tms.home.entity.motorcycle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Motorcycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Brand brand;
    @OneToOne
    private MotorcycleModel model;
    @OneToOne
    private MotorcycleType typeOfMotorcycle;
    private int yearOfIssue;
    @OneToOne
    private MotorcycleCondition condition;
    private int cylindersAmount;
    @OneToOne
    private EngineType engineDesignType;
    private int engineVolume;
    @OneToOne
    private DriveType driveType;
    private int mileage;

}
