package jp.co.mixi.androidtraining.graduates2014.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;

import jp.co.mixi.androidtraining.graduates2014.app.provider.AssetsFileProvider;
import jp.co.mixi.androidtraining.graduates2014.app.provider.NextActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 123;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

        Button cameraButton = (Button)findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.nextButton:
                mImageUri = Uri.parse("content://" + AssetsFileProvider.AUTHORITY + "/m_balloon_icon.png");
                intent = new Intent(this, NextActivity.class);
                intent.putExtra("AssetUri", mImageUri.toString());
                startActivity(intent);
                break;
            case R.id.cameraButton:
                File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String file = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                File photo = new File(folder, file);
                mImageUri =  Uri.fromFile(photo);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Intent intent = new Intent(this, NextActivity.class);
            intent.putExtra("AssetUri", mImageUri.getPath());
            intent.putExtra("IsCamera", true);
            startActivity(intent);
        }
    }
}
