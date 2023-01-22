package com.bm.commonlib.dto

data class BaseResponse<T,E>(var status: Boolean = true,
                           var error: E? = null,
                           var response: T? = null)
