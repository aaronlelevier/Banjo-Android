package com.bwldr.banjo.util;

import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * Wrapper for image files
 */
interface ImageFileContract {

    void create() throws IOException;

    void delete();

    boolean exists();

    File getFile();

    Uri getUri();
}
