package com.softaai.randomimagerenderers.renderers;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pedrogomez.renderers.Renderer;
import com.softaai.randomimagerenderers.R;
import com.softaai.randomimagerenderers.model.RandomImageResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amol Pawar on 16-05-2019.
 * softAai Apps
 */
public abstract class RandomImageRenderer extends Renderer<RandomImageResponse> {

    @BindView(R.id.tv_date)
    TextView date;
    @BindView(R.id.iv_random_image)
    ImageView image;
    @BindView(R.id.tv_sha256) TextView sha256;

    /**
     * Inflate the main layout used to render images in the list view.
     *
     * @param inflater LayoutInflater service to inflate.
     * @param parent ViewGroup used to inflate xml.
     * @return view inflated.
     */
    @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View inflatedView = inflater.inflate(R.layout.random_image_renderer, parent, false);
        /*
         * You don't have to use ButterKnife library to implement the mapping between your layout
         * and your widgets you can implement setUpView and hookListener methods declared in
         * Renderer<T> class.
         */
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @OnClick(R.id.iv_random_image) void onImageClicked() {
        RandomImageResponse randomImageResponse = getContent();
        Toast.makeText(getContext(), "Image clicked. Author = " + randomImageResponse.getAuthor(), Toast.LENGTH_LONG)
                .show();
    }

    /**
     * Main render algorithm based on render the image, render the date, render the sha256 value
     */
    @Override public void render() {
        final RandomImageResponse randomImageResponse = getContent();
        renderDate();
        renderImage(randomImageResponse);
        try {
            Thread tread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        renderSha256(randomImageResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            tread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void renderDate(){
        date.setText(getDownloadDate());

    }


    /**
     * Use glide to render the image into the imageview widget using a temporal
     * placeholder.
     *
     * @param randomImageResponse to get the rendered thumbnail.
     */
    private void renderImage(RandomImageResponse randomImageResponse) {


        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.placeholder);
        options.error(new ColorDrawable(Color.RED));
        options.centerCrop();

        Glide.with(image)
                .load(randomImageResponse.getDownloadUrl())
                .apply(options)
                .into(image);
    }

    /**
     * Render image date into the date widget.
     *
     * @param //randomImageResponse to get the image download date.
     */
//    private void renderDate(RandomImageResponse randomImageResponse) {
//        this.date.setText(randomImageResponse.getDate());
//    }



    public byte[] createChecksum(String filename) throws Exception {
        // read image from url
        BufferedInputStream inputStream = new BufferedInputStream(new URL(filename).openStream());

        InputStream fis =  inputStream;

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA-256");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }


    // a byte array to a HEX string
    public String getSha256Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    protected void renderSha256(RandomImageResponse randomImageResponse) throws Exception {
        sha256.setText(getSha256Checksum(randomImageResponse.getDownloadUrl()));
        sha256.setText("abcsdks;jsdlfjdslfjwefewqwe0924qd,wwd");
    }


    public String getDownloadDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String downloadDate = "Download Date : " + mdformat.format(calendar.getTime());
        return downloadDate;
    }

    private String checksum(String filepath, MessageDigest md) throws IOException {

        // file hashing with DigestInputStream
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }


    /**
     * Maps all the view elements from the xml declaration to members of this renderer.
     */
    @Override protected void setUpView(View rootView) {
        /*
         * Empty implementation substituted with the usage of ButterKnife library by Jake Wharton.
         */
    }

    /**
     * Insert external listeners in some widgets.
     */
    @Override protected void hookListeners(View rootView) {
        /*
         * Empty implementation substituted with the usage of ButterKnife library by Jake Wharton.
         */
    }
}


