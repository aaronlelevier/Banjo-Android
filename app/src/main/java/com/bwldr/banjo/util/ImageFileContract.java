package com.bwldr.banjo.util;

import java.io.IOException;

/**
 * Wrapper for image files
 */
interface ImageFileContract {

    void create(String name, String ext) throws IOException;

    void delete();

    boolean exists();

    String getPath();
}
