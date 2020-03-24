package sumireko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.sun.org.apache.xpath.internal.axes.PredicatedNodeTest;
import sumireko.abstracts.SealCard;
import sumireko.actions.DiscardSealAction;
import sumireko.actions.ExhaustSealAction;
import sumireko.actions.ResetSealSystemAction;
import sumireko.actions.YeetSealAction;
import sumireko.effects.SealLineEffect;
import sumireko.effects.SealPreviewGlow;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.ModifySealPower;
import sumireko.util.PretendMonster;
import sumireko.util.PreviewIntent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static sumireko.SumirekoMod.*;
import static sumireko.util.MathHelper.dist;

public class SealSystem {
    public static SealCard[] aroundCards = new SealCard[4];
    public static SealCard centerCard = null;
    public static HashMap<SealCard, AbstractMonster> targets = new HashMap<>();

    private static ArrayList<PreviewIntent> previewIntents = new ArrayList<>();

    public static int nextIndex = 0;

    private static float previewTime = 0.0f;
    private static float lineTime = 0.0f;

    private static ArrayList<AbstractGameEffect> previewEffects = new ArrayList<>();
    private static ArrayList<AbstractGameEffect> lineEffects = new ArrayList<>();

    private static final float PREVIEW_DELAY = 0.8f;
    private static final float LINE_DELAY = 0.2f;

    private static final float CENTER_X = Settings.WIDTH / 2.0F;
    private static final float CENTER_Y = Settings.HEIGHT / 2.0F + 60.0f;

    private static final float TOP_POS_X = CENTER_X;
    private static final float TOP_POS_Y = CENTER_Y + 275.0f * Settings.scale;
    private static final float LEFT_POS_X = CENTER_X - 275.0f * Settings.scale;
    private static final float LEFT_POS_Y = CENTER_Y;
    private static final float RIGHT_POS_X = CENTER_X + 275.0f * Settings.scale;
    private static final float RIGHT_POS_Y = CENTER_Y;
    private static final float BOTTOM_POS_X = CENTER_X;
    private static final float BOTTOM_POS_Y = CENTER_Y - 275.0f * Settings.scale;

    private static final float SHORT_DISTANCE = dist(TOP_POS_X, RIGHT_POS_X, TOP_POS_Y, RIGHT_POS_Y);
    private static final float LONG_DISTANCE = dist(TOP_POS_X, BOTTOM_POS_X, TOP_POS_Y, BOTTOM_POS_Y);



    public static void reset()
    {
        aroundCards = new SealCard[4];
        centerCard = null;
        nextIndex = 0;
        targets.clear();
        previewIntents.clear();
        previewEffects.clear();
        lineEffects.clear();
    }

    public static void addSeal(SealCard c, AbstractMonster target)
    {
        targets.put(c, target);
        if (nextIndex < 4)
        {
            c.targetDrawScale = 0.5f;
            aroundCards[nextIndex++] = c;
        }
        else if (nextIndex == 4)
        {
            c.targetDrawScale = 0.66f;
            centerCard = c;
            ++nextIndex;
        }
        else
        {
            logger.error("Trying to add seal when already at 5 Seals?");
        }
        updatePositions();
    }

    private static void updatePositions()
    {
        //count = nextIndex
        switch (nextIndex)
        {
            case 5:
                centerCard.target_x = CENTER_X;
                centerCard.target_y = CENTER_Y;
            case 4:
                aroundCards[3].target_x = RIGHT_POS_X;
                aroundCards[3].target_y = RIGHT_POS_Y;
            case 3:
                aroundCards[2].target_x = LEFT_POS_X;
                aroundCards[2].target_y = LEFT_POS_Y;
            case 2:
                aroundCards[1].target_x = BOTTOM_POS_X;
                aroundCards[1].target_y = BOTTOM_POS_Y;
            case 1:
                aroundCards[0].target_x = TOP_POS_X;
                aroundCards[0].target_y = TOP_POS_Y;
                break;
            default:
                logger.info("Repositioning cards with 0 or somehow more than 5 cards?");
                break;
        }
    }

