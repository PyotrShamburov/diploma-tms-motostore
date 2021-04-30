package by.tms.home.service;

import by.tms.home.entity.Announcement;
import by.tms.home.entity.AnnouncementSearchDTO;
import by.tms.home.entity.Image;
import by.tms.home.entity.OwnerDTO;
import by.tms.home.entity.PriceDTO;

import java.util.List;

public interface AnnouncementService {
    Announcement addAdToDatabase(Announcement announcement);
    Announcement getAdById(long id);
    void deleteAdById(long id);
    void setDateOfPublishingToAd(Announcement ad);
    List<Announcement> getAllAnnouncements();
    List<Announcement> findAdBySearchCriteria(AnnouncementSearchDTO finallyReadySearchDTO);
    AnnouncementSearchDTO createReadyMadeSearchDTO(AnnouncementSearchDTO nonCheckedSearchDTO);
    List<Announcement> getRandomAdToHomePage(long amount);
    boolean isAnnouncementHasNoPhotos(long adId);
    void addDefaultPhotoToAd(long adId, Image image);
    void photoCheckAndFinishOrSetDefaultPhoto(long adId);
    void updateAdComments(long adId, String newComments);
    void updateMileage(long adId, int newMileage);
    void updateCondition(long adId, long conditionId);
    void updateOwnerInfo(long adId, OwnerDTO ownerDTO);
    void updatePriceOfAd(long adId, PriceDTO priceDTO);
}
