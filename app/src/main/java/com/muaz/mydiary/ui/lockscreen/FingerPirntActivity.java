package com.muaz.mydiary.ui.lockscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityFingerPirntBinding;
import com.muaz.mydiary.ui.others.FAQActivity;

import me.aflak.libraries.callback.FingerprintCallback;
import me.aflak.libraries.callback.FingerprintDialogCallback;
import me.aflak.libraries.dialog.DialogAnimation;
import me.aflak.libraries.dialog.FingerprintDialog;

public class FingerPirntActivity extends AppCompatActivity {
    ActivityFingerPirntBinding activityFingerPirntBinding;
    private FingerprintManager fingerprintManager;
    private KeyguardManager    keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFingerPirntBinding = ActivityFingerPirntBinding.inflate(getLayoutInflater());
        View view=activityFingerPirntBinding.getRoot();
        setContentView(view);
       /* //1: Android version should be equal or greater than marshmallow
        //2:Device has fingerprint scanner
        //3:Have permsission to use fingerprint in the app
        //4:Lock screen is secured with atleast one type of lock
        //5:Atleast one fingerprint is registered
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
                activityFingerPirntBinding.tvFingerprintText.setText("Finger print is not detected in device");


            } else if (ContextCompat.checkSelfPermission(FingerPirntActivity.this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED) {
                activityFingerPirntBinding.tvFingerprintText.setText("Permission are not granted to use finger print scanner");
            } else if (!keyguardManager.isKeyguardSecure()) {
                activityFingerPirntBinding.tvFingerprintText.setText("Add lock to your phone in setting");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                activityFingerPirntBinding.tvFingerprintText.setText("you should add atleast 1 fingerprint to use this feature");

            } else {
                activityFingerPirntBinding.tvFingerprintText.setText("Please your finger on scanner to access the app");
            }

        }*/
       /*activityFingerPirntBinding.fingerprint.callback(new FingerprintCallback() {
           @Override
           public void onAuthenticationSucceeded() {

           }

           @Override
           public void onAuthenticationFailed() {

           }

           @Override
           public void onAuthenticationError(int errorCode, String error) {

           }
       });*/

        FingerprintDialog.initialize(this)
                .title(R.string.fingerprint_title)
                .message(R.string.fingerprint_message)
                .enterAnimation(DialogAnimation.Enter.RIGHT)
                .exitAnimation(DialogAnimation.Exit.RIGHT)
                .circleScanningColor(R.color.colorAccent)
                .callback(new FingerprintDialogCallback() {
                    @Override
                    public void onAuthenticationSucceeded() {

                    }

                    @Override
                    public void onAuthenticationCancel() {


                    }
                })
                .show();

    }
}