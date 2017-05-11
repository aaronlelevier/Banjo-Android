package com.bwldr.banjo;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;

import com.bwldr.banjo.util.ImageFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest({FileProvider.class})
public class MainPresenterTest {

    @Mock
    private MainContract.View mMainView;

    @Mock
    private ImageFile mImageFile;

    @Mock
    private FragmentActivity mActivity;

    @Mock
    private File mFile;

    @Mock
    private Uri mUri;

    private MainPresenter mMainPresenter;

    @Before
    public void setUp() {
        // injects @Mock decorated fields above
        MockitoAnnotations.initMocks(this);

        mMainPresenter = new MainPresenter(mMainView, mImageFile);

        mockStatic(FileProvider.class);

        when(FileProvider.getUriForFile((FragmentActivity)anyObject(), anyString(), (File)anyObject()))
                .thenReturn(mUri);

        when(mMainView.getActivity())
                .thenReturn(mActivity);
    }

    @Test
    public void takePicture() throws IOException {
        mMainPresenter.takePicture();

        verify(mImageFile).createImageFile(mActivity);
        verify(mMainView).openCamera(mUri);
    }
}