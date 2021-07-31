package TicTacToeAI;

public class Counts {
    private int my4s = 0;
    private int my3s = 0;
    private int my2s = 0;
    private int my1s = 0;
    private int opp1s = 0;
    private int opp2s = 0;
    private int opp3s = 0;
    private int opp4s = 0;
    private Board board;
    private Location loc;
    private char me;
    private char opp;

    public Counts(Board board, Location loc, char me)
    {
        this.board = board;
        this.loc = loc;
        this.me = me;
        opp = (me == 'X') ? 'O':'X';
        calculate();
        calculateOpp();
    }

    public int getMy1s() {
        return my1s;
    }

    public int getMy2s() {
        return my2s;
    }

    public int getMy3s() {
        return my3s;
    }

    public int getMy4s() {
        return my4s;
    }

    public int getOpp1s() {
        return opp1s;
    }

    public int getOpp2s() {
        return opp2s;
    }

    public int getOpp3s() {
        return opp3s;
    }

    public int getOpp4s() {
        return opp4s;
    }

    public void calculate() {
        int c = loc.getCol();
        int r = loc.getRow();
        int s = loc.getSheet();

        char[][][] b = board.getData();

        //Horizontal
        int count = 1;
        for (int cc = 0; cc < 4; cc++) {
            if (b[s][r][cc] == me)
                count++;
            else if (b[s][r][cc] == opp) {
                count = 0;
                break;
            }
        }
        addMe(count);

        count = 1;
        for (int cc = 0; cc < 4; cc++) {
            if (b[s][r][cc] == me)
                count++;
            else if (b[s][r][cc] == opp) {
                count = 0;
                break;
            }
        }
        addOpp(count);


        //vertical
        count = 1;
        for (int rr = 0; rr < 4; rr++) {
            if (b[s][rr][c] == me)
                count++;
            else if (b[s][rr][c] == opp) {
                count = 0;
                break;
            }
        }
        addMe(count);


        //backwards
        count = 1;
        for (int ss = 0; ss < 4; ss++) {
            if (b[ss][r][c] == me)
                count++;
            else if (b[ss][r][c] == opp) {
                count = 0;
                break;
            }
        }
        addMe(count);



        //Z Sheets Diagonal
        if (c == r) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[s][aa][aa] == me)
                    count++;
                else if (b[s][aa][aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }




        //Z Diagonal 2
        if (c + r == 3) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[s][aa][3 - aa] == me)
                    count++;
                else if (b[s][aa][3 - aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        // Y plane D1
        if (c == s) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][r][aa] == me)
                    count++;
                else if (b[aa][r][aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }



        // Y plane D2
        if (c + s == 3) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][r][3 - aa] == me)
                    count++;
                else if (b[aa][r][3 - aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }



        // X plane D1
        if (r == s) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][c] == me)
                    count++;
                else if (b[aa][aa][c] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        // X plane D2
        if (r + s == 3) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][3 - aa][c] == me)
                    count++;
                else if (b[aa][3 - aa][c] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }



        //special 1
        if ((c == 0 && r == 0 && s == 0) || (c == 1 && r == 1 && s == 1) || (c == 2 && r == 2 && s == 2)
                || (c == 3 && r == 3 && s == 3)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][aa] == me)
                    count++;
                else if (b[aa][aa][aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        //special 2
        if ((c == 3 && r == 0 && s == 0) || (c == 2 && r == 1 && s == 1) || (c == 1 && r == 2 && s == 2)
                || (c == 0 && r == 3 && s == 3)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][3 - aa] == me)
                    count++;
                else if (b[aa][aa][3 - aa] == opp) {
                    count = 0;
                    break;
                }
            }

            addMe(count);
        }


        //special 3
        if ((c == 0 && r == 0 && s == 3) || (c == 1 && r == 1 && s == 2) || (c == 2 && r == 2 && s == 1)
                || (c == 3 && r == 3 && s == 0)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][3 - aa] == me)
                    count++;
                else if (b[aa][aa][3 - aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        //special 4
        if ((c == 3 && r == 0 && s == 3) || (c == 2 && r == 1 && s == 2) || (c == 1 && r == 2 && s == 1)
                || (c == 0 && r == 3 && s == 0)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[3 - aa][aa][3 - aa] == me)
                    count++;
                else if (b[3 - aa][aa][3 - aa] == opp) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }
    }

    public void calculateOpp() {
        int c = loc.getCol();
        int r = loc.getRow();
        int s = loc.getSheet();

        char[][][] b = board.getData();

        //Horizontal
        int count = 1;
        for (int cc = 0; cc < 4; cc++) {
            if (b[s][r][cc] == opp)
                count++;
            else if (b[s][r][cc] == me) {
                count = 0;
                break;
            }
        }
        addMe(count);

        count = 1;
        for (int cc = 0; cc < 4; cc++) {
            if (b[s][r][cc] == opp)
                count++;
            else if (b[s][r][cc] == me) {
                count = 0;
                break;
            }
        }
        addOpp(count);


        //vertical
        count = 1;
        for (int rr = 0; rr < 4; rr++) {
            if (b[s][rr][c] == opp)
                count++;
            else if(b[s][rr][c] == me) {
                count = 0;
                break;
            }
        }
        addMe(count);


        //backwards
        count = 1;
        for (int ss = 0; ss < 4; ss++) {
            if (b[ss][r][c] == opp)
                count++;
            else if (b[ss][r][c] == me) {
                count = 0;
                break;
            }
        }
        addMe(count);



        //Z Sheets Diagonal
        if (c == r) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[s][aa][aa] == opp)
                    count++;
                else if (b[s][aa][aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }




        //Z Diagonal 2
        if (c + r == 3) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[s][aa][3 - aa] == opp)
                    count++;
                else if (b[s][aa][3 - aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        // Y plane D1
        if (c == s) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][r][aa] == opp)
                    count++;
                else if (b[aa][r][aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }



        // Y plane D2
        if (c + s == 3) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][r][3 - aa] == opp)
                    count++;
                else if (b[aa][r][3 - aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }



        // X plane D1
        if (r == s) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][c] == opp)
                    count++;
                else if (b[aa][aa][c] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        // X plane D2
        if (r + s == 3) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][3 - aa][c] == opp)
                    count++;
                else if (b[aa][3 - aa][c] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }



        //special 1
        if ((c == 0 && r == 0 && s == 0) || (c == 1 && r == 1 && s == 1) || (c == 2 && r == 2 && s == 2)
                || (c == 3 && r == 3 && s == 3)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][aa] == opp)
                    count++;
                else if (b[aa][aa][aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        //special 2
        if ((c == 3 && r == 0 && s == 0) || (c == 2 && r == 1 && s == 1) || (c == 1 && r == 2 && s == 2)
                || (c == 0 && r == 3 && s == 3)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][3 - aa] == opp)
                    count++;
                else if (b[aa][aa][3 - aa] == me) {
                    count = 0;
                    break;
                }
            }

            addMe(count);
        }


        //special 3
        if ((c == 0 && r == 0 && s == 3) || (c == 1 && r == 1 && s == 2) || (c == 2 && r == 2 && s == 1)
                || (c == 3 && r == 3 && s == 0)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[aa][aa][3 - aa] == opp)
                    count++;
                else if (b[aa][aa][3 - aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }


        //special 4
        if ((c == 3 && r == 0 && s == 3) || (c == 2 && r == 1 && s == 2) || (c == 1 && r == 2 && s == 1)
                || (c == 0 && r == 3 && s == 0)) {
            count = 1;
            for (int aa = 0; aa < 4; aa++) {
                if (b[3 - aa][aa][3 - aa] == opp)
                    count++;
                else if (b[3 - aa][aa][3 - aa] == me) {
                    count = 0;
                    break;
                }
            }
            addMe(count);
        }
    }

    public void addMe(int c)
    {
        if(c == 1)
            my1s++;
        else if(c == 2)
            my2s++;
        else if(c == 3)
            my3s++;
        else if(c == 4)
            my4s++;
    }

    public void addOpp(int c)
    {
        if(c == 1)
            opp1s++;
        else if(c == 2)
            opp2s++;
        else if(c == 3)
            opp3s++;
        else if(c == 4)
            opp4s++;
    }
}
