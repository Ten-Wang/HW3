package tw.teng.hw3.model

class Chat {
    var uid: String? = null
    var receiver: String? = null
    var message: String? = null
    var isSeen = false
        private set
    var time: Long? = null

    constructor(uid: String?, receiver: String?, message: String?, isseen: Boolean, time: Long?) {
        this.uid = uid
        this.receiver = receiver
        this.message = message
        this.isSeen = isseen
        this.time = time
    }

    constructor()

    fun setSeen(isSeen: Boolean) {
        this.isSeen = isSeen
    }
}