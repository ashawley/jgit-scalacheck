package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.test.Person

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.ScalaCheck

import org.scalacheck.Prop

object CommitCommandSpec
    extends Specification
    with CommitCommandTestCase
    with Mockito
    with ScalaCheck {

  "CommitCommand" >> {

    val systemReader = util.SystemReader.getInstance
    val now: Int = (systemReader.getCurrentTime / 1000).toInt

    "call" >> { implicit commit: CommitCommand =>
      commit.call() must throwA[org.eclipse.jgit.api.errors.NoMessageException]
    }

    "getAuthor" >> { implicit commit: CommitCommand =>
      commit.getAuthor must beNull // beEqualTo(Person.author)
    }

    "getCommitter" >> { implicit commit: CommitCommand =>
      commit.getCommitter must beNull // beEqualTo(Person.author)
    }

    "getMessage" >> { implicit commit: CommitCommand =>
      commit.getMessage must beNull // beEqualTo(Person.author)
    }

    "setAuthor(mock)" >> { implicit commit: CommitCommand =>
      val person = mock[PersonIdent]
      commit.setAuthor(person).getAuthor must beEqualTo(person)
    }

    "setCommitter(mock)" >> { implicit commit: CommitCommand =>
      val person = mock[PersonIdent]
      commit.setCommitter(person).getCommitter must beEqualTo(person)
    }

    "setMessage(str)" >> prop { msg: String =>
      { implicit commit: CommitCommand =>
        commit.setMessage(msg).getMessage must beEqualTo(msg)
      }
    }

    "setMessage(str).call" >> prop { msg: String =>
      { implicit commit: CommitCommand =>
        val revCommit = commit.setMessage(msg).call()
        revCommit must beAnInstanceOf[revwalk.RevCommit]
        revCommit.getFullMessage must beEqualTo(msg)
        revCommit.getCommitTime must beEqualTo(now)

      }
    }

    "add.setMessage(str).call" >> prop { (msg: String) =>
      { implicit commit: CommitCommand =>
        val repository = commit.getRepository
        val file = new java.io.File(repository.getWorkTree, "file")
        file.createNewFile
        new Git(repository).add.addFilepattern("file").call
        val revCommit = commit.setMessage(msg).call()
        revCommit must beAnInstanceOf[revwalk.RevCommit]
      }
    }

    "add.commit.edit.commit" >> { implicit commit: CommitCommand =>
      val repository = commit.getRepository
      val file = new java.io.File(repository.getWorkTree, "file")
      file.createNewFile
      val git = new Git(repository)
      val dirCache = git.add.addFilepattern("file").call
      dirCache must beAnInstanceOf[dircache.DirCache]
      commit.setMessage("msg0").call() must beAnInstanceOf[revwalk.RevCommit]
      val commit2 = git.commit()
      sbt.io.IO.append(file, "line0")
      commit2.setMessage("msg1").call() must beAnInstanceOf[revwalk.RevCommit]
    }
  }
}
