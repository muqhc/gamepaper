package io.github.muqhc.gamepaper.exception

class InvalidConfigException: Exception {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String,cause: Throwable): super(message,cause)
}