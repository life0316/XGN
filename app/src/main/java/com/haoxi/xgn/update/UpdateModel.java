package com.haoxi.xgn.update;

import android.os.Environment;
import android.util.Log;

import com.haoxi.xgn.base.BaseModel;
import com.haoxi.xgn.base.BaseSubscriber;
import com.haoxi.xgn.base.RequestCallback;
import com.haoxi.xgn.bean.UpdateBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UpdateModel  extends BaseModel implements IUpdateModel<UpdateBean>{
    @Override
    public void downloadApk(String apkUrl, final MyProgressCallback progressCallback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(apkUrl).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @SuppressWarnings("resource")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    progressCallback.onProgressMax(100);
                    File file = new File(SDPath, "dove.apk");
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        progressCallback.onProgressCurrent(progress);
                    }
                    fos.flush();
                    progressCallback.onSuccess(file.getAbsolutePath());
                } catch (Exception e) {
                    progressCallback.onError(e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException arg1) {
                progressCallback.onFailure("下载失败");
            }
        });
    }

    @Override
    public void updateVar(Map<String, String> map, final RequestCallback<UpdateBean> requestCallback) {
        netService.getAppVersion(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        requestCallback.beforeRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<UpdateBean, Boolean>() {
                    @Override
                    public Boolean call(UpdateBean codesBean) {
                        Log.e("fadvwasvb",codesBean.getMsg()+"-----"+codesBean.getCode());
                        return 200 == codesBean.getCode();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(requestCallback));
    }
}
