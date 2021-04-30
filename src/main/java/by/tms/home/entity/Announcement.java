package by.tms.home.entity;

import by.tms.home.entity.motorcycle.Motorcycle;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(exclude = "photos")
@ToString(exclude = "photos")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private long id;
    @OneToOne
    private Motorcycle motorcycle;
    @OneToOne(cascade = CascadeType.ALL)
    private Owner owner;
    @OneToOne(cascade = CascadeType.ALL)
    private Price price;
    private boolean bargain;
    private boolean exchange;
    private String comments;
    private String dateOfPublishing;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "announcement")
    private List<Image> photos;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
