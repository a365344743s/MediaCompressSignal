package chengdu.ws.mediacompress.signal;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

public class MainActivity extends AppCompatActivity {
    private TextView infoTV;
    private Button signalMemoryBTN, signalStreamBTN;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        infoTV = findViewById(R.id.tv_info);
        signalMemoryBTN = findViewById(R.id.btn_signal_memory);
        signalStreamBTN = findViewById(R.id.btn_signal_stream);

        PermissionUtils.permissionGroup(PermissionConstants.STORAGE)
                .request();

        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/source.mp4";
        String attachPathSignalMemory = this.getFilesDir() + "/convert/signalMemory.mp4";
        String attachPathSignalStream = this.getFilesDir() + "/convert/signalStream.mp4";
        long upperSizeLimit = 100 * 1024 * 1024;
        signalMemoryBTN.setOnClickListener(view -> {
            if (!org.thoughtcrime.securesms.util.MemoryFileDescriptor.supported()) {
                infoTV.setText("Signal memory failed: MemoryFileDescriptor unsupported!");
                return;
            }
            if (Build.VERSION.SDK_INT < 26) {
                infoTV.setText("Signal memory failed: RequiresApi(26)!");
                return;
            }
            Integer idSignalMemory = org.thoughtcrime.securesms.util.VideoConvertUtil.startVideoConvert(videoPath, attachPathSignalMemory, upperSizeLimit, true, new org.thoughtcrime.securesms.video.MediaController.ConvertorListener() {
                @Override
                public void onConvertProgress(org.thoughtcrime.securesms.video.MediaController.Task task, float progress) {
                    infoTV.setText("Signal memory progress: " + progress);
                }

                @Override
                public void onConvertSuccess(org.thoughtcrime.securesms.video.MediaController.Task task) {
                    infoTV.setText("Signal memory success: " + task.toString());
                }

                @Override
                public void onConvertFailed(org.thoughtcrime.securesms.video.MediaController.Task task) {
                    infoTV.setText("Signal memory failed: " + task.toString());
                }
            });
            if (idSignalMemory == null) {
                infoTV.setText("Signal memory start failed:");
            }
        });
        signalStreamBTN.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT < 26) {
                infoTV.setText("Signal memory failed: RequiresApi(26)!");
                return;
            }
            Integer idSignalStream = org.thoughtcrime.securesms.util.VideoConvertUtil.startVideoConvert(videoPath, attachPathSignalStream, upperSizeLimit, false, new org.thoughtcrime.securesms.video.MediaController.ConvertorListener() {
                @Override
                public void onConvertProgress(org.thoughtcrime.securesms.video.MediaController.Task task, float progress) {
                    infoTV.setText("Signal stream progress: " + progress);
                }

                @Override
                public void onConvertSuccess(org.thoughtcrime.securesms.video.MediaController.Task task) {
                    infoTV.setText("Signal stream success: " + task.toString());
                }

                @Override
                public void onConvertFailed(org.thoughtcrime.securesms.video.MediaController.Task task) {
                    infoTV.setText("Signal stream failed: " + task.toString());
                }
            });
            if (idSignalStream == null) {
                infoTV.setText("Signal stream start failed:");
            }
        });
    }
}
