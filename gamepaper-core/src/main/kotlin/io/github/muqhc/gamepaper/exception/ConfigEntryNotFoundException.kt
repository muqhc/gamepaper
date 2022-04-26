package io.github.muqhc.gamepaper.exception

class ConfigEntryNotFoundException: Exception {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String,cause: Throwable): super(message,cause)
}