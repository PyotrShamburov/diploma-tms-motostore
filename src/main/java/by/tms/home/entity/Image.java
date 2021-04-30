package by.tms.home.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "announcement")
@ToString(exclude = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private long id;
    private String url;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

}