    public static void render(SpriteBatch sb)
    {
        if (CardCrawlGame.isInARun())
        {
            previewTime -= Gdx.graphics.getDeltaTime();
            lineTime -= Gdx.graphics.getDeltaTime();
            boolean lines = lineTime < 0.0f;

            boolean preview = previewTime < 0.0f && AbstractDungeon.player.hoveredCard instanceof SealCard;

            int renderAmount = AbstractDungeon.player.hoveredCard instanceof SealCard ? nextIndex : nextIndex - 1;

            if (lines)
            {
                lineTime = LINE_DELAY;
                for (int i = 0; i <= renderAmount; ++i)
                {
                    switch (i)
                    {
                        case 0:
                            if (renderAmount >= 3)
                                lineEffects.add(new SealLineEffect(TOP_POS_X, TOP_POS_Y, RIGHT_POS_X, RIGHT_POS_Y, SHORT_DISTANCE, 135));

                            if (renderAmount >= 1 && renderAmount != 3)
                                lineEffects.add(new SealLineEffect(TOP_POS_X, TOP_POS_Y, BOTTOM_POS_X, BOTTOM_POS_Y, LONG_DISTANCE, 90));

                            if (renderAmount >= 2)
                                lineEffects.add(new SealLineEffect(TOP_POS_X, TOP_POS_Y, LEFT_POS_X, LEFT_POS_Y, SHORT_DISTANCE, 45));
                            break;
                        case 1:
                            if (renderAmount >= 3)
                                lineEffects.add(new SealLineEffect(BOTTOM_POS_X, BOTTOM_POS_Y, RIGHT_POS_X, RIGHT_POS_Y, SHORT_DISTANCE, 45));

                            if (renderAmount >= 2)
                                lineEffects.add(new SealLineEffect(BOTTOM_POS_X, BOTTOM_POS_Y, LEFT_POS_X, LEFT_POS_Y, SHORT_DISTANCE, 135));
                            break;
                        case 2:
                            if (renderAmount >= 4)
                                lineEffects.add(new SealLineEffect(LEFT_POS_X, LEFT_POS_Y, RIGHT_POS_X, RIGHT_POS_Y, LONG_DISTANCE, 0));
                            break;
                    }
                }
            }

            for (AbstractGameEffect e : lineEffects)
            {
                e.update();
                e.render(sb);
            }
            for (AbstractGameEffect e : previewEffects)
            {
                e.update();
                e.render(sb);
            }

            boolean centerPreview = true;
            for (int i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                {
                    //render next card position preview based on where it is null
                    if (preview) {
                        previewTime = PREVIEW_DELAY;
                        switch(i)
                        {
                            case 0:
                                previewEffects.add(new SealPreviewGlow(TOP_POS_X, TOP_POS_Y, SUMIREKO_COLOR_DIM));
                                break;
                            case 1:
                                previewEffects.add(new SealPreviewGlow(BOTTOM_POS_X, BOTTOM_POS_Y, SUMIREKO_COLOR_DIM));
                                break;
                            case 2:
                                previewEffects.add(new SealPreviewGlow(LEFT_POS_X, LEFT_POS_Y, SUMIREKO_COLOR_DIM));
                                break;
                            case 3:
                                previewEffects.add(new SealPreviewGlow(RIGHT_POS_X, RIGHT_POS_Y, SUMIREKO_COLOR_DIM));
                                break;
                        }
                    }
                    centerPreview = false;
                    break;
                }

                aroundCards[i].update();
                aroundCards[i].render(sb);
            }

            if (centerCard != null)
            {
                centerCard.update();
                centerCard.render(sb);
            }
            else if (centerPreview)
            {
                //render next card position preview in center
                if (preview) {
                    previewTime = PREVIEW_DELAY;
                    previewEffects.add(new SealPreviewGlow(CENTER_X, CENTER_Y, SUMIREKO_COLOR));
                }
            }

            for (PreviewIntent i : previewIntents)
            {
                i.update();
                i.render(sb);
            }
        }
    }

