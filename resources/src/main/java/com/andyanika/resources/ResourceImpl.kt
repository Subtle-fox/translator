package com.andyanika.resources

import android.content.Context
import com.andyanika.translator.common.interfaces.Resources
import javax.inject.Inject
import javax.inject.Named

class ResourceImpl @Inject constructor(@Named("activity") private val activityContext: Context) :
    Resources {
    override fun getString(resourceId: Int): String {
        return activityContext.getString(resourceId)
    }

    override fun getString(resourceName: String): String {
        val id = activityContext.resources.getIdentifier(resourceName, "string", activityContext.packageName)
        return getString(id)
    }
}
