import org.junit.Test

import org.junit.Assert.*
import ru.netology.ChatService

class ChatServiceTest {
    val chatService = ChatService

    @Test
    fun addMessages() {
        val result = chatService.add("Malov", "Batrakov", "Text1")
        // проверяем результат (используйте assertTrue или assertFalse)
        //assertTrue(result)

    }
}