import com.avenza.license.Request
import com.avenza.license.Response
import com.avenza.license.validate
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ValidateKtTest {

    @Test
    fun `run code`() = runTest {
        val request = object: Request {
            override val device: String
                get() = "Test"
            override val url: String
                get() = "http://127.0.0.1:3000/"
            override val headers: Map<String, String>
                get() = mapOf( "Android" to "User-Agent")

        }
        val result: Response? = validate(request)

        assertEquals(result?.message, "Hello World! Test")
    }
}