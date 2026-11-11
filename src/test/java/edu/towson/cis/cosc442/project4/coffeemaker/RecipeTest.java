package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RecipeTest {

    @Test
    void newRecipeStartsWithZeroAmountsAndNoName() {
        Recipe recipe = new Recipe();

        assertNull(recipe.getName());
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    @ParameterizedTest
    @CsvSource({
            "price, 125",
            "coffee, 3",
            "milk, 2",
            "sugar, 1",
            "chocolate, 4"
    })
    void settersStoreNonNegativeValues(String field, int value) {
        Recipe recipe = new Recipe();

        setField(recipe, field, value);

        assertEquals(value, getField(recipe, field));
    }

    @ParameterizedTest
    @CsvSource({
            "price",
            "coffee",
            "milk",
            "sugar",
            "chocolate"
    })
    void settersAcceptZeroAmounts(String field) {
        Recipe recipe = new Recipe();
        setField(recipe, field, 8);

        setField(recipe, field, 0);

        assertEquals(0, getField(recipe, field));
    }

    @ParameterizedTest
    @CsvSource({
            "price",
            "coffee",
            "milk",
            "sugar",
            "chocolate"
    })
    void settersReplaceNegativeValuesWithZero(String field) {
        Recipe recipe = new Recipe();
        setField(recipe, field, 8);

        setField(recipe, field, -1);

        assertEquals(0, getField(recipe, field));
    }

    @Test
    void equalsUsesNonNullRecipeNames() {
        Recipe first = recipeNamed("Latte");
        Recipe sameName = recipeNamed("Latte");
        Recipe differentName = recipeNamed("Mocha");

        assertTrue(first.equals(sameName));
        assertFalse(first.equals(differentName));
        assertFalse(new Recipe().equals(new Recipe()));
        assertFalse(first.equals(new Recipe()));
        assertFalse(new Recipe().equals(first));
    }

    @Test
    void toStringReturnsRecipeName() {
        Recipe recipe = recipeNamed("Espresso");

        assertEquals("Espresso", recipe.toString());
    }

    private static Recipe recipeNamed(String name) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        return recipe;
    }

    private static void setField(Recipe recipe, String field, int value) {
        switch (field) {
            case "price" -> recipe.setPrice(value);
            case "coffee" -> recipe.setAmtCoffee(value);
            case "milk" -> recipe.setAmtMilk(value);
            case "sugar" -> recipe.setAmtSugar(value);
            case "chocolate" -> recipe.setAmtChocolate(value);
            default -> throw new IllegalArgumentException("Unknown recipe field: " + field);
        }
    }

    private static int getField(Recipe recipe, String field) {
        return switch (field) {
            case "price" -> recipe.getPrice();
            case "coffee" -> recipe.getAmtCoffee();
            case "milk" -> recipe.getAmtMilk();
            case "sugar" -> recipe.getAmtSugar();
            case "chocolate" -> recipe.getAmtChocolate();
            default -> throw new IllegalArgumentException("Unknown recipe field: " + field);
        };
    }
}
