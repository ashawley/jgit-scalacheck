package org.eclipse.jgit
package api
package test

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.ScalaCheck

import org.scalacheck.Prop

object MergeCommandSpec
    extends Specification
    with MergeCommandTestCase
    with Mockito
    with ScalaCheck {

  "MergeCommand" >> {

    "call" >> { implicit merge: MergeCommand =>
      merge.call() must throwA[org.eclipse.jgit.api.errors.InvalidMergeHeadsException]
    }

    "include(HEAD).call" >> { implicit merge: MergeCommand =>
      val repo = merge.getRepository
      val headRef = repo.exactRef("HEAD")
      merge.include(headRef).call() must throwA[java.lang.NullPointerException]
    }

    "include(Initial commit).call" >> { implicit merge: MergeCommand =>
      val git = new Git(merge.getRepository)
      val msg = "Initial commit"
      val commit = git.commit().setMessage(msg).call()
      commit must beAnInstanceOf[revwalk.RevCommit]
      commit.getFullMessage must beEqualTo(msg)
      val mergeResult = merge.include(commit.getId).call()
      mergeResult must beAnInstanceOf[MergeResult]
      mergeResult.getMergeStatus must beEqualTo(MergeResult.MergeStatus.ALREADY_UP_TO_DATE)
    }

    "include(2nd commit).call" >> { implicit merge: MergeCommand =>
      val repository = merge.getRepository
      val git = new Git(repository)
      val msg = "Initial commit"
      val commit = git.commit().setMessage(msg).call()
      commit must beAnInstanceOf[revwalk.RevCommit]
      commit.getFullMessage must beEqualTo(msg)
      val mergeResult = merge.include(commit.getId).call()
      mergeResult must beAnInstanceOf[MergeResult]
      mergeResult.getMergeStatus must beEqualTo(MergeResult.MergeStatus.ALREADY_UP_TO_DATE)
    }

    "include(addCommit).call" >> { implicit merge: MergeCommand =>
      val repository = merge.getRepository
      val file0 = new java.io.File(repository.getWorkTree, "file0")
      file0.createNewFile
      val git = new Git(repository)
      val revCommit0 = git.commit().setMessage("msg0").call()
      revCommit0 must beAnInstanceOf[revwalk.RevCommit]
      val dirCache0 = git.add.addFilepattern("file0").call
      dirCache0 must beAnInstanceOf[dircache.DirCache]
      val revCommit1 = git.commit().setMessage("msg1").call()
      val ref0 = git.checkout().setStartPoint(revCommit0).setCreateBranch(true).setName("branch0").call()
      ref0 must beAnInstanceOf[lib.Ref]
      val file1 = new java.io.File(repository.getWorkTree, "file1")
      file1.createNewFile
      val dirCache1 = git.add.addFilepattern("file0").call
      dirCache1 must beAnInstanceOf[dircache.DirCache]
      val revCommit2 = git.commit().setMessage("msg2").call()
      revCommit2 must beAnInstanceOf[revwalk.RevCommit]
      val ref1 = git.checkout().setStartPoint("master").setCreateBranch(false).setName("master").call()
      ref1 must beAnInstanceOf[lib.Ref]
      val mergeResult = merge.include(revCommit2).call()
      mergeResult.getMergeStatus must beEqualTo(MergeResult.MergeStatus.MERGED)
    }

    "include(editCommit).call" >> { implicit merge: MergeCommand =>
      val repository = merge.getRepository
      val file = new java.io.File(repository.getWorkTree, "file")
      file.createNewFile
      val git = new Git(repository)
      val revCommit0 = git.commit().setMessage("msg0").call()
      revCommit0 must beAnInstanceOf[revwalk.RevCommit]
      val dirCache = git.add.addFilepattern("file").call
      dirCache must beAnInstanceOf[dircache.DirCache]
      val revCommit1 = git.commit().setMessage("msg1").call()
      val ref0 = git.checkout().setStartPoint(revCommit1).setCreateBranch(true).setName("branch0").call()
      ref0 must beAnInstanceOf[lib.Ref]
      sbt.io.IO.append(file, "line0\n")
      val revCommit2 = git.commit().setMessage("msg2").call()
      revCommit2 must beAnInstanceOf[revwalk.RevCommit]
      val ref1 = git.checkout().setStartPoint("master").setCreateBranch(false).setName("master").call()
      ref1 must beAnInstanceOf[lib.Ref]
      val mergeResult = merge.include(revCommit2).call()
      mergeResult.getMergeStatus must beEqualTo(MergeResult.MergeStatus.FAST_FORWARD)
    }

    "setMessage(str).call" >> { implicit merge: MergeCommand =>
      prop { str: String =>
        merge.setMessage(str).call() must throwA[org.eclipse.jgit.api.errors.InvalidMergeHeadsException]
      }
    }
  }
}
