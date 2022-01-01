package sumireko.actions.general;

import basemod.Pair;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.SumirekoMod;
import sumireko.patches.MultiGroupGridSelectPatches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class MultiGroupMoveAction extends AbstractGameAction {
    private static final int EXHAUST_PILE_X, EXHAUST_PILE_Y;

    static {
        EXHAUST_PILE_X = (int) (Settings.WIDTH - 40.0F * Settings.scale);
        EXHAUST_PILE_Y = (int) (200 * Settings.scale);
    }


    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("sumireko:MultiGroupMoveAction").TEXT;

    private final CardGroup destination;
    private final ArrayList<CardGroup> sources;
    private final AbstractPlayer p;

    private final HashMap<AbstractCard, CardGroup> cardSourceMap = new HashMap<>();

    private final Predicate<AbstractCard> canSelect;

    boolean first;

    public MultiGroupMoveAction(CardGroup.CardGroupType destination, int amount, CardGroup.CardGroupType... source) {
        this(destination, amount, (c)->true, source);
    }

    public MultiGroupMoveAction(CardGroup.CardGroupType destination, int amount, Predicate<AbstractCard> canSelect, CardGroup.CardGroupType... source) {
        this.amount = amount;
        this.p = AbstractDungeon.player;

        this.actionType = ActionType.CARD_MANIPULATION;
        this.destination = getGroup(destination);

        sources = new ArrayList<>();
        for (CardGroup.CardGroupType groupType : source) {
            sources.add(getGroup(groupType));
        }

        this.canSelect = canSelect;

        first = true;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
            return;
        }
        if (first) {
            first = false;

            if (sources.isEmpty() || destination == null) {
                this.isDone = true;
                return;
            }

            CardGroup selectGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            ArrayList<Pair<CardGroup.CardGroupType, Integer>> groupIndexes = new ArrayList<>();

            for (CardGroup group : sources) {
                int amt = 0;
                for (AbstractCard c : group.group) {
                    if (canSelect.test(c)) {
                        ++amt;
                        selectGroup.addToTop(c);
                        cardSourceMap.put(c, group);
                    }
                }

                if (amt > 0)
                    groupIndexes.add(new Pair<>(group.type, selectGroup.size()));
            }
            MultiGroupGridSelectPatches.Fields.groupIndexes.set(selectGroup, groupIndexes);

            if (selectGroup.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (selectGroup.size() <= amount) {
                int cardIndex = 0;
                for (Pair<CardGroup.CardGroupType, Integer> groupIndex : MultiGroupGridSelectPatches.Fields.groupIndexes.get(selectGroup)) {
                    CardGroup source = getGroup(groupIndex.getKey());
                    for (; cardIndex < groupIndex.getValue(); ++cardIndex) {
                        moveToDestination(source, selectGroup.group.get(cardIndex));
                    }
                }

                this.isDone = true;
                return;
            }

            //GridCardSelect patches to display what group cards are from and separate different groups slightly

            AbstractDungeon.gridSelectScreen.open(selectGroup, amount, TEXT[0], false, false, false, false);
            for (AbstractCard c : selectGroup.group) {
                c.unhover();
                c.stopGlowing();
            }
        }
        else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    moveToDestination(cardSourceMap.getOrDefault(c, p.limbo), c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            this.isDone = true;
        }
    }


    private void moveToDestination(CardGroup source, AbstractCard c) {
        if (source.type == CardGroup.CardGroupType.EXHAUST_PILE)
            c.unfadeOut();

        switch (destination.type) {
            case HAND:
                source.moveToHand(c);
                setCardPosition(source, c);
                break;
            case DRAW_PILE:
                source.moveToDeck(c, true);
                setCardPosition(source, c);
                break;
            case DISCARD_PILE:
                source.moveToDiscardPile(c);
                setCardPosition(source, c);
                break;
            case EXHAUST_PILE:
                source.moveToExhaustPile(c);
                setCardPosition(source, c);
                break;
            default:
                SumirekoMod.logger.error("MultiGroupMoveAction attempting to move to cardgroup of invalid type: " + destination.type.name());
                break;
        }
    }

    private void setCardPosition(CardGroup source, AbstractCard c) {
        switch (source.type) {
            case DRAW_PILE:
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                break;
            case DISCARD_PILE:
                c.current_x = CardGroup.DISCARD_PILE_X;
                c.current_y = CardGroup.DISCARD_PILE_Y;
                break;
            case EXHAUST_PILE:
                c.current_x = EXHAUST_PILE_X;
                c.current_y = EXHAUST_PILE_Y;
                break;
        }
        //Hand: Don't need to move. Anything else: what the heck
    }


    private CardGroup getGroup(CardGroup.CardGroupType groupType) {
        switch (groupType) {
            case HAND:
                return AbstractDungeon.player.hand;
            case DRAW_PILE:
                return AbstractDungeon.player.drawPile;
            case DISCARD_PILE:
                return AbstractDungeon.player.discardPile;
            case EXHAUST_PILE:
                return AbstractDungeon.player.exhaustPile;
            default:
                SumirekoMod.logger.error("MultiGroupMoveAction attempting to get cardgroup of invalid type: " + groupType.name());
                return null;
        }
    }
}
