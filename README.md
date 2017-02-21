## JGit test suite in Scalacheck

Property-based testing of the Git Java library, [JGit](http://eclipse.org/jgit/),
of the [Eclipse Foundation](http://eclipse.org) in Scala.

**Notice**: This test suite should be operating in the system temporary file
system, and not affect the user's Git congiguration or repositories, but
**please use at your own risk**.

```
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

### Getting started

SBT provides a way to run all the tests with `test` or to run one or
more tests with `testOnly` and `testQuick` commands.  More information:

- http://www.scala-sbt.org/0.13/docs/Testing.html

```
$ sbt
> test
[info] AddCommandSpec
[info] 
[info] AddCommand
[info]   + addFilepattern(str)
[info]   * addFilepattern(str).call throws(IllegalStateException). Pending until fixed
[info]   + call.throws(Exception)
[info] 
[info] 
[info] Total for specification AddCommandSpec
[info] Finished in 1 second, 865 ms
[info] 3 examples, 102 expectations, 0 failure, 0 error, 1 pending
[info] 
[info] GitSpec
[info] 
[info] Git
[info]   + init
[info]   + commit
[info]   + merge
[info]   + add
[info] 
[info] 
[info] Total for specification GitSpec
[info] Finished in 1 second, 947 ms
[info] 4 examples, 0 failure, 0 error
[info] 
[info] InitCommandSpec
[info] 
[info] InitCommand
[info]   + call
[info]   + setDirectory(mock).throws(Exception)
[info]   + setDirectory(dir).call
[info] 
[info] 
[info] Total for specification InitCommandSpec
[info] Finished in 1 second, 844 ms
[info] 3 examples, 0 failure, 0 error
[info] 
[info] RepositoryBuilderSpec
[info] 
[info] RepositoryBuilder
[info]   + build
[info]   + setBare.build
[info]   + setWorkTree.getWorkTree
[info]   + setWorkTree.build
[info] 
[info] 
[info] Total for specification RepositoryBuilderSpec
[info] Finished in 600 ms
[info] 4 examples, 0 failure, 0 error
[info] 
[info] RepositorySpec
[info] 
[info] Repository
[info]   + create
[info]   + getDirectory
[info]   + getWorkTree
[info] 
[info] 
[info] Total for specification RepositorySpec
[info] Finished in 1 second, 68 ms
[info] 3 examples, 0 failure, 0 error
[info] 
[info] CheckoutCommandSpec
[info] 
[info] CheckoutCommand
[info]   + call
[info]   + setName(master)
[info]   + setName(master).call
[info]   + setCreateBranch(true).setName(master).call
[info]   + setCreateBranch(true).setName(master).call
[info] 
[info] 
[info] Total for specification CheckoutCommandSpec
[info] Finished in 1 second, 651 ms
[info] 5 examples, 0 failure, 0 error
[info] 
[info] MergeCommandSpec
[info] 
[info] MergeCommand
[info]   + call
[info]   + include(HEAD).call
[info]   + include(Initial commit).call
[info]   + include(2nd commit).call
[info]   + include(addCommit).call
[info]   + include(editCommit).call
[info]   + setMessage(str).call
[info] 
[info] 
[info] Total for specification MergeCommandSpec
[info] Finished in 1 second, 631 ms
[info] 7 examples, 106 expectations, 0 failure, 0 error
[info] 
[info] CommitCommandSpec
[info] 
[info] CommitCommand
[info]   + call
[info]   + getAuthor
[info]   + getCommitter
[info]   + getMessage
[info]   + setAuthor(mock)
[info]   + setCommitter(mock)
[info]   + setMessage(str)
[info]   + setMessage(str).call
[info]   + add.setMessage(str).call
[info]   + add.commit.edit.commit
[info] 
[info] 
[info] Total for specification CommitCommandSpec
[info] Finished in 8 seconds, 916 ms
[info] 10 examples, 307 expectations, 0 failure, 0 error
[info] 
[info] Passed: Total 38, Failed 0, Errors 0, Passed 38, Pending 1
[success] Total time: 11 s, completed Feb 19, 2017 10:02:24 PM```

Since Scalacheck is provided by specs2, the command control of the
test suite supports specs2 options, and not the Scalacheck ones.  For
the available specs2 options, see its documentation:

- http://etorreborre.github.io/specs2/guide/SPECS2-3.8.8/org.specs2.guide.UseScalaCheck.html

Ustomize the Scalacheck test suite by adding a implicit parameter value:

```scala
import org.specs2.scalacheck.Parameters
implicit val params = Parameters(minTestsOk = 20)
```

### Dependencies

- Runs on Scala version 2.11 against
- JGit version 4.6 using
- specs2 version 3.8,
- Scalacheck version 1.13, and
- Mockito version 1.9.

### References

- http://specs2.org
- http://mockito.org
- http://scalacheck.org
- http://docs.oracle.com/javase/7/docs/api/
- http://scala-lang.org/
- http://github.com/centic9/jgit-cookbook
- http://github.com/rtyley/scala-git
- http://github.com/sbt/sbt-git
