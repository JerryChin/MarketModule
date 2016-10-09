package okhttp;

/**
 * Created by dzc on 15/12/13.
 */
public interface UploadListener extends okhttp.Callback{
    void onProgress(long totalBytes, long remainingBytes);
}
