package com.woowa.banchan.ui.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.woowa.banchan.R
import com.woowa.banchan.domain.entity.DeliveryStatus
import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.usecase.order.ModifyOrderUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DeliveryWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val modifyOrderUseCase: ModifyOrderUseCase
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result {
        val id = inputData.getLong(applicationContext.getString(R.string.order_id), 0)
        val orderAt = inputData.getLong(applicationContext.getString(R.string.order_at), 0)
        if (id > 0) {
            val result = modifyOrderUseCase(Order(id, orderAt, DeliveryStatus.DONE)).first()
            val isSuccess = result.getOrElse { return Result.retry() }
            return when (isSuccess) {
                1 -> Result.success()
                else -> Result.retry()
            }
        } else {
            return Result.failure()
        }
    }
}