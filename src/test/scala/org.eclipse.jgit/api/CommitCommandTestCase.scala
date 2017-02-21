package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.test.RepositoryProvider

import org.specs2.specification.ForEach
import org.specs2.execute.AsResult
import org.specs2.execute.Result

trait CommitCommandTestCase
    extends ForEach[CommitCommand]
    with RepositoryProvider {

  def foreach[R: AsResult](f: CommitCommand => R): Result = {

    withRepository { repository: Repository =>

      val git = new Git(repository)

      AsResult(f(git.commit()))
    }
  }
}
