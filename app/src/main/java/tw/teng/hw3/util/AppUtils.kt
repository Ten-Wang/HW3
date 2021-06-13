package tw.teng.hw3.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.regex.Pattern

class AppUtils {
    companion object {
        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun checkAccount(resource: String): Boolean {
            if(resource.length != 5)
                return false
            val p: Pattern = Pattern.compile("^User[1-8]")
            return p.matcher(resource).find()
        }


        fun getOtherUser(user: String): String {
            val pairNumber = when (user[user.length - 1].digitToInt()) {
                1 -> 2
                2 -> 1
                3 -> 4
                4 -> 3
                5 -> 6
                6 -> 5
                7 -> 8
                8 -> 7
                else -> 0
            }
            return "User$pairNumber"
        }

        fun getChatTable(user: String): String {
            val chatTable = when (user[user.length - 1].digitToInt()) {
                1 -> 1
                2 -> 1
                3 -> 2
                4 -> 2
                5 -> 3
                6 -> 3
                7 -> 4
                8 -> 4
                else -> 0
            }
            return "Chat$chatTable"
        }
    }
}