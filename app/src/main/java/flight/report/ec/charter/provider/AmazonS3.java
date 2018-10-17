package flight.report.ec.charter.provider;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.model.CannedAccessControlList;

import java.io.File;

import flight.report.ec.charter.utils.StringUtil;

public class AmazonS3 {
    private CredentialsProvider credentials;
    private TransferUtility transferUtility;
    private CallbackInterface mCallback;
    public interface CallbackInterface {
        void onS3UploadCompleted(String filePath, String amazonURL);
    }

    public AmazonS3(Context context) {
        credentials = new CredentialsProvider(context);
        transferUtility = credentials.credentialsProvider();
        mCallback = (CallbackInterface) context;
    }

    public AmazonS3(Context context, Fragment fragment) {
        credentials = new CredentialsProvider(context);
        transferUtility = credentials.credentialsProvider();
        mCallback = (CallbackInterface) fragment;
    }

    /**
     * Subir un archivo al servicio Amazon S3
     * @param filePath: Ruta del archivo
     * @param fileName: Nombre del archivo (unico)
     **/
    public void uploadImg(final String filePath, final String fileName) {
        TransferObserver uploadObserver =
                transferUtility.upload(
                        CredentialsProvider.AMAZON_BUCKET,
                        fileName,
                        new File(filePath),
                        CannedAccessControlList.PublicRead);

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                StringUtil.console("STATE", state);
                StringUtil.console("COMPLETED", TransferState.COMPLETED);

                if (TransferState.COMPLETED == state) {
                    if (mCallback != null) {
                        mCallback.onS3UploadCompleted(filePath, CredentialsProvider.AMAZON_ROOT + fileName);
                    }
                }
                if (TransferState.CANCELED == state || TransferState.FAILED == state || TransferState.UNKNOWN == state) {
                    if (mCallback != null) {
                        mCallback.onS3UploadCompleted(filePath, null);
                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                // Show Spinner or dialogMessage
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                StringUtil.console("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                StringUtil.console("onError", ex.toString());
                if (mCallback != null) {
                    mCallback.onS3UploadCompleted(filePath, null);
                }
            }
        });
    }

}
