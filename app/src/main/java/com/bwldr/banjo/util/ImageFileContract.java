package com.bwldr.banjo.util;

import java.io.IOException;

/**
 * Wrapper for image files
 */
interface ImageFileContract {

    void create() throws IOException;

    void delete();

    boolean exists();

    String getPath();
}
