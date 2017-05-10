package com.bwldr.banjo.util;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;

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
    public void create() throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        mImageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
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
    public String getPath() {
        return Uri.fromFile(mImageFile).toString();
    }
}
