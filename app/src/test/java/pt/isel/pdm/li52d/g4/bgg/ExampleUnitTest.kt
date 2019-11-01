package pt.isel.pdm.li52d.g4.bgg

import com.google.gson.Gson
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto
import org.junit.Test

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test() {
        val url = "https://www.boardgameatlas.com/api/search?name=Catan&client_id=SB1VGnDv7M"
        val gson = Gson()
        val value = gson.fromJson(BufferedReader(InputStreamReader(URL(url).openStream())),SearchDto::class.java)
        value.games.forEach(::println)
    }
}
