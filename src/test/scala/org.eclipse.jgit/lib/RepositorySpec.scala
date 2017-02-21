package org.eclipse.jgit
package lib
package test

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

object RepositorySpec
    extends Specification
    with RepositoryTestCase
    with Mockito {

  "Repository" >> {

    "create" >> {
      val file = mock[java.io.File]
      val repo = mock[Repository]
      repo.create must beEqualTo((): Unit)
    }

    "getDirectory" >> { implicit repository: Repository =>
      val mockRepo = mock[Repository]
      mockRepo.getDirectory must beNull
      val dir = repository.getDirectory
      dir.getPath must startWith(repository.getWorkTree.toString)
      dir.getName must beEqualTo(".git")
    }

    "getWorkTree" >> { implicit repository: Repository =>
      val mockRepo = mock[Repository]
      mockRepo.getWorkTree must beNull
      val dir = repository.getWorkTree
      dir.getName must not be equalTo(".git")
    }
  }
}
