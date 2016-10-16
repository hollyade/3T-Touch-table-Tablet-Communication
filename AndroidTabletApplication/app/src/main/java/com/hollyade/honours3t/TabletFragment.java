package com.hollyade.honours3t;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class TabletFragment extends Fragment {

    private static final String LOG = "TabletFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private boolean firstImageUpload = true;
    private String importedImage;

    public TabletFragment() {
    }

    public static TabletFragment newInstance(int sectionNumber) {
        TabletFragment fragment = new TabletFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tablet, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final FloatingActionButton loadImageButton = (FloatingActionButton) getView().findViewById(R.id.buttonLoadPicture);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importImage();
            }
        });
        final FloatingActionButton sendImageButton = (FloatingActionButton) getView().findViewById(R.id.buttonSendPicture);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableTabletActivity tableTabletActivity = (TableTabletActivity) getActivity();
                tableTabletActivity.getCommunicationProvider().sendCommunication(tableTabletActivity.getUsername(),importedImage);
                Toast.makeText(getContext(), "Image Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void importImage(){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                Uri image = data.getData();
                ImageView uploadedImage = (ImageView) getActivity().findViewById(R.id.importedImage);
                uploadedImage.setVisibility(View.INVISIBLE);
                ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                new BitmapWorkerTask().execute(uploadedImage, image);

                moveLoadImageButton();
            } else {
                Log.v(LOG, "No image chosen.");
            }
        } catch (Exception e) {
            Log.v(LOG, "Exception " + e.getMessage());
        }
    }

    private void moveLoadImageButton() {
        getView().findViewById(R.id.loadPictureText).setVisibility(View.INVISIBLE);
        final FloatingActionButton loadImageButton = (FloatingActionButton) getView().findViewById(R.id.buttonLoadPicture);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loadImageButton.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        loadImageButton.setLayoutParams(params);
    }

    private void tutorialVisibility(){
        Intent intent = new Intent(getActivity(), SendButtonTutorialActivity.class);
        FloatingActionButton floatingActionSendButton = (FloatingActionButton) getActivity().findViewById(R.id.buttonSendPicture);
        float x = floatingActionSendButton.getX();
        float y = floatingActionSendButton.getY();
        intent.putExtra("X", x);
        intent.putExtra("Y", y);
        getActivity().startActivity(intent);
        firstImageUpload = false;
    }

    private class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
        private Bitmap bitmap;
        private ImageView view;

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Object... params) {

            // Get the passed arguments here
            view = (ImageView) params[0];
            Uri uri = (Uri)params[1];

            // Create bitmap from passed in Uri here
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String decodableString = cursor.getString(columnIndex);
            cursor.close();

            this.bitmap = BitmapFactory.decodeFile(decodableString);

            //Compress for sending
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmapCopy = BitmapFactory.decodeFile(decodableString);
            bitmapCopy.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            bitmapCopy.recycle();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            importedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (view != null && bitmap != null) {
                view.setImageBitmap(this.bitmap);
                view.setVisibility(View.VISIBLE);
                final FloatingActionButton sendImageButton = (FloatingActionButton) getView().findViewById(R.id.buttonSendPicture);
                if (firstImageUpload) {
                    tutorialVisibility();
                }
                sendImageButton.setVisibility(View.VISIBLE);
                ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
            }

        }
    }
}

