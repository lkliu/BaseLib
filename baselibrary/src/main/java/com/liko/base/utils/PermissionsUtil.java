package com.liko.base.utils;

import android.Manifest;
import android.util.Log;

import com.liko.base.mvp.base.IBaseView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Liko on 17/10/30.
 */

public class PermissionsUtil {
    public interface RequestPermission {
        void onRequestPermissionSuccess(boolean flag);
    }

    /**
     * 请求摄像头权限
     */
    public static void launchCamera(final RequestPermission requestPermission, RxPermissions rxPermissions, final IBaseView view) {
        //先确保是否已经申请过摄像头，和写入外部存储的权限
        boolean isPermissionsGranted = rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && rxPermissions.isGranted(Manifest.permission.CAMERA);
        //已经申请过，直接执行操作
        if (isPermissionsGranted) {
            requestPermission.onRequestPermissionSuccess(true);
        } else {
            //没有申请过，则申请
            rxPermissions.requestEach(Manifest.permission.CAMERA
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                requestPermission.onRequestPermissionSuccess(true);
                                // 用户已经同意该权限
                                LogUtils.debugInfo(permission.name + " is granted.");
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                requestPermission.onRequestPermissionSuccess(false);
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                LogUtils.debugInfo(permission.name + " is denied. More info should be provided.");
                            } else {
                                requestPermission.onRequestPermissionSuccess(false);
                                // 用户拒绝了该权限，并且选中『不再询问』
                                LogUtils.debugInfo(permission.name + " is denied.");
                                view.showMessage("已拒绝摄像头权限,会影响部分功能,建议开启");
                            }
                        }
                    });
        }
    }

    /**
     * 请求打电话权限
     */
    public static void callPhone(final RequestPermission requestPermission, RxPermissions rxPermissions, final IBaseView view) {
        //先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions.isGranted(Manifest.permission.CALL_PHONE);
        //已经申请过，直接执行操作
        if (isPermissionsGranted) {
            requestPermission.onRequestPermissionSuccess(true);
        } else {//没有申请过，则申请
            rxPermissions
                    .requestEach(Manifest.permission.CALL_PHONE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(@NonNull Permission permission) throws Exception {
                            if (permission.granted) {
                                requestPermission.onRequestPermissionSuccess(true);
                                // 用户已经同意该权限
                                LogUtils.debugInfo(permission.name + " is granted.");
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                requestPermission.onRequestPermissionSuccess(false);
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                LogUtils.debugInfo(permission.name + " is denied. More info should be provided.");
                            } else {
                                requestPermission.onRequestPermissionSuccess(false);
                                // 用户拒绝了该权限，并且选中『不再询问』
                                LogUtils.debugInfo(permission.name + " is denied.");
                                view.showMessage("已拒绝打电话权限,会影响部分功能,建议开启");
                            }
                        }
                    });
        }
    }
}
