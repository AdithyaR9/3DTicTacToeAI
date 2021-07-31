package TicTacToeAI;

public class Move implements PlayerInt {

    private char player;
    private String name;

    public Move(char letter) {
        name = "AdithyaR9";
        player = letter;
    }


    @Override
    public char getLetter() {
        return player;
    }

    @Override
    public Location getMove(Board board) {
        int max = -1;
        int oppMax = -1;
        Location best = null;
        Location oppBest = null;

        for (int s = 0; s < 4; s++)
            for (int r = 0; r < 4; r++)
                for (int c = 0; c < 4; c++) {
                    Location loc = new Location(c, r, s);
                    if (board.isEmpty(loc)) {
                        Counts counts = new Counts(board, loc, player);
                        int score = counts.getMy1s() + counts.getMy2s() * 70 + counts.getMy3s() * 100 + counts.getMy4s() * 1000;
                        int oppScore = counts.getOpp1s() + counts.getOpp2s() * 10 + counts.getOpp3s() * 100 + counts.getOpp4s() * 1000;
//                        System.out.println(score);
//                        System.out.println(loc);
                        if (score > max) {
                            max = score;
                            best = loc;
                        }
                        if (oppScore > max && oppScore >= 1) {
                            max = oppScore;
                            best = loc;
                        }
                    }
                }

        return best;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerInt freshCopy() {
        return new Move(player);
    }
}


