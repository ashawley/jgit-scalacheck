package org.eclipse.jgit
package lib
package test

import org.eclipse.jgit.lib.Repository

import org.specs2.specification.ForEach
import org.specs2.execute.AsResult
import org.specs2.execute.Result

trait RepositoryTestCase
    extends ForEach[Repository]
    with RepositoryProvider {

  def foreach[R: AsResult](f: Repository => R): Result = {

    withRepository { repository => 

      AsResult(f(repository))
    }
  }
}
