package com.bwldr.banjo;

import android.net.Uri;

import com.bwldr.banjo.util.ImageFile;

import java.io.File;
import java.io.IOException;

/**
 * Presenter from home screen, (Main), of the app
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private final ImageFile mImageFile;

    public MainPresenter(MainContract.View mainView, ImageFile imageFile) {
        mView = mainView;
        mImageFile = imageFile;
    }

    @Override
    public void takePicture() throws IOException {
        mImageFile.create();
        mView.openCamera();
    }

    @Override
    public Uri getUri() {
        return  mImageFile.getUri();
    }

    @Override
    public File getFile() {
        return mImageFile.getFile();
    }
}
