package io.github.muqhc.gamepaper.loader

import java.io.File
import java.net.URLClassLoader
import java.util.concurrent.ConcurrentHashMap

class GameClassLoader(
    private val loader: GameLoader,
    file: File,
    parent: ClassLoader
) :
    URLClassLoader(arrayOf(file.toURI().toURL()), parent) {

    private val classes = ConcurrentHashMap<String, Class<*>>()

    @Throws(ClassNotFoundException::class)
    override fun findClass(name: String): Class<*> {
        return try {
            findLocalClass(name)
        } catch (e: ClassNotFoundException) {
            loader.findClass(name, this)
        }
    }

    internal fun findLocalClass(name: String): Class<*> {
        this.classes[name]?.let { return it }

        val found = super.findClass(name)
        this.classes[name] = found

        return found
    }
}