package tw.teng.hw3.util

import org.junit.Assert.*
import org.junit.Test
import tw.teng.hw3.util.AppUtils.Companion.checkAccount
import tw.teng.hw3.util.AppUtils.Companion.getChatTable
import tw.teng.hw3.util.AppUtils.Companion.getOtherUser

class AppUtilsTest {

    @Test
    fun `test checkAccount`() {
        for (i in 1..8)
            assertTrue(checkAccount("User$i"))
    }

    @Test
    fun `test checkAccount case 2`() {
        assertFalse(checkAccount("User0"))
        assertFalse(checkAccount("User9"))
        assertFalse(checkAccount("User"))
        assertFalse(checkAccount("Uer0"))
        assertFalse(checkAccount("UserA"))
    }

    @Test
    fun `test getOtherUser`() {
        assertEquals("User2", getOtherUser("User1"))
        assertEquals("User1", getOtherUser("User2"))
        assertEquals("User3", getOtherUser("User4"))
        assertEquals("User4", getOtherUser("User3"))
        assertEquals("User5", getOtherUser("User6"))
        assertEquals("User6", getOtherUser("User5"))
        assertEquals("User7", getOtherUser("User8"))
        assertEquals("User8", getOtherUser("User7"))
        assertEquals("User0", getOtherUser("User9"))
    }

    @Test
    fun `test getChatTable`() {
        assertEquals("Chat1", getChatTable("User1"))
        assertEquals("Chat1", getChatTable("User2"))
        assertEquals("Chat2", getChatTable("User3"))
        assertEquals("Chat2", getChatTable("User4"))
        assertEquals("Chat3", getChatTable("User5"))
        assertEquals("Chat3", getChatTable("User6"))
        assertEquals("Chat4", getChatTable("User7"))
        assertEquals("Chat4", getChatTable("User8"))
        assertEquals("Chat0", getChatTable("User9"))
    }
}