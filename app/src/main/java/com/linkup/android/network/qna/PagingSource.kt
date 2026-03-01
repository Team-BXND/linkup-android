package com.linkup.android.network.qna

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.linkup.android.network.Category


class PopularPagingSource(
    private val service: QnaService,
    private val category: Category
) : PagingSource<Int, QnaItemResponse>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, QnaItemResponse> {

        val page = params.key ?: 0

        return try {

            val response = service.popular(page)
            val body = response.body()
                ?: return LoadResult.Error(Exception())

            val filtered = if (category == Category.ALL) {
                body.data
            } else {
                body.data.filter { it.category == category }
            }

            LoadResult.Page(
                data = filtered,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (body.meta.hasNext) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, QnaItemResponse>
    ): Int? = state.anchorPosition
}