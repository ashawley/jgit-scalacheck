package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.test.RepositoryProvider

import org.specs2.specification.ForEach
import org.specs2.execute.AsResult
import org.specs2.execute.Result

trait InitCommandTestCase
    extends ForEach[InitCommand] {

  def foreach[R: AsResult](f: InitCommand => R): Result = {
    AsResult(f(new InitCommand))
  }
}
