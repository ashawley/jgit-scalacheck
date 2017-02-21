package org.eclipse.jgit
package test
package scalacheck

import org.eclipse.jgit.util.SystemReader

trait JgitTestHarness {
  SystemReader.setInstance(TestSuiteSystemReader.getInstance)
}
