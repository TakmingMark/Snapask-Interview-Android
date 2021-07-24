package com.mark.snapask_interview_android.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mark.snapask_interview_android.databinding.FragmentUserBinding
import com.mark.snapask_interview_android.ui.user.recyclerview.UserAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class UserFragment:Fragment() {
    private lateinit var binding: FragmentUserBinding

    private val viewModel: UserViewModel by viewModel()

    private val userAdapter: UserAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")
        binding = FragmentUserBinding.inflate(layoutInflater)
            .apply {

            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initLiveDataListener()
        initData()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.userRecyclerView.layoutManager = linearLayoutManager
        binding.userRecyclerView.adapter = userAdapter
    }

    private fun initLiveDataListener() {
        viewModel.callUsersDataFailure.observe(viewLifecycleOwner, { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        })

        viewModel.updateUsersData.observe(viewLifecycleOwner, { users ->
            userAdapter.addItems(users)
        })
    }

    private fun initData() {
        viewModel.callUsersData()
    }
}