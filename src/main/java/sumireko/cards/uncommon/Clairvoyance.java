package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.ClairvoyanceAction;
import sumireko.util.CardInfo;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class Clairvoyance extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Clairvoyance",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int UPG_COST = 0;

    private static final int BUFF = 1;

    public Clairvoyance() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(BUFF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new AttackChoice(this.magicNumber));
        choices.add(new SkillChoice(this.magicNumber));
        choices.add(new PowerChoice(this.magicNumber));
        choices.add(new OtherChoice(this.magicNumber));

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

        public AttackChoice(int buff) {
            super(cardInfo, false);

            this.misc = buff;
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

        public SkillChoice(int buff) {
            super(cardInfo, false);

            this.misc = buff;
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

        public PowerChoice(int buff) {
            super(cardInfo, false);

            this.misc = buff;
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

        public OtherChoice(int buff) {
            super(cardInfo, false);

            this.misc = buff;
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