package com.bwldr.banjo.util;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;

import java.io.File;
import java.io.IOException;

/**
 * wrapper for image file
 */
public class ImageFile implements ImageFileContract {

    @VisibleForTesting
    File mImageFile;

    @Override
    public void create(String filename, String suffix) throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        mImageFile = File.createTempFile(filename, suffix, storageDir);
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
