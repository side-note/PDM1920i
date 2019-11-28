package pt.isel.pdm.li52d.g4.bgg


import android.content.Context
import android.os.AsyncTask
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto

const val BGG_GET_GAMES = "https://www.boardgameatlas.com/api/search?name=%S&limit=%d&skip=%d&pretty=true&client_id=SB1VGnDv7M"
const val BGG_MPP = "https://www.boardgameatlas.com/api/search/?order_by=%S&limit=%d&skip=%d&ascending=false&client_id=SB1VGnDv7M"
const val BGG_PUBLISHER = "https://www.boardgameatlas.com/api/search/?publisher=%s&limit=%d&skip=%d&client_id=SB1VGnDv7M"
const val BGG_ARTIST ="https://www.boardgameatlas.com/api/search/?artist=%s&limit=%d&skip=%d&client_id=SB1VGnDv7M"

class BGGWebApi(ctx: Context) {
    val queue = Volley.newRequestQueue(ctx)
    val gson = Gson()

    fun search (
        name: String?,
        limit: Int?,
        skip: Int?,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String)
    {
        val url = String.format(url, name, limit, skip)

        class MyTask: AsyncTask<String, Int, SearchDto>() {
            override fun doInBackground(vararg resp: String): SearchDto {
                return gson.fromJson(resp[0], SearchDto::class.java)
            }
            override fun onPostExecute(result: SearchDto) {
                onSuccess(result)
            }
        }
        val task =  MyTask()

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                task.execute(response)
            },
            Response.ErrorListener { err -> onError(err)})
        queue.add(stringRequest)
    }

}
