package ph.codeia.gcmnmsyncing.main;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

public class BestTimeService extends GcmTaskService {
    public static final String TAG = BestTimeService.class.getSimpleName();

    @Override
    public int onRunTask(TaskParams taskParams) {
        String taskId = taskParams.getExtras().getString(CodelabUtil.TASK_ID);
        boolean completed = CodelabUtil.makeNetworkCall();

        Log.d(TAG, String.format("oneoff scheduled call executed with id %s", taskId));

        Intent taskUpdateIntent = new Intent(CodelabUtil.TASK_UPDATE_FILTER);
        taskUpdateIntent.putExtra(CodelabUtil.TASK_ID, taskId);
        TaskItem taskItem = CodelabUtil.getTaskItemFromFile(this, taskId);
        if (taskItem == null) {
            return GcmNetworkManager.RESULT_FAILURE;
        }
        String status = completed ? TaskItem.EXECUTED_STATUS : TaskItem.FAILED_STATUS;
        taskItem.setStatus(status);
        taskUpdateIntent.putExtra(CodelabUtil.TASK_STATUS, status);
        CodelabUtil.saveTaskItemToFile(this, taskItem);

        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this);
        bm.sendBroadcast(taskUpdateIntent);
        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
