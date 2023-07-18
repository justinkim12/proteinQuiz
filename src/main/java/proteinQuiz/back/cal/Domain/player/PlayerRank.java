package proteinQuiz.back.cal.Domain.player;

public class PlayerRank extends PlayerOutcome{
    private Integer rank;

    public PlayerRank(String nickName, Integer score,Integer rank) {
        super(nickName, score);
        this.rank = rank;
    }

    public PlayerRank(PlayerOutcome outcome, Integer rank) {
        super(outcome.getNickName(), outcome.getScore());
        this.rank = rank;
    }

    public Integer getRank() {
        return rank;
    }
}
