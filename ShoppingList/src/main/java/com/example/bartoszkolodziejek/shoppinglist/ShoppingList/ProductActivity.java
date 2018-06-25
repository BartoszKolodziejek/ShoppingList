package com.example.bartoszkolodziejek.shoppinglist.ShoppingList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.Product;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Spinner spinner = (Spinner) this.findViewById(R.id.unit);
        String[] values = getResources().getStringArray(R.array.units);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item_layout , values);
        spinner.setAdapter(arrayAdapter);
    }

    public void photoAction(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            final WebView webView = (WebView) findViewById(R.id.photo);
            webView.loadUrl("file://"+currentPhotoPath);
            try {
                File file = new File(currentPhotoPath);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(file));
                webView.zoomOut();
                webView.setInitialScale(getScale(bitmap.getWidth()));
                webView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

    public void upload(View view){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View addUrl = inflater.inflate(R.layout.upload_layout, null);
        final PopupWindow popupWindow = new PopupWindow(addUrl,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView collapse = (ImageView) addUrl.findViewById(R.id.collapse);
        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        final WebView webView = (WebView) findViewById(R.id.photo);
        Button button = (Button) addUrl.findViewById(R.id.add_item);
        final TextView textView = (TextView) addUrl.findViewById(R.id.url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.loadUrl(textView.getText().toString());
                webView.setVisibility(View.VISIBLE);
                popupWindow.dismiss();

            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(true);
        popupWindow.update();
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private int getScale(double PIC_WIDTH){
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(PIC_WIDTH);
        val = val * 100d;
        return val.intValue();
    }
    public void save(View view){
        TextView textView = (TextView) findViewById(R.id.name);
        WebView webView = (WebView) findViewById(R.id.photo);
        Spinner spinner = (Spinner) findViewById(R.id.unit);
        Product product = new Product( textView.getText().toString(), spinner.getSelectedItem().toString(), webView.getUrl());
        if(validate(textView, webView)){
            product.save();
        Toast toast = Toast.makeText(this, R.string.product_saved, Toast.LENGTH_SHORT);
        toast.show();
        super.onBackPressed();}
        else{
            Toast toast = Toast.makeText(this, R.string.product_validation, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private boolean validate(TextView textView, WebView webView){
        List<Product> products = Product.listAll(Product.class);
        String name = textView.getText().toString();
        for(Product product: products){
            if(product.getName().equals(name))
                return false;
        }
        if(name.equals(""))
            return false;

        return true && (webView.getUrl()!=null || !webView.getUrl().equals(""));
    }
}
