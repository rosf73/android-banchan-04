package com.woowa.banchan.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.woowa.banchan.R

class AlarmBroadcastReceiver : BroadcastReceiver(), Notifications {

    override fun onReceive(context: Context, intent: Intent) {
        val food = intent.getStringExtra(context.getString(R.string.order_food_name)) ?: BLANK
        val count = intent.getIntExtra(context.getString(R.string.order_food_count), 0) - 1
        val id = intent.getLongExtra(context.getString(R.string.order_id), 0)
        val orderAt = intent.getLongExtra(context.getString(R.string.order_at), 0)
        val description =
            if (count > 0) context.getString(R.string.notification_products, food, count)
            else context.getString(R.string.notification_product, food)

        showDeliveryCompleteNotification(
            context,
            context.getString(R.string.order_done),
            description,
            food,
            count,
            id,
            orderAt,
            "${context.packageName}-${context.getString(R.string.app_name)}",
            context.getString(R.string.detail_ordering)
        )
    }

    companion object {
        const val BLANK = ""
    }
}