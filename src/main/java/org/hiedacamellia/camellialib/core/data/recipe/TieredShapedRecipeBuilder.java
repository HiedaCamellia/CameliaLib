package org.hiedacamellia.camellialib.core.data.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.*;

//默认T为Tier替换的物品
public class TieredShapedRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> tiered;
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final ItemStack resultStack;
    private final List<String> rows;
    private final Map<Character, Ingredient> key;
    private final Map<String, Criterion<?>> criteria;
    @Nullable
    private String group;
    private boolean showNotification;

    public TieredShapedRecipeBuilder(RecipeCategory category, ItemLike result,List<Ingredient> tiered, int count) {
        this(category, new ItemStack(result, count),tiered);
    }

    public TieredShapedRecipeBuilder(RecipeCategory category, ItemStack result,List<Ingredient> tiered) {
        this.rows = Lists.newArrayList();
        this.key = Maps.newLinkedHashMap();
        this.criteria = new LinkedHashMap();
        this.showNotification = true;
        this.category = category;
        this.result = result.getItem();
        this.count = result.getCount();
        this.resultStack = result;
        this.tiered = tiered;
    }

    public static TieredShapedRecipeBuilder defaultTiered(RecipeCategory category, ItemLike result) {
        return shaped(category, result,List.of(Ingredient.of(ItemTags.PLANKS)
                ,Ingredient.of(Items.COBBLESTONE)
                ,Ingredient.of(Items.IRON_INGOT)
                ,Ingredient.of(Items.GOLD_INGOT)
                ,Ingredient.of(Items.DIAMOND)), 1);
    }

    public static TieredShapedRecipeBuilder defaultTiered(RecipeCategory category, ItemLike result, int count) {
        return new TieredShapedRecipeBuilder(category, result,List.of(Ingredient.of(ItemTags.PLANKS)
                ,Ingredient.of(Items.COBBLESTONE)
                ,Ingredient.of(Items.IRON_INGOT)
                ,Ingredient.of(Items.GOLD_INGOT)
                ,Ingredient.of(Items.DIAMOND)), count);
    }

    public static TieredShapedRecipeBuilder defaultTiered(RecipeCategory category, ItemStack result) {
        return new TieredShapedRecipeBuilder(category, result,List.of(Ingredient.of(ItemTags.PLANKS)
                ,Ingredient.of(Items.COBBLESTONE)
                ,Ingredient.of(Items.IRON_INGOT)
                ,Ingredient.of(Items.GOLD_INGOT)
                ,Ingredient.of(Items.DIAMOND)));
    }

    public static TieredShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result,List<Ingredient> tiered) {
        return shaped(category, result,tiered, 1);
    }

    public static TieredShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result,List<Ingredient> tiered, int count) {
        return new TieredShapedRecipeBuilder(category, result,tiered, count);
    }

    public static TieredShapedRecipeBuilder shaped(RecipeCategory category, ItemStack result,List<Ingredient> tiered) {
        return new TieredShapedRecipeBuilder(category, result,tiered);
    }

    public TieredShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(tag));
    }

    public TieredShapedRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(new ItemLike[]{item}));
    }

    public TieredShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public TieredShapedRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != ((String)this.rows.get(0)).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    public TieredShapedRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public TieredShapedRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public TieredShapedRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        for(int i =0 ;i<this.tiered.size();i++){
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath()+"_Tier"+i);
            ShapedRecipePattern shapedrecipepattern = this.ensureValid(resourceLocation,i);
            Advancement.Builder advancement$builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(AdvancementRequirements.Strategy.OR);
            Objects.requireNonNull(advancement$builder);
            this.criteria.forEach(advancement$builder::addCriterion);
            ShapedRecipe shapedrecipe = new ShapedRecipe(Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), shapedrecipepattern, this.resultStack, this.showNotification);
            recipeOutput.accept(resourceLocation, shapedrecipe, advancement$builder.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
        }
    }

    private ShapedRecipePattern ensureValid(ResourceLocation loaction,int tier) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + loaction);
        } else {
            Map<Character, Ingredient> key = new HashMap<>(this.key);
            key.put('T',this.tiered.get(tier));
            return ShapedRecipePattern.of(key, this.rows);
        }
    }

}
