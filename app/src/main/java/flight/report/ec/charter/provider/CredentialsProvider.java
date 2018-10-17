package flight.report.ec.charter.provider;

import android.content.Context;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

public class CredentialsProvider {
    private Context context;
    /**
     * No publicar Key, Secret en repositorios publicos como Github.
     * Amazon lo detecta y bloquea la cuenta
     **/
    private String KEY = "YOUR_AMAZON_KEY";
    private String SECRET = "YOUR_AMAZON_SECRET";

    private static String AMAZON_REGION = "us-east-2";
    private static String AMAZON_PATH = "https://s3." + AMAZON_REGION + ".amazonaws.com";
    public static String AMAZON_BUCKET = "YOUR_AMAZON_BUCKET";
    private static String SEPARATOR = "/";
    public static final String AMAZON_ROOT = AMAZON_PATH + SEPARATOR + AMAZON_BUCKET + SEPARATOR;

    public CredentialsProvider(Context context) {
        this.context = context;
    }

    /**
     * Credenciales para conectarse con Amazon S3
     **/
    public TransferUtility credentialsProvider() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(KEY, SECRET);
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        return TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();
    }

}
