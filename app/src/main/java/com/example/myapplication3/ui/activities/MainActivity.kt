package com.example.myapplication3.ui.activities


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.myapplication3.BaseActivity
import com.example.myapplication3.R
import com.example.myapplication3.adapter.RecyclerViewAdapter
import com.example.myapplication3.databinding.ActivityMainBinding
import com.example.myapplication3.interfaces.OnItemClickListener
import com.example.myapplication3.models.UserModel
import com.example.myapplication3.network.response.JsonArrayResponse
import com.example.myapplication3.network.response.Resource
import com.example.myapplication3.utils.Constants
import com.example.myapplication3.utils.Utils
import retrofit2.Response

class MainActivity : BaseActivity(), OnItemClickListener<UserModel> {

    lateinit var list : ArrayList<UserModel>
    lateinit var binding : ActivityMainBinding
    lateinit var adapter : RecyclerViewAdapter<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

         list = ArrayList()
        // addDataToList()
         setUpRecyclerView()

        
        
        observeViewModel()

        myViewModel.employeeList()


    }


    private fun observeViewModel() {

        myViewModel.employeeListObserver.observe(
            this,
            Observer<Resource<Response<JsonArrayResponse<UserModel>>>> { resource ->
                if (resource.response.code() == Constants.SUCCESS && resource.response.body()!!.status.equals(Constants.STATUS_SUCCESS)) {
                    goToNext(resource.response.body()!!.list!!)
                    list.addAll(resource.response.body()!!.list!!)
                    adapter.notifyDataSetChanged()
                } else {
                    /*                   alert {
                                           message = resource.message
                                           btnPosText = getString(R.string.ok)
                                       }*/
                    onInfo(resource.message + " Code = " + resource.response.code() + " Status = "+resource.response.body()!!.status)
                }
            })
    }

    fun goToNext(list : ArrayList<UserModel>)
    {

    }

    fun setUpRecyclerView()
    {
        adapter = RecyclerViewAdapter(list, this, R.layout.item_temp)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(view: View, `object`: UserModel) {

        when(`object`.employeeName)
        {
            "Tahir" -> {
                Toast.makeText(this, `object`.employeeName, Toast.LENGTH_LONG).show()
            }
            "Zoyab" -> {
                Toast.makeText(this, `object`.employeeName, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addDataToList() {

        var obj1 = UserModel()
        obj1.employeeName = "Tahir"

        var obj2 = UserModel()
        obj2.employeeName = "Aatif"

        var obj3 = UserModel()
        obj3.employeeName = "Shoib"

        var obj4 = UserModel()
        obj4.employeeName = "Moin"

        var obj5 = UserModel()
        obj5.employeeName = "Aadil"

        var obj6 = UserModel()
        obj6.employeeName = "Zoyab"

        var obj7 = UserModel()
        obj7.employeeName = "Samar"

        var obj8 = UserModel()
        obj8.employeeName = "Meer"

        list.add(obj1)
        list.add(obj2)
        list.add(obj3)
        list.add(obj4)
        list.add(obj5)
        list.add(obj6)
        list.add(obj7)
        list.add(obj8)
    }
}