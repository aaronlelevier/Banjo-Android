package com.bwldr.banjo;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;

import com.bwldr.banjo.util.Constants;
import com.bwldr.banjo.util.ImageFile;

import java.io.IOException;

/**
 * Presenter from home screen, (Main), of the app
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;
    private final ImageFile mImageFile;

    public MainPresenter(MainContract.View mainView, ImageFile imageFile) {
        mMainView = mainView;
        mImageFile = imageFile;
    }

    @Override
    public void takePicture(FragmentActivity activity) throws IOException {
        mImageFile.create(activity);

        Uri photoUri = FileProvider.getUriForFile(
                activity,
                Constants.FILE_PROVIDER_PATH,
                mImageFile.getFile());

        mMainView.openCamera(photoUri);
    }

    Uri getImageFileUri() {
        return  mImageFile.getUri();
    }
}
