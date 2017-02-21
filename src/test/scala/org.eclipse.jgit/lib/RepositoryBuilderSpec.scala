package org.eclipse.jgit
package lib
package test

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.ScalaCheck
import org.scalacheck.Prop
import org.scalacheck.Properties
import org.scalacheck.Prop.AnyOperators
import org.scalacheck.Prop.BooleanOperators

object RepositoryBuilderSpec
    extends Specification
    with Mockito
    with ScalaCheck {

  def mkTempDir = sbt.io.IO.createTemporaryDirectory

  "RepositoryBuilder" >> {

    "build" >> {
      val builder = new RepositoryBuilder
      builder.build() must throwA[java.lang.IllegalArgumentException]
    }

    "setBare.build" >> {
      val builder = new RepositoryBuilder
      builder.setBare
      builder.build() must throwA[java.lang.IllegalArgumentException]
    }

    "setWorkTree.getWorkTree" >> {
      val builder = new RepositoryBuilder
      val file = mock[java.io.File]
      builder.setWorkTree(file).getWorkTree must beEqualTo(file)
    }

    "setWorkTree.build" >> {
      val builder = new RepositoryBuilder
      val file = mock[java.io.File]
      builder.setWorkTree(file)
      builder.build() must throwA[java.lang.NullPointerException]
    }
  }
}
