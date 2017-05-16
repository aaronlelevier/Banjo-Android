package com.bwldr.banjo.preview;


import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PreviewPresenter.class, Environment.class, File.class})
public class PreviewPresenterTest {

    private PreviewPresenter mPreviewPresenter;

    @Mock
    private PreviewContract.View mockMainView;

    @Mock
    private FragmentActivity mockActivity;

    @Mock
    private Uri mockUri;

    @Mock
    private File mockPhotoDir;

    @Mock
    private File mockOutputDir;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPreviewPresenter = new PreviewPresenter(mockMainView);

        mockStatic(Environment.class);

        when(Environment.getExternalStorageState())
                .thenReturn(Environment.MEDIA_MOUNTED);

        when(Environment.getExternalStoragePublicDirectory(anyString()))
                .thenReturn(mockPhotoDir);

        try {
            mockOutputDir = mock(File.class);
            whenNew(File.class).withAnyArguments()
                    .thenReturn(mockOutputDir);
            when(mockOutputDir.exists())
                    .thenReturn(true);
        } catch (Exception e) {
            throw new AssertionError("new File(..) mock failed");
        }
    }

    @Test
    public void broadcastNewFile() {
        PreviewPresenter spyPresenter = spy(new PreviewPresenter(mockMainView));

        spyPresenter.broadcastNewFile(mockActivity, mockUri);

        verify(spyPresenter).getFileFromUri(mockActivity, mockUri);
    }

    @Test
    public void getFileFromUri() {
        File ret = mPreviewPresenter.getFileFromUri(mockActivity, mockUri);

        assertTrue(ret != null);
    }

    @Test
    public void getOrCreatePhotoDirectory_getExternalStorageState() {
        File ret = mPreviewPresenter.getOrCreatePhotoDirectory(mockActivity);

        verifyStatic();
        Environment.getExternalStorageState();

        verifyStatic();
        Environment.getExternalStoragePublicDirectory(anyString());

        verify(mockOutputDir).exists();
        verify(mockOutputDir, never()).mkdirs();
        assertTrue(ret != null);
    }

    @Test
    public void getOrCreatePhotoDirectory_getExternalStorageState_mediaNotMounted() {
        when(Environment.getExternalStorageState())
                .thenReturn("not mounted");

        assertEquals(null, mPreviewPresenter.getOrCreatePhotoDirectory(mockActivity));

        verifyStatic();
        Environment.getExternalStorageState();
    }

    @Test
    public void getOrCreatePhotoDirectory_outputDirDoesNotExist_mkdirsCallSucceeds() {
        when(mockOutputDir.exists())
                .thenReturn(false);
        when(mockOutputDir.mkdirs())
                .thenReturn(true);

        File ret = mPreviewPresenter.getOrCreatePhotoDirectory(mockActivity);

        verify(mockOutputDir).exists();
        verify(mockOutputDir).mkdirs();
        assertTrue(ret != null);
    }

    @Test
    public void getOrCreatePhotoDirectory_outputDirDoesNotExist_mkdirsCallFails() {
        when(mockOutputDir.exists())
                .thenReturn(false);
        when(mockOutputDir.mkdirs())
                .thenReturn(false);

        File ret = mPreviewPresenter.getOrCreatePhotoDirectory(mockActivity);

        verify(mockOutputDir).exists();
        verify(mockOutputDir).mkdirs();
        assertTrue(ret == null);
    }
}
