package com.enplus.network

import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.google.gson.Gson
import okhttp3.*
import okio.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

class Https @JvmOverloads constructor(var url: String? = null) {

    companion object {
        private var mClient: OkHttpClient? = null
        private const val TYPE_JSON = "application/x-www-form-urlencoded; charset=utf-8"

        init { // 设置网络拦截器，是因为有些数据来源会重定向
            mClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS) //设置超时时间
                .readTimeout(15, TimeUnit.SECONDS) //设置读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS) //设置写入超时时间
                .build()
        }
    }

    private var mParam: String? = null
    private var contentType: String? = null
    private var mHeaders: MutableMap<String?, String>? = null
    private var mParams: MutableMap<String, String>? = null
    private val mUploadFiles: List<UploadData>? = null
    private val responseOnUiThread = true
    private val print = false
    private var mCall: Call? = null

    fun addHeader(key: String?, value: String): Https {
        if (TextUtils.isEmpty(key)) {
            return this
        }
        if (mHeaders == null) {
            mHeaders = HashMap()
        }
        mHeaders!![key] = value
        return this
    }

    fun addHeaders(headers: Map<String?, String>?): Https {
        if (mHeaders == null) {
            mHeaders = HashMap()
        }
        mHeaders!!.putAll(headers!!)
        return this
    }

    fun url(url: String?): Https {
        this.url = url
        return this
    }

    fun setParam(param: String?, contentType: String?): Https {
        mParam = param
        this.contentType = contentType
        return this
    }

    fun setParam(params: MutableMap<String, String>?): Https {
        mParams = params
        return this
    }

    val param: Map<String, String>?
        get() = mParams

    fun addParam(key: String, value: Int): Https {
        return addParam(key, value.toString())
    }

    fun addParam(key: String, value: String): Https {
        if (mParams == null) {
            mParams = HashMap()
        }
        mParams!![key] = value
        return this
    }

    fun addParams(params: Map<String, String>?): Https {
        if (mParams == null) {
            mParams = HashMap()
        }
        mParams!!.putAll(params!!)
        return this
    }

    private val okHttpClient: OkHttpClient
        get() = mClient!!.newBuilder().build()

    // 你不就是想获得主线程的handler发消息吗，用得着这么绕？
    private val mainHandler: Handler
        private get() = Handler(Looper.getMainLooper()) // 你不就是想获得主线程的handler发消息吗，用得着这么绕？

    private fun buildGetRequest(): Request {
        val builder = defaultRequestBuilder
        if (mParams != null && !mParams!!.isEmpty()) {
            val upon = Uri.parse(url).buildUpon()
            for (me in mParams!!.entries) {
                val key = me.key
                var value = me.value
                if (TextUtils.isEmpty(key)) {
                    continue
                }
                if (value == null) {
                    value = ""
                }
                upon.appendQueryParameter(key, value)
            }
            builder.url(upon.toString())
        } else {
            builder.url(url)
        }
        appendHeadersIfExsit(builder)
        return builder.build()
    }

    private fun buildPostRequest(): Request {
        val builder = defaultRequestBuilder
        builder.url(url)
        if (mParams != null && !mParams!!.isEmpty()) {
            val bodyBuilder = FormBody.Builder()
            for (me in mParams!!.entries) {
                val key = me.key
                var value = me.value
                if (TextUtils.isEmpty(key)) {
                    continue
                }
                if (value == null) {
                    value = ""
                }
                bodyBuilder.add(key, value)
            }
            builder.post(bodyBuilder.build())
        } else {
            val mediaType =
                MediaType.parse(if (contentType == null) TYPE_JSON else contentType)
            val body =
                RequestBody.create(mediaType, if (mParam == null) "" else mParam)
            builder.post(body)
        }
        appendHeadersIfExsit(builder)
        return builder.build()
    }

    private fun buildUploadRequest(listener: UploadListener<*>?): Request {
        val builder = defaultRequestBuilder
        builder.url(url)
        val bodyBuilder = MultipartBody.Builder().setType(
            MultipartBody.FORM
        )
        if (mParams != null && !mParams!!.isEmpty()) {
            for (me in mParams!!.entries) {
                val key = me.key
                var value = me.value
                if (TextUtils.isEmpty(key)) {
                    continue
                }
                if (value == null) {
                    value = ""
                }
                bodyBuilder.addFormDataPart(key, value)
            }
        }
        if (mUploadFiles != null && !mUploadFiles.isEmpty()) {
            var counter = 0
            for (data in mUploadFiles) {
                val rBody = RequestBody.create(
                    MediaType.parse("application/octet-stream"),
                    data.file
                )
                if (TextUtils.isEmpty(data.name)) {
                    data.name = "file_" + counter++
                }
                if (TextUtils.isEmpty(data.fileName)) {
                    data.fileName = getFileName(data.file!!.path)
                }
                bodyBuilder.addFormDataPart(data.name, data.fileName, rBody)
            }
        }
        builder.post(object : UploadRequestBody(bodyBuilder.build()) {
            override fun loading(
                current: Long,
                total: Long,
                done: Boolean
            ) {
                listener?.onUploading(current, total, done)
            }
        })
        appendHeadersIfExsit(builder)
        return builder.build()
    }

    private fun getFileName(path: String): String {
        if (!TextUtils.isEmpty(path)) {
            val index = path.lastIndexOf("/")
            if (index > 0) {
                return path.substring(index + 1, path.length)
            }
        }
        return ""
    }

    private fun buildDownloadRequest(): Request {
        val builder = defaultRequestBuilder
        builder.url(url)
        appendHeadersIfExsit(builder)
        return builder.build()
    }

    private fun appendHeadersIfExsit(builder: Request.Builder) {
        if (mHeaders != null && !mHeaders!!.isEmpty()) {
            for ((key, value) in mHeaders!!) {
                builder.addHeader(key, value)
            }
        }
    }

    private val defaultRequestBuilder: Request.Builder
        private get() = Request.Builder()
            .addHeader("Connection", "keep-alive")
            .addHeader("phoneModel", Build.MODEL)
            .addHeader("systemVersion", Build.VERSION.RELEASE)
            .addHeader("appVersion", "3.2.0")

    @Throws(IOException::class)
    private fun execute(request: Request): Response {
        val client = okHttpClient
        mCall = okHttpClient.newCall(request)
        return mCall!!.execute()
    }

    private fun <T> enqueue(request: Request, callback: ResponseCallback<T?>) {
        val client = okHttpClient
        mCall = client.newCall(request)
        mCall!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handleException(call, e, callback)
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                handleResponse(call, response, callback)
            }
        })
    }

    private fun <T> handleResponse(
        call: Call?,
        response: Response,
        callback: ResponseCallback<T?>?
    ) {
        var printResult: String? = null
        if (call != null && callback != null) {
            val request = call.request()
            val code = response.code()
            try {
                val genType =
                    callback.javaClass.genericInterfaces[0]
                val type: Type
                type = if (genType is ParameterizedType) {
                    genType.actualTypeArguments[0]
                } else {
                    String::class.java
                }
                val result = convert(response, type as Class<T>)
                if (print && result != null) {
                    printResult = result.toString()
                }
                postResult(Runnable { callback.onSuccess(request, response, result, code) })
            } catch (e: IOException) {
                postResult(Runnable { callback.onFailure(request, e) })
            }
        }
        if (print) {
            printHttpResponse(call, response, printResult)
        }
    }

    @Throws(IOException::class)
    private fun <T> convert(response: Response, c: Class<T>): T? {
        return if (c == InputStream::class.java) {
            response.body().byteStream() as T
        } else {
            val result = response.body().string()
            if (c == String::class.java) {
                result as T
            } else {
                try {
                    Gson().fromJson(result, c)
                } catch (ignore: Exception) {
                    null
                }
            }
        }
    }

    private fun <T> handleException(
        call: Call,
        e: IOException,
        callback: ResponseCallback<T?>?
    ) {
        if (callback != null) {
            val request = call.request()
            postResult(Runnable { callback.onFailure(request, e) })
        }
    }

    private fun postResult(r: Runnable?) {
        if (r == null) {
            return
        }
        if (responseOnUiThread && Looper.myLooper() != Looper.getMainLooper()) {
            mainHandler.post(r)
        } else {
            r.run()
        }
    }

    private fun printHttpResponse(
        call: Call?,
        response: Response,
        result: String?
    ) {
        val request = call!!.request()
        val sb = StringBuilder()
        sb.append("  ")
        sb.append("\n")
        sb.append("----start----start----start-----start-----start----start-----start----")
            .append("\n")
        sb.append(request.method()).append("\n")
        sb.append(request.url().toString()).append("\n")
        if (!TextUtils.isEmpty(mParam)) {
            sb.append(mParam).append("\n")
        }
        if (mParams != null) {
            for (me in mParams!!.entries) {
                var value = me.value
                if (value != null && value.length > 1024) {
                    value = "too long,so substring it  :::  " + value.substring(0, 99)
                }
                sb.append(me.key).append("=").append(value).append("\n")
            }
        }
        sb.append(response.code()).append("\n")
        try {
            sb.append(JSONObject(result).toString(4)).append("\n")
        } catch (e: Exception) {
            sb.append(result).append("\n")
        }
        sb.append("----end-----end-----end-----end-----").append("\n\n")
    }

    private fun printHttpException(call: Call, e: IOException) {
        val request = call.request()
        val sb = StringBuilder()
        sb.append("  ")
        sb.append("\n")
        sb.append("----start----start----start-----start-----start----start-----start----")
            .append("\n")
        sb.append(request.method()).append("\n")
        sb.append(request.url().toString()).append("\n")
        if (!TextUtils.isEmpty(mParam)) {
            sb.append(mParam).append("\n")
        }
        if (mParams != null) {
            for (me in mParams!!.entries) {
                var value = me.value
                if (value != null && value.length > 1024) {
                    value = "too long,so substring it  :::  " + value.substring(0, 99)
                }
                sb.append(me.key).append("=").append(value).append("\n")
            }
        }
        sb.append(e.message).append("\n")
        sb.append("----end-----end-----end-----end-----").append("\n\n")
    }

    @Throws(IOException::class)
    fun get(): Response {
        return execute(buildGetRequest())
    }

    @Throws(IOException::class)
    fun post(): Response {
        return execute(buildPostRequest())
    }

    @Throws(IOException::class)
    fun upload(): Response {
        return execute(buildUploadRequest(null))
    }

    fun <T> get(callBack: ResponseCallback<T?>) {
        enqueue(buildGetRequest(), callBack)
    }

    fun <T> post(callBack: ResponseCallback<T?>) {
        enqueue(buildPostRequest(), callBack)
    }

    fun <T> upload(listener: UploadListener<*>) {
        enqueue(buildUploadRequest(listener), listener)
    }

    fun <T> download(
        saveFile: File,
        listener: DownloadListener?
    ) {
        val request = buildDownloadRequest()
        val client = okHttpClient
        mCall = client.newCall(request)
        mCall!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postDownloadFailure(listener, e)
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                handleDownloadResponse(saveFile, response, listener)
            }
        })
    }

    private fun handleDownloadResponse(
        saveFile: File,
        response: Response?,
        listener: DownloadListener?
    ) {
        if (response == null || !response.isSuccessful) {
            postDownloadFailure(
                listener,
                IOException("response is null or resoonse is failure.code = " + response?.code())
            )
            return
        }
        if (!saveFile.parentFile.exists()) {
            saveFile.parentFile.mkdirs()
        }
        if (saveFile.exists()) {
            saveFile.delete()
        }
        var out: FileOutputStream? = null
        val total = response.body().contentLength()
        try {
            saveFile.createNewFile()
            val `in` = response.body().byteStream()
            out = FileOutputStream(saveFile)
            val buf = ByteArray(8192)
            var len: Int
            var progress: Long = 0
            while (`in`.read(buf).also { len = it } != -1) {
                out.write(buf, 0, len)
                progress += len.toLong()
                postDownloadProgress(listener, progress, total)
            }
            out.close()
            postDownloadCompleted(listener)
        } catch (e: IOException) {
            postDownloadFailure(listener, e)
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (ignore: IOException) {
                }
            }
        }
    }

    private fun postDownloadCompleted(listener: DownloadListener?) {
        if (listener != null) {
            postResult(Runnable { listener.onCompleted() })
        }
    }

    private fun postDownloadFailure(
        listener: DownloadListener?,
        e: Exception
    ) {
        if (listener != null) {
            postResult(Runnable { listener.onFailure(e) })
        }
    }

    private fun postDownloadProgress(
        listener: DownloadListener?,
        progress: Long,
        total: Long
    ) {
        if (listener != null) {
            postResult(Runnable { listener.onProgress(progress, total) })
        }
    }

    fun cancel() {
        if (mCall != null) {
            mCall!!.cancel()
        }
    }

    private inner class UploadData {
        var name: String? = null
        var fileName: String? = null
        var file: File? = null
    }

    private abstract inner class UploadRequestBody internal constructor(private val original: RequestBody) :
        RequestBody() {
        private var bufferedSink: BufferedSink? = null
        override fun contentType(): MediaType {
            return original.contentType()
        }

        @Throws(IOException::class)
        override fun contentLength(): Long {
            return original.contentLength()
        }

        @Throws(IOException::class)
        override fun writeTo(sink: BufferedSink) {
            if (bufferedSink == null) {
                bufferedSink = Okio.buffer(sink(sink))
            }
            original.writeTo(bufferedSink)
            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink!!.flush()
        }

        private fun sink(sink: Sink): Sink {
            return object : ForwardingSink(sink) {
                private var current: Long = 0
                private var total: Long = 0
                //private int last = 0;
                @Throws(IOException::class)
                override fun write(
                    source: Buffer,
                    byteCount: Long
                ) {
                    super.write(source, byteCount)
                    if (total == 0L) {
                        total = contentLength()
                    }
                    current += byteCount
                    //int now = (int) (current * 100 / total);
/*if (last < now) {
                        loading(last, 100, total == current);
                        last = now;
                    }*/loading(current, total, total == current)
                }
            }
        }

        abstract fun loading(
            current: Long,
            total: Long,
            done: Boolean
        )

    }

    interface ResponseCallback<T> {
        fun onSuccess(
            request: Request?,
            response: Response?,
            result: T?,
            code: Int
        )

        fun onFailure(request: Request?, e: IOException?)
    }

    interface UploadListener<T> : ResponseCallback<T> {
        fun onUploading(
            current: Long,
            total: Long,
            done: Boolean
        )
    }

    interface DownloadListener {
        fun onProgress(progress: Long, total: Long)
        fun onCompleted()
        fun onFailure(e: Exception?)
    }

}