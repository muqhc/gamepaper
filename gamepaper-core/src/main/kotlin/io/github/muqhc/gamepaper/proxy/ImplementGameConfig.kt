package io.github.muqhc.gamepaper.proxy

import io.github.muqhc.gamepaper.config.GameConfig
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

fun implementGameConfig(configInterface: Class<out GameConfig>, propValueMap: Map<String,Any?>) =
    Proxy.newProxyInstance(configInterface.classLoader, arrayOf(configInterface)) { obj, method, args ->
        propValueMap[method.name]
            ?:
                if (method.isDefault) InvocationHandler.invokeDefault(obj, method, args)
                else
                    if (method.name == "toString") "{Proxy Instance : $configInterface}"
                    else null
    }.let { configInterface.cast(it) }