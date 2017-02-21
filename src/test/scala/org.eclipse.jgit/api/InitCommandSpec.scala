package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.test.RepositoryTestCase

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.ScalaCheck

import org.scalacheck.Prop

object InitCommandSpec
    extends Specification
    with RepositoryTestCase
    with Mockito
    with ScalaCheck {

  "InitCommand" >> {

    val init = new InitCommand

    "call" >> {
      init.call() must beAnInstanceOf[Git]
    }

    "setDirectory(mock).throws(Exception)" >> { implicit repository: Repository =>
      val mockDir = mock[java.io.File]
      init.setDirectory(mockDir).call() must throwA[java.lang.NullPointerException]
    }

    "setDirectory(dir).call" >> { implicit repository: Repository =>
      val dir = repository.getWorkTree
      init.setDirectory(dir).call() must beAnInstanceOf[Git]
    }
  }
}
