package com.zhou.mvpstandarddemo.comm.network;

import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @author jett
 * @since 2017-11-16.
 */
public class Https {

    private static final OkHttpClient mClient;

    static {
        // 设置网络拦截器，是因为有些数据来源会重定向
        mClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS)//设置写入超时时间
                .build();
    }

    private static final String TYPE_JSON = "application/x-www-form-urlencoded; charset=utf-8";

    private String mUrl;
    private String mParam;
    private String contentType;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private List<UploadData> mUploadFiles;
    private boolean responseOnUiThread = true;
    private boolean print = false;

    private Call mCall;

    public Https() {
        this(null);
    }

    public Https(String url) {
        this.mUrl = url;
    }

    public Https addHeader(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        mHeaders.put(key, value);
        return this;
    }

    public Https addHeaders(Map<String, String> headers) {
        if (this.mHeaders == null) {
            this.mHeaders = new HashMap<>();
        }
        this.mHeaders.putAll(headers);
        return this;
    }

    public Https url(String url) {
        this.mUrl = url;
        return this;
    }

    public Https setParam(String param, String contentType) {
        this.mParam = param;
        this.contentType = contentType;
        return this;
    }

    public Https setParam(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    public Map<String, String> getParam() {
        return mParams;
    }

    public Https addParam(String key, int value) {
        return addParam(key, String.valueOf(value));
    }

    public Https addParam(String key, String value) {
        if (this.mParams == null) {
            mParams = new HashMap<>();
        }
        mParams.put(key, value);
        return this;
    }

    public Https addParams(Map<String, String> params) {
        if (this.mParams == null) {
            mParams = new HashMap<>();
        }
        mParams.putAll(params);
        return this;
    }


    public OkHttpClient getOkHttpClient() {
        return mClient.newBuilder().build();
    }

    private Handler getMainHandler() {
        return new Handler(Looper.getMainLooper());// 你不就是想获得主线程的handler发消息吗，用得着这么绕？
    }

    private Request buildGetRequest() {
        Request.Builder builder = getDefaultRequestBuilder();
        if (mParams != null && !mParams.isEmpty()) {
            Uri.Builder upon = Uri.parse(mUrl).buildUpon();
            for (Map.Entry<String, String> me : mParams.entrySet()) {
                String key = me.getKey();
                String value = me.getValue();
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                if (value == null) {
                    value = "";
                }
                upon.appendQueryParameter(key, value);
            }
            builder.url(upon.toString());
        } else {
            builder.url(mUrl);
        }
        appendHeadersIfExsit(builder);
        return builder.build();
    }

    private Request buildPostRequest() {
        Request.Builder builder = getDefaultRequestBuilder();
        builder.url(mUrl);

        if (mParams != null && !mParams.isEmpty()) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> me : mParams.entrySet()) {
                String key = me.getKey();
                String value = me.getValue();
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                if (value == null) {
                    value = "";
                }
                bodyBuilder.add(key, value);
            }
            builder.post(bodyBuilder.build());
        } else {
            MediaType mediaType = MediaType.parse(this.contentType == null ? TYPE_JSON : this.contentType);
            RequestBody body = RequestBody.create(mediaType, mParam == null ? "" : mParam);
            builder.post(body);
        }

        appendHeadersIfExsit(builder);
        return builder.build();
    }

    private Request buildUploadRequest(final UploadListener listener) {
        Request.Builder builder = getDefaultRequestBuilder();
        builder.url(mUrl);

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, String> me : mParams.entrySet()) {
                String key = me.getKey();
                String value = me.getValue();
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                if (value == null) {
                    value = "";
                }
                bodyBuilder.addFormDataPart(key, value);
            }
        }
        if (mUploadFiles != null && !mUploadFiles.isEmpty()) {
            int counter = 0;
            for (UploadData data : mUploadFiles) {
                RequestBody rBody = RequestBody.create(MediaType.parse("application/octet-stream"), data.file);
                if (TextUtils.isEmpty(data.name)) {
                    data.name = "file_" + counter++;
                }
                if (TextUtils.isEmpty(data.fileName)) {
                    data.fileName = getFileName(data.file.getPath());
                }
                bodyBuilder.addFormDataPart(data.name, data.fileName, rBody);
            }
        }
        builder.post(new UploadRequestBody(bodyBuilder.build()) {
            @Override
            public void loading(long current, long total, boolean done) {
                if (listener != null) {
                    listener.onUploading(current, total, done);
                }
            }
        });

        appendHeadersIfExsit(builder);
        return builder.build();
    }

    private String getFileName(String path) {
        if (!TextUtils.isEmpty(path)) {
            int index = path.lastIndexOf("/");
            if (index > 0) {
                return path.substring(index + 1, path.length());
            }
        }
        return "";
    }

    public String getUrl() {
        return mUrl;
    }

    private Request buildDownloadRequest() {
        Request.Builder builder = getDefaultRequestBuilder();
        builder.url(mUrl);

        appendHeadersIfExsit(builder);
        return builder.build();
    }

    private void appendHeadersIfExsit(Request.Builder builder) {
        if (mHeaders != null && !mHeaders.isEmpty()) {
            for (Map.Entry<String, String> me : mHeaders.entrySet()) {
                builder.addHeader(me.getKey(), me.getValue());
            }
        }
    }

    private Request.Builder getDefaultRequestBuilder() {
        return new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
    }

    private Response execute(Request request) throws IOException {
        OkHttpClient client = getOkHttpClient();
        mCall = client.newCall(request);
        return mCall.execute();
    }

    private <T> void enqueue(Request request, final ResponseCallback<T> callback) {
        OkHttpClient client = getOkHttpClient();
        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleException(call, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(call, response, callback);
            }
        });
    }

    private <T> void handleResponse(Call call, final Response response, final ResponseCallback<T> callback) {
        String printResult = null;
        if (call != null && callback != null) {
            final Request request = call.request();
            final int code = response.code();
            try {
                Type genType = callback.getClass().getGenericInterfaces()[0];
                Type type;
                if (genType instanceof ParameterizedType) {
                    type = ((ParameterizedType) genType).getActualTypeArguments()[0];
                } else {
                    type = String.class;
                }
                final T result = convert(response, (Class<T>) type);
                if (print && result != null) {
                    printResult = result.toString();
                }
                postResult(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(request, response, result, code);
                    }
                });
            } catch (final IOException e) {
                postResult(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(request, e);
                    }
                });
            }
        }
        if (print) {
            printHttpResponse(call, response, printResult);
        }
    }

    private <T> T convert(Response response, Class<T> c) throws IOException {
        if (c == InputStream.class) {
            return (T) response.body().byteStream();
        } else {
            String result = response.body().string();
            if (c == String.class) {
                return (T) result;
            } else {
                try {
                    return new Gson().fromJson(result, c);
                } catch (Exception ignore) {
                    return null;
                }
            }
        }
    }

    private <T> void handleException(Call call, final IOException e, final ResponseCallback<T> callback) {
        if (callback != null) {
            final Request request = call.request();
            postResult(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(request, e);
                }
            });
        }
        if (print) {
            printHttpException(call, e);
        }
    }

    private void postResult(Runnable r) {
        if (r == null) {
            return;
        }
        if (responseOnUiThread && Looper.myLooper() != Looper.getMainLooper()) {
            getMainHandler().post(r);
        } else {
            r.run();
        }
    }

    private void printHttpResponse(Call call, Response response, String result) {
        final Request request = call.request();
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        sb.append("\n");
        sb.append("----start----start----start-----start-----start----start-----start----").append("\n");
        sb.append(request.method()).append("\n");
        sb.append(request.url().toString()).append("\n");
        if (!TextUtils.isEmpty(mParam)) {
            sb.append(mParam).append("\n");
        }
        if (mParams != null) {
            for (Map.Entry<String, String> me : mParams.entrySet()) {
                String value = me.getValue();
                if (value != null && value.length() > 1024) {
                    value = "too long,so substring it  :::  " + value.substring(0, 99);
                }
                sb.append(me.getKey()).append("=").append(value).append("\n");
            }
        }
        sb.append(response.code()).append("\n");
        try {
            sb.append(new JSONObject(result).toString(4)).append("\n");
        } catch (Exception e) {
            sb.append(result).append("\n");
        }
        sb.append("----end-----end-----end-----end-----").append("\n\n");
    }

    private void printHttpException(Call call, IOException e) {
        final Request request = call.request();
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        sb.append("\n");
        sb.append("----start----start----start-----start-----start----start-----start----").append("\n");
        sb.append(request.method()).append("\n");
        sb.append(request.url().toString()).append("\n");
        if (!TextUtils.isEmpty(mParam)) {
            sb.append(mParam).append("\n");
        }
        if (mParams != null) {
            for (Map.Entry<String, String> me : mParams.entrySet()) {
                String value = me.getValue();
                if (value != null && value.length() > 1024) {
                    value = "too long,so substring it  :::  " + value.substring(0, 99);
                }
                sb.append(me.getKey()).append("=").append(value).append("\n");
            }
        }
        sb.append(e.getMessage()).append("\n");
        sb.append("----end-----end-----end-----end-----").append("\n\n");
    }

    public Response get() throws IOException {
        return execute(buildGetRequest());
    }

    public Response post() throws IOException {
        return execute(buildPostRequest());
    }

    public Response upload() throws IOException {
        return execute(buildUploadRequest(null));
    }

    public <T> void get(ResponseCallback<T> callBack) {
        enqueue(buildGetRequest(), callBack);
    }

    public <T> void post(ResponseCallback<T> callBack) {
        enqueue(buildPostRequest(), callBack);
    }

    public <T> void upload(final UploadListener listener) {
        enqueue(buildUploadRequest(listener), listener);
    }

    public <T> void download(final File saveFile, final DownloadListener listener) {
        Request request = buildDownloadRequest();
        OkHttpClient client = getOkHttpClient();
        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postDownloadFailure(listener, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleDownloadResponse(saveFile, response, listener);
            }
        });
    }

    private void handleDownloadResponse(File saveFile, Response response, final DownloadListener listener) {
        if (response == null || !response.isSuccessful()) {
            postDownloadFailure(listener, new IOException("response is null or resoonse is failure.code = " + (response == null ? null : response.code())));
            return;
        }
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        if (saveFile.exists()) {
            saveFile.delete();
        }
        FileOutputStream out = null;
        long total = response.body().contentLength();
        try {
            saveFile.createNewFile();
            InputStream in = response.body().byteStream();
            out = new FileOutputStream(saveFile);
            byte[] buf = new byte[8192];
            int len;
            long progress = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                progress += len;
                postDownloadProgress(listener, progress, total);
            }
            out.close();
            postDownloadCompleted(listener);
        } catch (IOException e) {
            postDownloadFailure(listener, e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private void postDownloadCompleted(final DownloadListener listener) {
        if (listener != null) {
            postResult(new Runnable() {
                @Override
                public void run() {
                    listener.onCompleted();
                }
            });
        }
    }

    private void postDownloadFailure(final DownloadListener listener, final Exception e) {
        if (listener != null) {
            postResult(new Runnable() {
                @Override
                public void run() {
                    listener.onFailure(e);
                }
            });
        }
    }

    private void postDownloadProgress(final DownloadListener listener, final long progress, final long total) {
        if (listener != null) {
            postResult(new Runnable() {
                @Override
                public void run() {
                    listener.onProgress(progress, total);
                }
            });
        }
    }

    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    private class UploadData {
        String name;
        String fileName;
        File file;
    }

    private abstract class UploadRequestBody extends RequestBody {

        private final RequestBody original;
        private BufferedSink bufferedSink;

        UploadRequestBody(RequestBody original) {
            this.original = original;
        }

        @Override
        public MediaType contentType() {
            return original.contentType();
        }

        @Override
        public long contentLength() throws IOException {
            return original.contentLength();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                bufferedSink = Okio.buffer(sink(sink));
            }
            original.writeTo(bufferedSink);
            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink.flush();
        }

        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                private long current;
                private long total;
                //private int last = 0;

                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (total == 0) {
                        total = contentLength();
                    }
                    current += byteCount;
                    //int now = (int) (current * 100 / total);
                    /*if (last < now) {
                        loading(last, 100, total == current);
                        last = now;
                    }*/
                    loading(current, total, total == current);
                }
            };
        }

        public abstract void loading(long current, long total, boolean done);

    }

    public interface ResponseCallback<T> {

        void onSuccess(Request request, Response response, T result, int code);

        void onFailure(Request request, IOException e);

    }

    public interface UploadListener<T> extends ResponseCallback<T> {

        void onUploading(long current, long total, boolean done);

    }

    public interface DownloadListener {

        void onProgress(long progress, long total);

        void onCompleted();

        void onFailure(Exception e);

    }

}
