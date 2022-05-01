package ru.mirea.kryazhin.workmanager

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit


class UploadWorker(@NonNull context: Context, @NonNull workerParams: WorkerParameters?) :
    Worker(context, workerParams!!) {
    @NonNull
    override fun doWork(): Result {
        Log.d(UploadWorker.Companion.TAG, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(UploadWorker.Companion.TAG, "doWork: end")
        return Result.success()
    }

    companion object {
        const val TAG = "UploadWorker"
    }
}