    public static void calculateSeals()
    {
        //reset and count duplicates
        HashMap<String, Integer> cardCounts = new HashMap<>();
        int i = 0;
        for (; i < 4; ++i)
        {
            if (aroundCards[i] == null)
                break;

            aroundCards[i].resetSealValue();

            if (!cardCounts.containsKey(aroundCards[i].cardID))
            {
                cardCounts.put(aroundCards[i].cardID, 0);
            }
            else
            {
                cardCounts.put(aroundCards[i].cardID, cardCounts.get(aroundCards[i].cardID) + 1);
            }
        }
        if (centerCard != null)
        {
            centerCard.resetSealValue();

            if (!cardCounts.containsKey(centerCard.cardID))
            {
                cardCounts.put(centerCard.cardID, 0);
            }
            else
            {
                cardCounts.put(centerCard.cardID, cardCounts.get(centerCard.cardID) + 1);
            }
        }

        //duplicate bonus
        for (i = 0; i < 4; ++i)
        {
            if (aroundCards[i] == null)
                break;

            aroundCards[i].modifySealValue(cardCounts.get(aroundCards[i].cardID));
        }
        if (centerCard != null)
            centerCard.modifySealValue(cardCounts.get(centerCard.cardID));


        //adjacency and power bonuses.
        //modifiers are applied to BUFF_SEAL seals first, to maintain consistent results.

        for (i = 0; i < 4; ++i) //add/subtract
        {
            if (aroundCards[i] == null)
                break;

            if (!aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                continue;

            switch (i)
            {
                case 0:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyBaseAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[1] != null)
                    {
                        aroundCards[1].applyBaseAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyBaseAdjacencyEffect(aroundCards[i]);
                    break;
                case 1:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyBaseAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[0] != null && centerCard == null)
                    {
                        aroundCards[0].applyBaseAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyBaseAdjacencyEffect(aroundCards[i]);
                    break;
                case 2:
                case 3:
                    if (aroundCards[0] != null)
                        aroundCards[0].applyBaseAdjacencyEffect(aroundCards[i]);

                    if (aroundCards[1] != null)
                        aroundCards[1].applyBaseAdjacencyEffect(aroundCards[i]);
                    break;
            }
            if (centerCard != null)
            {
                centerCard.applyBaseAdjacencyEffect(aroundCards[i]);
            }

            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ModifySealPower)
                    ((ModifySealPower) p).modifySeal(aroundCards[i]);
            }
        }
        if (centerCard != null && centerCard.hasTag(CustomCardTags.BUFF_SEAL))
        {
            for (i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                    break;

                aroundCards[i].applyBaseAdjacencyEffect(centerCard);
            }

            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ModifySealPower)
                    ((ModifySealPower) p).modifySeal(centerCard);
            }
        }

        for (i = 0; i < 4; ++i) //multiplication
        {
            if (aroundCards[i] == null)
                break;

            if (!aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                continue;

            switch (i)
            {
                case 0:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[1] != null)
                    {
                        aroundCards[1].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    break;
                case 1:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[0] != null && centerCard == null)
                    {
                        aroundCards[0].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    break;
                case 2:
                case 3:
                    if (aroundCards[0] != null)
                        aroundCards[0].applyFinalBaseAdjacencyEffect(aroundCards[i]);

                    if (aroundCards[1] != null)
                        aroundCards[1].applyFinalBaseAdjacencyEffect(aroundCards[i]);
                    break;
            }
            if (centerCard != null)
            {
                centerCard.applyFinalBaseAdjacencyEffect(aroundCards[i]);
            }
        }
        if (centerCard != null && centerCard.hasTag(CustomCardTags.BUFF_SEAL))
        {
            for (i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                    break;

                aroundCards[i].applyFinalBaseAdjacencyEffect(centerCard);
            }
        }

        //non-buff seals
        for (i = 0; i < 4; ++i) //add/subtract
        {
            if (aroundCards[i] == null)
                break;

            if (aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                continue;

            switch (i)
            {
                case 0:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[1] != null)
                    {
                        aroundCards[1].applyAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyAdjacencyEffect(aroundCards[i]);
                    break;
                case 1:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[0] != null && centerCard == null)
                    {
                        aroundCards[0].applyAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyAdjacencyEffect(aroundCards[i]);
                    break;
                case 2:
                case 3:
                    if (aroundCards[0] != null)
                        aroundCards[0].applyAdjacencyEffect(aroundCards[i]);

                    if (aroundCards[1] != null)
                        aroundCards[1].applyAdjacencyEffect(aroundCards[i]);
                    break;
            }
            if (centerCard != null)
            {
                centerCard.applyAdjacencyEffect(aroundCards[i]);
            }

            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ModifySealPower)
                    ((ModifySealPower) p).modifySeal(aroundCards[i]);
            }
        }
        if (centerCard != null && !centerCard.hasTag(CustomCardTags.BUFF_SEAL))
        {
            centerCard.modifySealValue(cardCounts.get(centerCard.cardID));

            for (i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                    break;

                aroundCards[i].applyAdjacencyEffect(centerCard);
            }

            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ModifySealPower)
                    ((ModifySealPower) p).modifySeal(centerCard);
            }
        }

        boolean calculateIntentPreview = false;

        for (i = 0; i < 4; ++i) //multiplication
        {
            if (aroundCards[i] == null)
                break;

            calculateIntentPreview = true; //if any seals are in play

            if (aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                continue;

            switch (i)
            {
                case 0:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyFinalAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[1] != null)
                    {
                        aroundCards[1].applyFinalAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyFinalAdjacencyEffect(aroundCards[i]);
                    break;
                case 1:
                    if (aroundCards[3] != null)
                    {
                        aroundCards[3].applyFinalAdjacencyEffect(aroundCards[i]);
                    }
                    else if (aroundCards[0] != null && centerCard == null)
                    {
                        aroundCards[0].applyFinalAdjacencyEffect(aroundCards[i]);
                    }
                    if (aroundCards[2] != null)
                        aroundCards[2].applyFinalAdjacencyEffect(aroundCards[i]);
                    break;
                case 2:
                case 3:
                    if (aroundCards[0] != null)
                        aroundCards[0].applyFinalAdjacencyEffect(aroundCards[i]);

                    if (aroundCards[1] != null)
                        aroundCards[1].applyFinalAdjacencyEffect(aroundCards[i]);
                    break;
            }
            if (centerCard != null)
            {
                centerCard.applyFinalAdjacencyEffect(aroundCards[i]);
            }
        }
        if (centerCard != null && !centerCard.hasTag(CustomCardTags.BUFF_SEAL))
        {
            for (i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                    return;

                aroundCards[i].applyFinalAdjacencyEffect(centerCard);
            }

            centerCard.isSealModified = centerCard.sealValue != centerCard.baseSealValue;
        }




        // Calculate preview result
        previewIntents.clear();

        if (calculateIntentPreview && !AbstractDungeon.player.hasRelic(RunicDome.ID))
        {
            HashMap<AbstractMonster, PretendMonster> previewMonsters = new HashMap<>();

            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                if (!m.halfDead && !m.isDying && !m.isEscaping)
                    previewMonsters.put(m, new PretendMonster(m));

            PretendMonster.prepareFakePlayerPowers();

            if (centerCard != null)
            {
                centerCard.instantSealEffect(previewMonsters.get(SealSystem.targets.get(centerCard)), previewMonsters);
            }

            for (i = 3; i >= 0; --i)
            {
                if (aroundCards[i] == null)
                    continue;

                aroundCards[i].instantSealEffect(previewMonsters.get(SealSystem.targets.get(aroundCards[i])), previewMonsters);
            }

            for (Map.Entry<AbstractMonster, PretendMonster> m : previewMonsters.entrySet())
            {
                m.getValue().calculateDamage();
                previewIntents.add(new PreviewIntent(m.getKey(), m.getValue()));
            }
        }
    }

    public static void triggerEndOfTurn() {
        if (centerCard != null)
        {
            centerCard.superFlash(Color.PURPLE);
            centerCard.triggerSealEffect(SealSystem.targets.get(centerCard));

            if (centerCard.hasTag(CustomCardTags.FRAGILE_SEAL))
                AbstractDungeon.actionManager.addToBottom(new ExhaustSealAction(centerCard, true));
            else
                AbstractDungeon.actionManager.addToBottom(new DiscardSealAction(centerCard, true));
        }

        for (int i = 3; i >= 0; --i)
        {
            if (aroundCards[i] == null)
                continue;

            aroundCards[i].superFlash(Color.PURPLE);
            aroundCards[i].triggerSealEffect(SealSystem.targets.get(aroundCards[i]));

            if (aroundCards[i].hasTag(CustomCardTags.ULTRA_FRAGILE_SEAL))
                AbstractDungeon.actionManager.addToBottom(new YeetSealAction(aroundCards[i], i));
            else if (aroundCards[i].hasTag(CustomCardTags.FRAGILE_SEAL))
                AbstractDungeon.actionManager.addToBottom(new ExhaustSealAction(aroundCards[i], i));
            else
                AbstractDungeon.actionManager.addToBottom(new DiscardSealAction(aroundCards[i], i));
        }

        AbstractDungeon.actionManager.addToBottom(new ResetSealSystemAction());
    }
}
