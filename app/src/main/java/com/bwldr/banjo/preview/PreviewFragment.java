package com.bwldr.banjo.preview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwldr.banjo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreviewFragment extends Fragment {

    public PreviewFragment() {
    }

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        Uri photoUri = Uri.parse(getArguments().getString("data"));
        ((ImageView) view.findViewById(R.id.image)).setImageURI(photoUri);

        return view;
    }
}
