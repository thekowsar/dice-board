package com.jkr.frontend.frontend1.domain;

import com.jkr.frontend.frontend1.util.Constant;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Player {

    private int order, totalScore, lastScore;
    private String playerName, doseDiceHolder, doseHitSix, sixAndNotFour;

    public String updateScore(int score){
        if(this.doseHitSix.equalsIgnoreCase(Constant.YES)) {
            if(score != 4 && this.getSixAndNotFour().equalsIgnoreCase(Constant.NO)){
                this.setTotalScore(this.getTotalScore() + score);
                this.setSixAndNotFour(Constant.YES);
                if(score == 6){
                    this.setDoseDiceHolder(Constant.YES);
                }
                else {
                    this.setDoseDiceHolder(Constant.NO);
                }
            }
            else if(score != 4 && this.getSixAndNotFour().equalsIgnoreCase(Constant.YES)){
                this.setTotalScore(this.getTotalScore() + score);
                if(score == 6){
                    this.setDoseDiceHolder(Constant.YES);
                }
                else {
                    this.setDoseDiceHolder(Constant.NO);
                }
            }
            else if(score == 4 && this.getSixAndNotFour().equalsIgnoreCase(Constant.NO)){
                this.setDoseDiceHolder(Constant.NO);
                this.setDoseHitSix(Constant.NO);
            }
            else if (score == 4 && this.totalScore > 4){
                this.setTotalScore(this.getTotalScore() - score);
                this.setDoseDiceHolder(Constant.NO);
            }
            else if (score == 4 && this.totalScore < 5){
                this.setTotalScore(0);
                this.setDoseDiceHolder(Constant.NO);
            }
        }
        else if(score == 6) {
            this.setDoseDiceHolder(Constant.YES);
            this.setDoseHitSix(Constant.YES);
        }
        else {
            this.setDoseDiceHolder(Constant.NO);
        }
        return this.getDoseDiceHolder();
    }

    public boolean dosePassWinningScore(int winScore){
        boolean win = false;
        if(this.getTotalScore() >= winScore){
            win = true;
        }
        return win;
    }

}
