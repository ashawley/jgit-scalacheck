package org.eclipse.jgit
package api
package test

import org.eclipse.jgit.lib.Ref

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.ScalaCheck

import org.scalacheck.Prop

object CheckoutCommandSpec
    extends Specification
    with CheckoutCommandTestCase
    with Mockito
    with ScalaCheck {

  "CheckoutCommand" >> {

    "call" >> { implicit checkout: CheckoutCommand =>
      checkout.call() must throwA[org.eclipse.jgit.api.errors.InvalidRefNameException]
    }

    "setName(master)" >> { implicit checkout: CheckoutCommand =>
      checkout.setName("master") must beAnInstanceOf[CheckoutCommand]
    }

    "setName(master).call" >> { implicit checkout: CheckoutCommand =>
      checkout.setName("master").call must throwA[org.eclipse.jgit.api.errors.RefNotFoundException]
    }

    "setCreateBranch(true).setName(master).call" >> { implicit checkout: CheckoutCommand =>
      checkout.setCreateBranch(true).setName("master").call must throwA[org.eclipse.jgit.api.errors.RefNotFoundException]
    }

    "setCreateBranch(true).setName(master).call" >> { implicit checkout: CheckoutCommand =>
      checkout.setName("master").call must throwA[org.eclipse.jgit.api.errors.RefNotFoundException]
    }
  }
}
