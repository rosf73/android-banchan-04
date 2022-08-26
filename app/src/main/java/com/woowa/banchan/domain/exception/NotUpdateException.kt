package com.woowa.banchan.domain.exception

class NotUpdateException(message: String) : Exception(message) {
    constructor() : this("Can not update item")
}
