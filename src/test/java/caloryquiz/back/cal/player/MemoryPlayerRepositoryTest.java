package caloryquiz.back.cal.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryPlayerRepositoryTest {
    private MemoryPlayerRepository memoryPlayerRepository= new MemoryPlayerRepository();
    @Test
    void save() {
        Player player1 = new Player("a@a.com", 0, 1L, new ArrayList<>());
        Player player2 = new Player("b@a.com", 0, 1L, new ArrayList<>());
        Player player3 = new Player("c@a.com", 0, 1L, new ArrayList<>());

        memoryPlayerRepository.save(player1);
        memoryPlayerRepository.save(player2);
        memoryPlayerRepository.save(player3);

        assertThat(memoryPlayerRepository.findPlayerByKey(0L).get().getEmail()).isEqualTo("a@a.com");

    }
}