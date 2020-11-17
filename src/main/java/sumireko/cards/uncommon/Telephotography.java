package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sumireko.abstracts.BaseCard;
import sumireko.actions.TelephotographyAction;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class Telephotography extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Telephotography",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public Telephotography() {
        super(cardInfo, true);

        setExhaust(true);
        setMagic(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new TelephotographyAction(randomAttacks(this.magicNumber)));
        }
        else
        {
            AbstractCard c = randomAttack();

            if (!c.hasTag(CustomCardTags.FINAL))
            {
                OccultFields.isOccult.set(c, true);
                c.initializeDescription();
            }

            addToBot(new MakeTempCardInHandAction(c));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Telephotography();
    }

    private static AbstractCard randomAttack()
    {
        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet())
        {
            if (c.getValue().type == CardType.ATTACK &&
                (!UnlockTracker.isCardLocked(c.getKey()) ||
                 Settings.treatEverythingAsUnlocked())
            )
                anyCard.addToBottom(c.getValue());

        }

        anyCard.shuffle(AbstractDungeon.cardRng);
        return anyCard.getRandomCard(true).makeCopy();
    }
    private static ArrayList<AbstractCard> randomAttacks(int amount)
    {
        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet())
        {
            if (c.getValue().type == CardType.ATTACK &&
                    (!UnlockTracker.isCardLocked(c.getKey()) ||
                            Settings.treatEverythingAsUnlocked())
            )
                anyCard.addToBottom(c.getValue());

        }

        anyCard.shuffle(AbstractDungeon.cardRng);

        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < amount && !anyCard.isEmpty())
        {
            AbstractCard c = anyCard.getRandomCard(true).makeCopy();

            cards.add(c);
            anyCard.removeCard(c);
        }
        return cards;
    }
}