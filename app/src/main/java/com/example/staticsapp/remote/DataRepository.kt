package com.example.staticsapp.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.staticsapp.model.InvoiceApiModel
import com.example.staticsapp.model.InvoiceStatus
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.model.JobStatus
import zuper.dev.android.dashboard.data.remote.ApiDataSource

class DataRepository(private val apiDataSource: ApiDataSource) {

    /**
     * This API returns jobs in realtime using which stats can be computed
     */
    fun observeJobs(): Flow<List<JobApiModel>> {
        return apiDataSource.observeJobs()
    }

    /**
     * This API returns invoices in realtime using which stats can be computed
     */
    fun observeInvoices(): Flow<List<InvoiceApiModel>> {
        return apiDataSource.observeInvoices()
    }

    /**
     * This API returns random jobs every time invoked
     */
    fun getJobs(): List<JobApiModel> {
        // TODO - Update as per listing page expectations
        return listOf()
    }

    fun observeJobsByStatus(status: JobStatus): Flow<List<JobApiModel>> {
        return apiDataSource.observeJobs()
            .map { jobs -> jobs.filter { it.status == status } }
    }

    fun observeInvoiceByStatus(status: InvoiceStatus): Flow<List<InvoiceApiModel>> {
        return apiDataSource.observeInvoices()
            .map { jobs -> jobs.filter { it.status == status } }
    }
}
