package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.test.RepositoryProvider

import org.specs2.specification.ForEach
import org.specs2.execute.AsResult
import org.specs2.execute.Result

trait AddCommandTestCase
    extends ForEach[AddCommand]
    with RepositoryProvider {

  def foreach[R: AsResult](f: AddCommand => R): Result = {

    withRepository { repository: Repository =>

      AsResult(f(new AddCommand(repository) {
        // Exception: java.lang.IllegalStateException:
        // Command org.eclipse.jgit.api.AddCommand was
        // called in the wrongstate
        // protected override def checkCallable(): Unit = {}
      }))
    }
  }
}
