package com.bwldr.banjo;

import android.net.Uri;

import com.bwldr.banjo.util.ImageFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class MainPresenterTest {

    private MainPresenter mMainPresenter;

    @Mock
    private MainContract.View mockMainView;

    @Mock
    private ImageFile mockImageFile;

    @Mock
    private File mockFile;

    @Mock
    private Uri mockUri;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mMainPresenter = new MainPresenter(mockMainView, mockImageFile);
    }

    @Test
    public void takePicture() throws IOException {
        mMainPresenter.takePicture();
        verify(mockImageFile).create();
        verify(mockMainView).openCamera();
    }

    @Test
    public void getUri() {
        mMainPresenter.getUri();
        verify(mockImageFile).getUri();
    }

    @Test
    public void getFile() {
        mMainPresenter.getFile();
        verify(mockImageFile).getFile();
    }
}