package com.uits.baseproject.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.uits.baseproject.utils.rxpermission.Permission;
import com.uits.baseproject.utils.rxpermission.PermissionObservable;

import io.reactivex.observers.DisposableObserver;


public class PermissionUtil {
    private static final String[] mArrayPermissionsPhoto = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final String[] mArrayPermissionsRecord = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final String[] mArrayPermissionsCamera = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final String[] mArrayPermissionsLocation = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private static final String[] mArrayPermissionsCall = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};

    private static final String[] mArrayPermissionContact = {Manifest.permission.READ_CONTACTS};

    private static final String[] mArrayPermissionNumberPhone = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.SEND_SMS,};

    /**
     * simple permission callback
     */
    public interface PermissionCallbackSuccess {
        void onPermissionComplete();
    }

    /**
     * simple permission callback
     */
    public interface PermissionCallback {
        void onPermissionComplete();

        void onPermissionError();
    }

    /**
     * multi permission callback
     */
    public interface MultiPermissionCallback {
        void onPermissionComplete();

        void onPermissionError();

        void onPermissionNext(String permission, boolean isGrant);
    }

    /**
     * Check all permission on Android M
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check each permission on Android M
     */
    public static boolean hasPermissions(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permission != null) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * check simple permission
     *
     * @param context
     * @param permission
     * @param callback
     */
    public static void checkPermission(final Context context, final String permission, final PermissionCallback callback) {
        PermissionObservable.getInstance().request(context, permission).subscribe(new DisposableObserver<Permission>() {
            @Override
            public void onNext(Permission value) {

            }

            @Override
            public void onError(Throwable e) {
                callback.onPermissionError();
            }

            @Override
            public void onComplete() {
                if (hasPermissions(context, permission)) {
                    callback.onPermissionComplete();
                } else {
                    callback.onPermissionError();
                }
            }
        });
    }

    /**
     * check multi permission
     *
     * @param context
     * @param callback
     * @param permission
     */
    public static void checkPermission(Context context, final MultiPermissionCallback callback, String... permission) {
        PermissionObservable.getInstance().request(context, permission).subscribe(new DisposableObserver<Permission>() {
            @Override
            public void onNext(Permission value) {
                callback.onPermissionNext(value.getName(), value.getGranted() == 0 ? false : true);
            }

            @Override
            public void onError(Throwable e) {
                callback.onPermissionError();
            }

            @Override
            public void onComplete() {
                callback.onPermissionComplete();
            }
        });
    }

    /**
     * photo
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionPhoto(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsPhoto)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsPhoto);
    }

    /**
     * photo
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionContact(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionContact)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionContact);
    }

    /**
     * Camera
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionCamera(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCamera)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsCamera);
    }

    /**
     * Record
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionRecord(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsRecord)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsRecord);
    }

    /**
     * Location
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionLocation(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsLocation)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsLocation);
    }

    /**
     * Call
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionCall(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCall)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsCall);
    }

    /**
     * Call
     *
     * @param context
     * @param callback
     */
    public static void checkPermissionNumberPhone(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionNumberPhone)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionNumberPhone);
    }
}
