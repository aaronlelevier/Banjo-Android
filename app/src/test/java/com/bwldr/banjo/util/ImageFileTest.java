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

    private ImageFile mFileHelper;

    @Mock
    private File mockDirectory;

    @Mock
    private File mockImageFile;

    @Mock
    private Uri mockUri;

    @Before
    public void createImageFile() throws IOException {
        // get a ref to class under test
        mFileHelper = new ImageFile();

        // setup mocking for these 2 classes' static methods
        mockStatic(Environment.class, File.class);

        // make the Environment class return the mocked external storage dir
        when(Environment.getExternalStorageDirectory())
                .thenReturn(mockDirectory);

        // make the File class return the mocked file
        when(File.createTempFile(anyString(), anyString(), eq(mockDirectory)))
                .thenReturn(mockImageFile);
    }

    @Test
    public void create_willCreateAnImageFile() throws IOException {
        assertThat(mFileHelper.mImageFile, is(nullValue()));

        mFileHelper.create();

        assertThat(mFileHelper.mImageFile, is(notNullValue()));
    }

    @Test
    public void delete_removesImageFile() throws IOException {
        mFileHelper.create();

        assertThat(mFileHelper.mImageFile, is(notNullValue()));

        mFileHelper.delete();

        assertThat(mFileHelper.mImageFile, is(nullValue()));
    }

    @Test
    public void exists_returnsBooleanIfFileExists() throws IOException {
        assertFalse(mFileHelper.exists());

        mFileHelper.create();

        assertTrue(mFileHelper.exists());
    }
}
