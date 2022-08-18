package com.woowa.banchan.domain.exception

class ExistCartException(message: String) : Exception(message) {
    constructor() : this("There is an item in the cart")
}