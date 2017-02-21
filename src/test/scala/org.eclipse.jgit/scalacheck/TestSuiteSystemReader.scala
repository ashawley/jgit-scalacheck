package org.eclipse.jgit
package test
package scalacheck

import org.eclipse.jgit.test.Person
import org.eclipse.jgit.lib.Config
import org.eclipse.jgit.util.FS
import org.eclipse.jgit.storage.file.FileBasedConfig

object TestSuiteSystemReader {

  val tmpConfigFile =
    java.io.File.createTempFile("jgit-test", ".gitconfig")

  val gitConfig = {

    val config = new Config
    config.setString("user", null, "name", Person.author.getName)
    config.setString("user", null, "email", Person.author.getEmailAddress)

    new FileBasedConfig(
      config,
      tmpConfigFile,
      FS.DETECTED
    )
  }

  val getInstance = {

    val mockSystemReader = new junit.MockSystemReader {
      override def openUserConfig(config: Config, fs: FS): FileBasedConfig =
        gitConfig
    }

    mockSystemReader.setProperty(
      lib.Constants.GIT_CEILING_DIRECTORIES_KEY,
      sbt.io.IO.temporaryDirectory.toString
    )

    mockSystemReader
  }
}
