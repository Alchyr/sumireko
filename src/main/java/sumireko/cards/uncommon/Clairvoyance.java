package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.ClairvoyanceAction;
import sumireko.actions.EscapeArtistAction;
import sumireko.util.CardInfo;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class Clairvoyance extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Clairvoyance",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;


    public Clairvoyance() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new AttackChoice(this.block));
        choices.add(new SkillChoice(this.block));
        choices.add(new PowerChoice(this.block));
        choices.add(new OtherChoice(this.block));

        addToBot(new ChooseOneAction(choices));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Clairvoyance();
    }


    private static class AttackChoice extends BaseCard {
        private final static CardInfo cardInfo = new CardInfo(
                "AttackChoice",
                -2,
                CardType.SKILL,
                CardTarget.NONE,
                CardRarity.SPECIAL);

        private static final String ID = makeID(cardInfo.cardName);

        public AttackChoice(int block) {
            super(cardInfo, false);

            this.misc = block;
        }

        @Override
        public void onChoseThisOption() {
            addToTop(new DrawCardAction(1, new ClairvoyanceAction(CardType.ATTACK, this.misc)));
        }

        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) { }

        @Override
        public AbstractCard makeCopy() {
            return new AttackChoice(this.misc);
        }
    }
    private static class SkillChoice extends BaseCard {
        private final static CardInfo cardInfo = new CardInfo(
                "SkillChoice",
                -2,
                CardType.SKILL,
                CardTarget.NONE,
                CardRarity.SPECIAL);

        private static final String ID = makeID(cardInfo.cardName);

        public SkillChoice(int block) {
            super(cardInfo, false);

            this.misc = block;
        }

        @Override
        public void onChoseThisOption() {
            addToTop(new DrawCardAction(1, new ClairvoyanceAction(CardType.SKILL, this.misc)));
        }

        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) { }

        @Override
        public AbstractCard makeCopy() {
            return new AttackChoice(this.misc);
        }
    }
    private static class PowerChoice extends BaseCard {
        private final static CardInfo cardInfo = new CardInfo(
                "PowerChoice",
                -2,
                CardType.SKILL,
                CardTarget.NONE,
                CardRarity.SPECIAL);

        private static final String ID = makeID(cardInfo.cardName);

        public PowerChoice(int block) {
            super(cardInfo, false);

            this.misc = block;
        }

        @Override
        public void onChoseThisOption() {
            addToTop(new DrawCardAction(1, new ClairvoyanceAction(CardType.POWER, this.misc)));
        }

        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) { }

        @Override
        public AbstractCard makeCopy() {
            return new AttackChoice(this.misc);
        }
    }
    private static class OtherChoice extends BaseCard {
        private final static CardInfo cardInfo = new CardInfo(
                "OtherChoice",
                -2,
                CardType.SKILL,
                CardTarget.NONE,
                CardRarity.SPECIAL);

        private static final String ID = makeID(cardInfo.cardName);

        public OtherChoice(int block) {
            super(cardInfo, false);

            this.misc = block;
        }

        @Override
        public void onChoseThisOption() {
            addToTop(new DrawCardAction(1, new ClairvoyanceAction(this.misc)));
        }

        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) { }

        @Override
        public AbstractCard makeCopy() {
            return new AttackChoice(this.misc);
        }
    }
}