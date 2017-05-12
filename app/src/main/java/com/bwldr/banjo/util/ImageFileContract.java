package com.bwldr.banjo.util;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import java.io.File;
import java.io.IOException;

/**
 * Wrapper for image files
 */
interface ImageFileContract {

    void create(FragmentActivity activity) throws IOException;

    void delete();

    boolean exists();

    File getFile();

    Uri getUri();
}
