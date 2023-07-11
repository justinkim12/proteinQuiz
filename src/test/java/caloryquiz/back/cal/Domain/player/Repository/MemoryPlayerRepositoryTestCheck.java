//package caloryquiz.back.cal.Domain.player.Repository;
//
//import caloryquiz.back.cal.Domain.player.Player;
//import caloryquiz.back.cal.Domain.player.Repository.MemoryPlayerRepository;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//class MemoryPlayerRepositoryTestCheck {
//    private MemoryPlayerRepository memoryPlayerRepository= new MemoryPlayerRepository();
//    @Test
//    void saveAndFind() {
//        Player player1 = new Player("a@a.com", 0,1,new ArrayList<>());
//        Player player2 = new Player("b@a.com", 0,1,new ArrayList<>());
//        Player player3 = new Player("c@a.com", 0, 1, new ArrayList<>());
//
//        memoryPlayerRepository.save(player1);
//        memoryPlayerRepository.save(player2);
//        memoryPlayerRepository.save(player3);
//
//        assertThat(memoryPlayerRepository.findPlayerByKey(0L).get().getNickName()).isEqualTo("a@a.com");
//
//    }
//}