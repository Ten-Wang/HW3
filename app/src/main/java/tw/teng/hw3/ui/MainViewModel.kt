package tw.teng.hw3.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import tw.teng.hw3.model.Chat
import tw.teng.hw3.util.AppUtils
import tw.teng.hw3.util.SingleLiveEvent

open class MainViewModel :
    ViewModel() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var token: String
    private lateinit var token2: String
    private lateinit var chatTable: String

    private val _loginEvent = SingleLiveEvent<Boolean>()
    val loginEvent: LiveData<Boolean> = _loginEvent

    private val _sendEvent = MutableLiveData<Boolean>()
    val sendEvent: LiveData<Boolean> = _sendEvent

    private val _exception = MutableLiveData<Boolean>()
    val exceptionEvent: LiveData<Boolean> = _exception

    private val _updateRV = MutableLiveData<List<Chat>>()
    val updateRV: LiveData<List<Chat>> = _updateRV

    fun login() {
        _loginEvent.value = isLoginSuccess()
    }

    private fun isLoginSuccess(): Boolean {
        // prepare for future login feature
        return true
    }


    fun loginInit(user: String, otherUser: String) {
        if (otherUser == "User0") {
            _exception.value = true
            return
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user)

            val hashMap = HashMap<String, String>()
            hashMap["id"] = user
            hashMap["token"] = token

            databaseReference.setValue(hashMap)
                .addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        Log.i(TAG, "task isSuccessful")
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                    databaseReference.child(otherUser).get().addOnSuccessListener {
                        try {
                            token2 = (it.value as HashMap<*, *>)["token"].toString()
                        } catch (e: Exception) {

                        }

                        chatTable = AppUtils.getChatTable(user)

                        databaseReference = FirebaseDatabase.getInstance().getReference(chatTable)
                        databaseReference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val chats = ArrayList<Chat>()
                                for (snapshot in dataSnapshot.children) {
                                    val chat = snapshot.getValue(Chat::class.java)
                                    chats.add(chat!!)
                                }
                                _updateRV.value = chats
                            }

                            override fun onCancelled(databaseError: DatabaseError) {}
                        })
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                }
        })
    }

    fun sendEvent(user: String, otherUser: String, message: String, time: Long) {
        _sendEvent.value = sentMessage(user, otherUser, message, time)
    }

    private fun sentMessage(user: String, otherUser: String, message: String, time: Long): Boolean {
        return isSentMessageSuccess(user, otherUser, message, time)
    }

    private fun isSentMessageSuccess(
        user: String,
        otherUser: String,
        message: String,
        time: Long
    ): Boolean {
        if (message.isEmpty())
            return false

        // send notification
//        apiService.sendNotification(Sender(Data(), token2))
//            .enqueue(object : Callback<MyResponse>, retrofit2.Callback<MyResponse> {
//                override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
//                }
//            })

        val reference = FirebaseDatabase.getInstance().reference
        val chat = Chat(user, otherUser, message, false, time)
        reference.child(chatTable).push().setValue(chat)
        return true
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
