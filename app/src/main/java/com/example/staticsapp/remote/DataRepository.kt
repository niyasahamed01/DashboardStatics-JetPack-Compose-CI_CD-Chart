package com.example.staticsapp.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.staticsapp.model.InvoiceApiModel
import com.example.staticsapp.model.InvoiceStatus
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.model.JobStatus

class DataRepository(private val apiDataSource: ApiDataSource) {

    fun observeJobsByStatus(status: JobStatus): Flow<List<JobApiModel>> {
        return apiDataSource.observeJobs()
            .map { jobs -> jobs.filter { it.status == status } }
    }

    fun observeInvoiceByStatus(status: InvoiceStatus): Flow<List<InvoiceApiModel>> {
        return apiDataSource.observeInvoices()
            .map { jobs -> jobs.filter { it.status == status } }
    }
}
