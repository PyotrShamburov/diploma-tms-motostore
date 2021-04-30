package by.tms.home.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor()
public class AnnouncementSearchDTO {
    private long brandStartId;
    private long brandEndId;
    private long typeStartId;
    private long typeEndId;
    private long conditionStartId;
    private long conditionEndId;
    private long engineTypeStartId;
    private long engineTypeEndId;
    private long driveTypeStartId;
    private long driveTypeEndId;
    private int lowestYearOfIssue;
    private int highestYearOfIssue;
    private int lowestEngineVolume;
    private int highestEngineVolume;
    private int lowestPrice;
    private int highestPrice;
}
