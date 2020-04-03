package `in`.ac.bits_hyderabad.swd.swd.user.fragment

import `in`.ac.bits_hyderabad.swd.swd.APIConnection.ConnectData
import `in`.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService
import `in`.ac.bits_hyderabad.swd.swd.R
import `in`.ac.bits_hyderabad.swd.swd.helper.PersonAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrgConnectFragment(orgToDisplay: Int) : Fragment() {

    companion object {
        val TYPE_SWD = 200
        val TYPE_SUC = 201
        val TYPE_CRC = 202
        val TYPE_SMC = 203
        val TYPE_EC = 204
    }

    var type: Int = orgToDisplay

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var peopleToDisplay: ArrayList<`in`.ac.bits_hyderabad.swd.swd.APIConnection.Person>
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var retrofitClient: Retrofit
    private lateinit var retrofitService: GetDataService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_connect_org, container, false)

        retrofitClient = Retrofit.Builder().baseUrl(getString(R.string.URL)).addConverterFactory(GsonConverterFactory.create()).build()
        retrofitService = retrofitClient.create(GetDataService::class.java)

        swipeRefresh = rootView.findViewById(R.id.swipeRefreshConnect)
        swipeRefresh.isRefreshing = true

        peopleToDisplay = ArrayList()
        recyclerView = rootView.findViewById(R.id.rvConnect)
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        adapter = PersonAdapter(peopleToDisplay, activity)
        recyclerView.adapter = adapter
        getContacts()

        swipeRefresh.setOnRefreshListener {
            peopleToDisplay.clear()
            getContacts()
        }

        return rootView
    }

    private lateinit var call: Call<ConnectData>

    fun getContacts() {
        call = retrofitService.getConnectData("get_contacts")

        call.enqueue(object : Callback<ConnectData> {

            override fun onResponse(call: Call<ConnectData>, response: Response<ConnectData>) {
                response.body()?.data?.forEach {
                    when (type) {
                        TYPE_SWD -> if (it.heading?.contains(" Student Welfare Division Nucleus", true) == true) peopleToDisplay.add(it)
                        TYPE_SUC -> if (it.heading?.contains("Students' Union Council", true) == true) peopleToDisplay.add(it)
                        TYPE_CRC -> if (it.heading?.contains("Corroboration and Review Committee", true) == true) peopleToDisplay.add(it)
                        TYPE_SMC -> if (it.heading?.contains("Student Mess Council", true) == true) peopleToDisplay.add(it)
                        TYPE_EC -> if (it.heading?.contains("Election Commission", true) == true) peopleToDisplay.add(it)
                    }
                }
                peopleToDisplay.sortBy {
                    it.order?.toInt()
                }
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<ConnectData>, t: Throwable) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false
                t.printStackTrace()
            }

        })
    }

}
