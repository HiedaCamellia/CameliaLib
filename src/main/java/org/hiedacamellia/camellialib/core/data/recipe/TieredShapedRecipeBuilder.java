package org.hiedacamellia.camellialib.core.data.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

//默认T为Tier替换的物品
public class TieredShapedRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> tiered;
    private final RecipeCategory category;
    private final List<Item> result;
    private final List<ItemStack> resultStack;
    private final List<String> rows;
    private final Map<Character, Ingredient> key;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;
    private boolean showNotification;

    public TieredShapedRecipeBuilder(RecipeCategory category, List<ItemLike> result,List<Ingredient> tiered, int count) {
        this(category, getResultStack(result,count),tiered);
    }

    public TieredShapedRecipeBuilder(RecipeCategory category, List<ItemStack> result,List<Ingredient> tiered) {
        this.rows = Lists.newArrayList();
        this.key = Maps.newLinkedHashMap();
        this.showNotification = true;
        this.category = category;
        List<Item> results = new ArrayList<>();
        result.forEach(itemStack -> {
            results.add(itemStack.getItem());
        });
        this.result = results;
        this.resultStack = result;
        this.tiered = tiered;
    }

    public static TieredShapedRecipeBuilder defaultTiered(RecipeCategory category, List<ItemLike> result) {
        return defaultTiered(category, result, 1);
    }

    public static TieredShapedRecipeBuilder defaultTiered(RecipeCategory category, List<ItemLike> result, int count) {
        return defaultTieredI(category, getResultStack(result,count));
    }

    public static TieredShapedRecipeBuilder defaultTieredI(RecipeCategory category, List<ItemStack> result) {
        return new TieredShapedRecipeBuilder(category, result,List.of(Ingredient.of(ItemTags.PLANKS)
                ,Ingredient.of(Items.COBBLESTONE)
                ,Ingredient.of(Items.IRON_INGOT)
                ,Ingredient.of(Items.GOLD_INGOT)
                ,Ingredient.of(Items.DIAMOND)));
    }

    public static TieredShapedRecipeBuilder shaped(RecipeCategory category, List<ItemLike> result,List<Ingredient> tiered) {
        return shaped(category, result,tiered, 1);
    }

    public static TieredShapedRecipeBuilder shaped(RecipeCategory category, List<ItemLike> result,List<Ingredient> tiered, int count) {
        return new TieredShapedRecipeBuilder(category, result,tiered, count);
    }

    public static TieredShapedRecipeBuilder shapedI(RecipeCategory category, List<ItemStack> result,List<Ingredient> tiered) {
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

    @Override
    public RecipeBuilder unlockedBy(String s, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(s, criterionTriggerInstance);
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
        return this.result.get(0);
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        for(int i =0 ;i<this.tiered.size();i++) {
            ResourceLocation resourceLocation = ResourceLocation.tryBuild(pRecipeId.getNamespace(), pRecipeId.getPath()+"_tier"+i);
            this.ensureValid(resourceLocation,i);
            this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe"+"_tier"+i, RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(resourceLocation)).requirements(RequirementsStrategy.OR);
            Map<Character, Ingredient> map = Maps.newHashMap(this.key);
            map.put('T', this.tiered.get(i));
            pFinishedRecipeConsumer.accept(new ShapedRecipeBuilder.Result(resourceLocation, this.result.get(i), this.resultStack.get(i).getCount(), this.group == null ? "" : this.group, determineBookCategory(this.category), this.rows, map, this.advancement, resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.showNotification));
        }
    }


    private void ensureValid(ResourceLocation pId,int tier) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + pId + "!");
        } else {
            Set<Character> charset = Sets.newHashSet(this.key.keySet());
            charset.add('T');
            charset.remove(' ');
            Iterator var3 = this.rows.iterator();

            while(var3.hasNext()) {
                String next = (String)var3.next();

                for(int i = 0; i < next.length(); ++i) {
                    char c = next.charAt(i);
                    if ((!this.key.containsKey(c) && c != ' ' && c!='T')) {
                        throw new IllegalStateException("Pattern in recipe " + pId + " uses undefined symbol '" + c + "'");
                    }

                    charset.remove(c);
                }
            }

            if (!charset.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + pId);
            } else if (this.rows.size() == 1 && ((String)this.rows.get(0)).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + pId + " only takes in a single item - should it be a shapeless recipe instead?");
            } else if (this.advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + pId);
            }
        }
    }


    public static List<ItemStack> getResultStack(List<ItemLike> result,int count){
        List<ItemStack> stacks = new ArrayList<>();
        result.forEach(item -> stacks.add(new ItemStack(item, count)));
        return stacks;
    }

    public static class Result extends CraftingRecipeBuilder.CraftingResult {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final boolean showNotification;

        public Result(ResourceLocation pId, Item pResult, int pCount, String pGroup, CraftingBookCategory pCategory, List<String> pPattern, Map<Character, Ingredient> pKey, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, boolean pShowNotification) {
            super(pCategory);
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.group = pGroup;
            this.pattern = pPattern;
            this.key = pKey;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.showNotification = pShowNotification;
        }

        public void serializeRecipeData(JsonObject pJson) {
            super.serializeRecipeData(pJson);
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray $$1 = new JsonArray();
            Iterator var3 = this.pattern.iterator();

            while(var3.hasNext()) {
                String $$2 = (String)var3.next();
                $$1.add($$2);
            }

            pJson.add("pattern", $$1);
            JsonObject $$3 = new JsonObject();
            Iterator var7 = this.key.entrySet().iterator();

            while(var7.hasNext()) {
                Map.Entry<Character, Ingredient> $$4 = (Map.Entry)var7.next();
                $$3.add(String.valueOf($$4.getKey()), ((Ingredient)$$4.getValue()).toJson());
            }

            pJson.add("key", $$3);
            JsonObject $$5 = new JsonObject();
            $$5.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                $$5.addProperty("count", this.count);
            }

            pJson.add("result", $$5);
            pJson.addProperty("show_notification", this.showNotification);
        }

        public RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
