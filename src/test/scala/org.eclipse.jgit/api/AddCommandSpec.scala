package org.eclipse.jgit
package api
package test

import org.scalacheck.Prop
import org.scalacheck.Prop.BooleanOperators

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck

object AddCommandSpec
    extends Specification
    with AddCommandTestCase
    with ScalaCheck {

  "AddCommand" >> {

    "addFilepattern(str)" >> { implicit add: AddCommand =>
      prop { (str: String) =>
        add.addFilepattern(str) must beAnInstanceOf[AddCommand]
      }
    }

    // Pending, see [1]
    "addFilepattern(str).call" >> { implicit add: AddCommand =>

      prop { (str: String) =>

        Prop.atLeastOne(

          (str.isEmpty) ==>
            Prop.throws(classOf[java.lang.IllegalArgumentException]) {
              add.addFilepattern(str).call() must throwA
            },

          (!str.isEmpty) ==> {

            Prop.throws(classOf[java.io.IOException]) {
              val repository = add.getRepository
              val file = new java.io.File(repository.getWorkTree, str)
              file.createNewFile

            // 1. Disk repository isn't thread safe?
            } || Prop.throws(classOf[java.lang.IllegalStateException]) {
              val repository = add.getRepository
              val file = new java.io.File(repository.getWorkTree, str)
              file.createNewFile
              add.addFilepattern(str).call()

            } || Prop.protect {

              val repository = add.getRepository

              val file = new java.io.File(repository.getWorkTree, str)
              file.createNewFile

              val dirCache = add.addFilepattern(str).call()
              dirCache must beAnInstanceOf[dircache.DirCache]
              dirCache.getEntryCount must beEqualTo(1)
              dirCache.getEntry(0).getPathString must beEqualTo(str)
              dirCache.findEntry(str) must be_>=(0)
            }
          }
        )
      }
    }.pendingUntilFixed("throws(IllegalStateException)") // FIXME

    "call.throws(Exception)" >> { implicit add: AddCommand =>
      add.call() must throwA[org.eclipse.jgit.api.errors.NoFilepatternException]
    }
  }
}
