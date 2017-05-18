package com.bwldr.banjo.util;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * wrapper for image file
 */
public class ImageFile implements ImageFileContract {

    @VisibleForTesting
    File mImageFile;

    @Override
    public void create(FragmentActivity activity) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        mImageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        // delete temp file once VM terminates
        mImageFile.deleteOnExit();
    }

    @Override
    public void delete() {
        mImageFile = null;
    }

    @Override
    public boolean exists() {
        return mImageFile != null && mImageFile.exists();
    }

    @Override
    public File getFile() {
        return mImageFile;
    }

    @Override
    public Uri getUri() {
        return Uri.fromFile(mImageFile);
    }
}
