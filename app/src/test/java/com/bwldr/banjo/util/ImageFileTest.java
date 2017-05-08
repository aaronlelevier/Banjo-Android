package com.bwldr.banjo.util;

import android.net.Uri;
import android.os.Environment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test creating and deleting of Image Files for our photo app
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Environment.class, File.class})
public class ImageFileTest {

    @Mock
    private File mDirectory;

    @Mock
    private File mImageFile;

    private ImageFile mFileHeper;

    @Mock
    private Uri mUri;

    @Before
    public void createImageFile() throws IOException {
        // get a ref to class under test
        mFileHeper = new ImageFile();

        // setup mocking for these 2 classes' static methods
        mockStatic(Environment.class, File.class);

        // make the Environment class return the mocked external storage dir
        when(Environment.getExternalStorageDirectory())
                .thenReturn(mDirectory);

        // make the File class return the mocked file
        when(File.createTempFile(anyString(), anyString(), eq(mDirectory)))
                .thenReturn(mImageFile);
    }

    @Test
    public void create_willCreateAnImageFile() throws IOException {
        assertThat(mFileHeper.mImageFile, is(nullValue()));

        mFileHeper.create("Name", "Extension");

        assertThat(mFileHeper.mImageFile, is(notNullValue()));
    }

    @Test
    public void delete_removesImageFile() throws IOException {
        mFileHeper.create("Name", "Extension");

        assertThat(mFileHeper.mImageFile, is(notNullValue()));

        mFileHeper.delete();

        assertThat(mFileHeper.mImageFile, is(nullValue()));
    }

    @Test
    public void exists_returnsBooleanIfFileExists() throws IOException {
        assertFalse(mFileHeper.exists());

        mFileHeper.create("Name", "Extension");

        assertTrue(mFileHeper.exists());
    }
}
