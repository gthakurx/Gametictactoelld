package strategy.botPlayingStrategy;

import models.BotDifficultyLevel;

public class BotPLayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel difficultyLevel){
        return switch (difficultyLevel){
            case EASY -> new EasyBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
            case MEDIUM -> new MediumBotPLayingStrategy();
        }
    }
}
