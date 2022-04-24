package io.github.muqhc.gamepaper.proxy

import io.github.muqhc.gamepaper.config.GameConfig
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaGetter

val cache = mutableMapOf<Any,MutableMap<String,Any?>>()

fun implementGameConfig(configInterface: Class<out GameConfig>, propValueMap: Map<KProperty1<out GameConfig,*>,Any?>) =
    Proxy.newProxyInstance(configInterface.classLoader, arrayOf(configInterface)) { obj, method, args ->
        val hash = configInterface.toString()
        if (cache[hash]?.containsKey(method.name) == true)
            return@newProxyInstance cache[hash]!![method.name]
        val key = propValueMap.keys.find {
//            println("proxying [ ${method.name} =?= ${it.javaGetter?.name} ]")
            method.name == it.javaGetter?.name
        }
        if (key == null)
            if (method.isDefault) InvocationHandler.invokeDefault(obj,method,args)
            else
                if (method.name == "toString") "{Proxy Instance : $configInterface}"
                else null
        else propValueMap[key].also {
            if (!cache.containsKey(obj))
                cache[hash] = mutableMapOf()
            cache[hash]!![key.name] = it
        }
    }.let { configInterface.cast(it) }