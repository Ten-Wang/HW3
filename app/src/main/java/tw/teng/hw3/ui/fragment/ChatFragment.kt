package tw.teng.hw3.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.teng.hw3.databinding.ChatFragmentBinding
import tw.teng.hw3.model.Chat
import tw.teng.hw3.network.APIService
import tw.teng.hw3.network.Client
import tw.teng.hw3.ui.MainViewModel
import tw.teng.hw3.util.AppUtils


class ChatFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: ChatFragmentBinding

    private lateinit var apiService: APIService
    private lateinit var user: String
    private lateinit var other: String

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        user = requireArguments().getString("userName").toString()
        other = AppUtils.getOtherUser(user)
        viewModel.loginInit(user, other)

        binding.btnSend.setOnClickListener {
            val time = System.currentTimeMillis()
            val message = binding.editSend.text.toString()
            viewModel.sendEvent(user, other, message, time)
        }
        // init View
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ChatRVAdapter(arrayListOf(), user)

        recyclerView.adapter = adapter
        apiService =
            Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)

        viewModel.sendEvent.observe(viewLifecycleOwner, {
            if (it) {
                binding.editSend.setText("")
                AppUtils.hideKeyboard(activity as Activity)
            }
        })

        viewModel.exceptionEvent.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    context,
                    "No in my predict, pls tell me or try again late.",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        })
        viewModel.updateRV.observe(viewLifecycleOwner, {
            updateRecyclerView(it as MutableList<Chat>)
        })
    }

    private fun updateRecyclerView(chats: MutableList<Chat>) {
        val adapter: ChatRVAdapter = binding.recyclerView.adapter as ChatRVAdapter
        adapter.addMessage(
            chats
        ).notifyDataSetChanged()
        binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
    }
}