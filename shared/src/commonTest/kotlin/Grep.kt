//import org.kodein.mock.Mock
//import org.kodein.mock.tests.TestsWithMocks
//import io.mockative.Mock
//import io.mockative.classOf
//import io.mockative.mock
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


interface Blah {
    fun blahFun(): String
}

class BlahClass : Blah {
    override fun blahFun(): String {
        return "Aye man"
    }

}

fun grep(blah: Blah, lines: List<String>, pattern: String, action: (String) -> Unit): String {
    val regex = pattern.toRegex()
    lines.filter(regex::containsMatchIn)
        .forEach(action)
    return blah.blahFun()
}

fun grep(lines: List<String>, pattern: String, action: (String) -> Unit) {
    val regex = pattern.toRegex()
    lines.filter(regex::containsMatchIn)
        .forEach(action)
}

class GrepTest  {
    //override fun setUpMocks() = injectMocksBeforeTest()

    val mockBlah: Blah = mock<Blah>()


    companion object {
        val sampleData = listOf(
            "123 abc",
            "abc 123",
            "123 ABC",
            "ABC 123"
        )
    }

    @Test
    fun shouldFindMatches() {
        val results = mutableListOf<String>()
        every { mockBlah.blahFun() } returns "Aye man"
        val aye = grep(mockBlah, sampleData, "[a-z]+") {
            results.add(it)
        }
        assertEquals(aye, "Aye man")
        assertEquals(2, results.size)
        for (result in results) {
            assertContains(result, "abc")
        }
    }
}
