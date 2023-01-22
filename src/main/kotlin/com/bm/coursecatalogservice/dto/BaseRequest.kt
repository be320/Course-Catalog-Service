package com.bm.commonlib.dto
import java.util.UUID

open class BaseRequest(open val requestId: String = UUID.randomUUID().toString())