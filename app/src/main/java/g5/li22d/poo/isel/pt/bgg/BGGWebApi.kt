package g5.li22d.poo.isel.pt.bgg


import android.content.Context
import android.util.Log
import android.widget.SearchView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import g5.li22d.poo.isel.pt.bgg.dto.GetGamesDto
import g5.li22d.poo.isel.pt.bgg.dto.SearchDto

const val BGG_GET_GAMES = "https://www.boardgameatlas.com/api/search?name=%S&pretty=true&client_id=SB1VGnDv7M"
const val BGG_MPP = "https://www.boardgameatlas.com/api/search/?order_by=popularity&ascending=false&client_id=SB1VGnDv7M"
const val BGG_PUBLISHER = "https://www.boardgameatlas.com/api/search/?publisher=%s&client_id=SB1VGnDv7M"

class BGGWebApi(ctx: Context) {

    // Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(ctx)
    val gson = Gson()

    fun searchPublisher (
        publisher: String?,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit)
    {
        val url = String.format(BGG_PUBLISHER, publisher)
        // !!!!! ToDo: Students must refactor this code to avoid duplication of the
        //   HTTP request code !!!
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                val dto = gson.fromJson<SearchDto>(response, SearchDto::class.java)
                onSuccess(dto)
            },
            Response.ErrorListener {
                    err -> onError(err)
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


    fun mostPopularGame (
        mostPopularGame: String?,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit)
    {
        val url = String.format(BGG_MPP, mostPopularGame)
        // !!!!! ToDo: Students must refactor this code to avoid duplication of the
        //   HTTP request code !!!
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                val dto = gson.fromJson<SearchDto>(response, SearchDto::class.java)
                onSuccess(dto)
            },
            Response.ErrorListener {
                    err -> onError(err)
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    fun searchGame (
        name: String?,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit)
    {
        val url = String.format(BGG_GET_GAMES, name)
        // !!!!! ToDo: Students must refactor this code to avoid duplication of the
        //   HTTP request code !!!
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                val dto = gson.fromJson<SearchDto>(response, SearchDto::class.java)
                onSuccess(dto)
            },
            Response.ErrorListener {
                    err -> onError(err)
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}
