package miq0717.mapsandplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PermissionsActivity extends AppCompatActivity {
    private Context context;

    @BindView(R.id.btnGrantPermission)
    Button btnGrantPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        context = this;
        ButterKnife.bind(this);
        checkLocationPermission();
        setListeners();
    }

    private void setListeners() {
        btnGrantPermission.setOnClickListener(v -> {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            startActivity(new Intent(context, MapActivity.class));
                            finish();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(context.getString(R.string.permission_denied))
                                        .setMessage(context.getString(R.string.permission_denied_permanently))
                                        .setNegativeButton("Cancel", null)
                                        .setPositiveButton("OK", (dialog, which) -> {
                                            Log.e("PERMANENTLY DENIED", "Go to settings");
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        })
                                        .show();
                            } else
                                Toast.makeText(context, context.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .check();
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(context, MapActivity.class));
            finish();
            return;
        }
    }
}
