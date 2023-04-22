package io.github.muqhc.gamepaper.dependency

import org.apache.maven.repository.internal.MavenRepositorySystemUtils
import org.eclipse.aether.DefaultRepositorySystemSession
import org.eclipse.aether.RepositorySystem
import org.eclipse.aether.RepositorySystemSession
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.collection.CollectRequest
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.impl.DefaultServiceLocator
import org.eclipse.aether.repository.LocalRepository
import org.eclipse.aether.repository.RemoteRepository
import org.eclipse.aether.resolution.DependencyRequest
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory
import org.eclipse.aether.spi.connector.transport.TransporterFactory
import org.eclipse.aether.transport.file.FileTransporterFactory
import org.eclipse.aether.transport.http.HttpTransporterFactory
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.logging.Logger

class DependencyLoadingSystem(
    val dir: File = File("libraries"),
    val repositorySystem: RepositorySystem = newRepositorySystem(),
    val repositorySystemSession: RepositorySystemSession =
        newRepositorySystemSession(dir ,repositorySystem),
    val logger: Logger = Logger.getAnonymousLogger(),
) {

    fun load(urlClassLoader: URLClassLoader, repositoryInfoList: List<RepositoryInfo>, dependencyTextList: List<String>) {
        if (repositoryInfoList.isEmpty() || dependencyTextList.isEmpty()) return

        val dependencies = dependencyTextList.map { Dependency(DefaultArtifact(it),null) }
        val repositories = repositorySystem.newResolutionRepositories(repositorySystemSession,repositoryInfoList.map {
            RemoteRepository.Builder(it.id,"default",it.url).build()
        })

        val result = repositorySystem.resolveDependencies(repositorySystemSession,
            DependencyRequest(
                CollectRequest(null as Dependency?, dependencies, repositories), null
            )
        )

        result.artifactResults.map { it.artifact.file.toURI().toURL() }.forEach {
            urlClassLoader.loadLibrary(it)
            logger.info("Loaded Library ${it.file}")
        }
    }

    companion object {
        private fun newRepositorySystemSession(dir: File, system: RepositorySystem): DefaultRepositorySystemSession {
            val session = MavenRepositorySystemUtils.newSession()
            val localRepo = LocalRepository(dir)
            session.localRepositoryManager = system.newLocalRepositoryManager(session, localRepo)
            return session
        }

        private fun newRepositorySystem(): RepositorySystem {
            val locator = MavenRepositorySystemUtils.newServiceLocator()
            locator.addService(RepositoryConnectorFactory::class.java, BasicRepositoryConnectorFactory::class.java)
            locator.addService(TransporterFactory::class.java, FileTransporterFactory::class.java)
            locator.addService(TransporterFactory::class.java, HttpTransporterFactory::class.java)
            return locator.getService(RepositorySystem::class.java)
        }

        private fun URLClassLoader.loadLibrary(url: URL) {
            javaClass.getDeclaredMethod("addURL",URL::class.java).invoke(this,url)
        }
    }
}