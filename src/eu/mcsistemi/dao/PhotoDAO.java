package eu.mcsistemi.dao;

import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.Photo;

public interface PhotoDAO {
	public List<Photo> getPhotosByBarcode(Connection conn, String barcode);
	public boolean createPhotos(Connection conn, List<Photo> photos);
	public boolean updatePhotos(Connection conn, List<Photo> photos, String barcode);
}
