import org.scalatest.FunSuite

class MainTest extends FunSuite {
  test("passes") {
    assert(true === true)
  }
  test("fails") {
    assert("actual" === "expected")
  }
}
