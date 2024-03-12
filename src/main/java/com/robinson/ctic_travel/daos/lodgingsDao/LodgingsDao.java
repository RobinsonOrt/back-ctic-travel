package com.robinson.ctic_travel.daos.lodgingsDao;

import com.robinson.ctic_travel.models.Lodgings;

import java.util.List;

public interface LodgingsDao {
    void createLodging(Lodgings newLodging);
    void updateLodging(Lodgings newLodging);
    void deleteLodging(String lodgingId);
    List<Lodgings> getAllLodgings(int page);
    int getLodgingsCount();
    Lodgings getLodgingByTag(String lodgingTag);
}
