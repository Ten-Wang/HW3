package tw.teng.hw3.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.teng.hw3.R
import tw.teng.hw3.model.Chat
import java.text.SimpleDateFormat
import java.util.*

class ChatRVAdapter internal constructor(
    private var itemList: MutableList<Chat>,
    private val user: String
) :
    RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {

    companion object {
        const val MSG_TYPE_LEFT = 0
        const val MSG_TYPE_RIGHT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            MSG_TYPE_LEFT -> {
                val inflater =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.chat_item_left, parent, false)
                ViewHolder(inflater)
            }
            else -> {
                val inflater =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.chat_item_right, parent, false)
                ViewHolder(inflater)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].uid.equals(user)) {
            true -> {
                MSG_TYPE_RIGHT
            }
            else -> {
                MSG_TYPE_LEFT
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MSG_TYPE_LEFT -> viewHolder.bindData(itemList, position)
            else -> viewHolder.bindData(itemList, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addMessage(listItems: MutableList<Chat>): ChatRVAdapter {
        itemList = listItems
        return this
    }

    fun appendMessage(listItems: ArrayList<Chat>): ChatRVAdapter {
        itemList.addAll(listItems)
        return this
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var showMessage: TextView = itemView.findViewById(R.id.show_message)
        var profileImage: ImageView = itemView.findViewById(R.id.profile_image)
        var txtSeen: TextView = itemView.findViewById(R.id.txt_seen)
        var timeTv: TextView = itemView.findViewById(R.id.time_tv)
        fun convertTime(time: String): String {
            val formatter = SimpleDateFormat("h:mm a")
            return formatter.format(Date(time.toLong()))
        }

        fun bindData(itemList: MutableList<Chat>, position: Int) {
            showMessage.text = itemList[position].message
        }
    }
}