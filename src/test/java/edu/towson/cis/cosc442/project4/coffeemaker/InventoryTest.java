package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void newInventoryContainsFifteenOfEachIngredient() {
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @ParameterizedTest
    @CsvSource({
            "coffee, 0",
            "milk, 2",
            "sugar, 7",
            "chocolate, 15"
    })
    void settersStoreNonNegativeAmounts(String ingredient, int amount) {
        setAmount(ingredient, amount);

        assertEquals(amount, getAmount(ingredient));
    }

    @ParameterizedTest
    @CsvSource({
            "coffee",
            "milk",
            "sugar",
            "chocolate"
    })
    void settersAcceptZeroAmounts(String ingredient) {
        setAmount(ingredient, 8);

        setAmount(ingredient, 0);

        assertEquals(0, getAmount(ingredient));
    }

    @ParameterizedTest
    @CsvSource({
            "coffee",
            "milk",
            "sugar",
            "chocolate"
    })
    void settersClampNegativeAmountsToZero(String ingredient) {
        setAmount(ingredient, -1);

        assertEquals(0, getAmount(ingredient));
    }

    @Test
    void enoughIngredientsAcceptsExactAmountsAndRejectsShortage() {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(2);
        recipe.setAmtMilk(3);
        recipe.setAmtSugar(4);
        recipe.setAmtChocolate(5);
        inventory.setCoffee(2);
        inventory.setMilk(3);
        inventory.setSugar(4);
        inventory.setChocolate(5);

        assertTrue(inventory.enoughIngredients(recipe));

        inventory.setCoffee(1);
        assertFalse(inventory.enoughIngredients(recipe));
        inventory.setCoffee(2);
        inventory.setMilk(2);
        assertFalse(inventory.enoughIngredients(recipe));
        inventory.setMilk(3);
        inventory.setSugar(3);
        assertFalse(inventory.enoughIngredients(recipe));
        inventory.setSugar(4);
        inventory.setChocolate(4);
        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void toStringListsEachIngredientAmount() {
        inventory.setCoffee(1);
        inventory.setMilk(2);
        inventory.setSugar(3);
        inventory.setChocolate(4);

        assertEquals("Coffee: 1\nMilk: 2\nSugar: 3\nChocolate: 4\n", inventory.toString());
    }

    private void setAmount(String ingredient, int amount) {
        switch (ingredient) {
            case "coffee" -> inventory.setCoffee(amount);
            case "milk" -> inventory.setMilk(amount);
            case "sugar" -> inventory.setSugar(amount);
            case "chocolate" -> inventory.setChocolate(amount);
            default -> throw new IllegalArgumentException("Unknown ingredient: " + ingredient);
        }
    }

    private int getAmount(String ingredient) {
        return switch (ingredient) {
            case "coffee" -> inventory.getCoffee();
            case "milk" -> inventory.getMilk();
            case "sugar" -> inventory.getSugar();
            case "chocolate" -> inventory.getChocolate();
            default -> throw new IllegalArgumentException("Unknown ingredient: " + ingredient);
        };
    }
}
