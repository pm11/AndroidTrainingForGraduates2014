package jp.co.mixi.androidtraining.graduates2014.app.provider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import jp.co.mixi.androidtraining.graduates2014.app.R;

public class NextActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        mImageView = (ImageView)findViewById(R.id.image);

        Intent received = getIntent();
        String urlString = received.getStringExtra("AssetUri");
        Boolean isCamera = received.getBooleanExtra("IsCamera", false);

        if (urlString == null) {
            return;
        }

        Uri uri = Uri.parse(urlString);


        try {
            Bitmap bitmap;
            if(isCamera) {
                bitmap = BitmapFactory.decodeFile(uri.getPath());
            } else {
                InputStream in = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(in);
            }
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
