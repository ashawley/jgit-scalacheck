package org.eclipse.jgit
package lib
package test

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.RepositoryBuilder
import org.eclipse.jgit.test.scalacheck.JgitTestHarness

trait RepositoryProvider
    extends JgitTestHarness {

  def withRepository[T](f: Repository => T): T = {

    sbt.io.IO.withTemporaryDirectory { tmpDir =>

      val builder = new RepositoryBuilder
      builder.setWorkTree(tmpDir)
      builder.build()

      val repo = new internal.storage.file.FileRepository(builder)
      repo.create(true)

      f(repo)
    }
  }
}

