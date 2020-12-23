package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.RePhotoDao;
import com.openclassrooms.realestatemanager.model.RePhoto;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class RePhotoRepository {

    private RePhotoDao mRePhotoDao;

    public RePhotoRepository(RePhotoDao pRePhotoDao) {mRePhotoDao = pRePhotoDao;}

    public LiveData<List<RePhoto>> selectRePhoto(long pReId) { return mRePhotoDao.selectRePhoto(pReId);}

    public void insertRePhoto(RePhoto pRePhoto) { mRePhotoDao.insertRePhoto(pRePhoto);}

    public void updateRePhoto(RePhoto pRePhoto) { mRePhotoDao.updateRePhoto(pRePhoto);}

    public void deleteRePhoto(RePhoto pRePhoto) { mRePhotoDao.deleteRePhoto(pRePhoto);}

}
