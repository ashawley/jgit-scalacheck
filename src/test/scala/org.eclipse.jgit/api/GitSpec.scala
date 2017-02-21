package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.test.RepositoryTestCase

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

object GitSpec
    extends Specification
    with RepositoryTestCase
    with Mockito {

  "Git" >> {

    "init" >> {
      Git.init() must beAnInstanceOf[InitCommand]
    }

    "commit" >> { implicit repository: Repository =>
      new Git(repository).commit() must beAnInstanceOf[CommitCommand]
    }

    "merge" >> { implicit repository: Repository =>
      new Git(repository).merge() must beAnInstanceOf[MergeCommand]
    }

    "add" >> { implicit repository: Repository =>
      new Git(repository).add() must beAnInstanceOf[AddCommand]
    }
  }
}
