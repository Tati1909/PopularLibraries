package com.example.popularlibraries.base.resourcesprovider

import android.content.Context
import javax.inject.Inject

class ResourcesProviderImpl @Inject constructor(
    private val context: Context
) : ResourcesProvider {

    override fun getString(resourcesId: Int): String {
        return context.getString(resourcesId)
    }
}