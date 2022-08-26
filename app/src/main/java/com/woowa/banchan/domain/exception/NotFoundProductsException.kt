package com.woowa.banchan.domain.exception

class NotFoundProductsException(message: String) : Exception(message) {
    constructor() : this("Can not find products")
}
