package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoffeeMakerTest {

    private CoffeeMaker coffeeMaker;

    @BeforeEach
    void setUp() {
        coffeeMaker = new CoffeeMaker();
    }

    @Test
    void newCoffeeMakerHasNoRecipesAndDefaultInventory() {
        for (Recipe recipe : coffeeMaker.getRecipes()) {
            assertNotNull(recipe);
            assertNull(recipe.getName());
        }
        assertEquals(15, coffeeMaker.checkInventory().getCoffee());
        assertEquals(15, coffeeMaker.checkInventory().getMilk());
        assertEquals(15, coffeeMaker.checkInventory().getSugar());
        assertEquals(15, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void addRecipeStoresRecipeAndRejectsDuplicateName() {
        Recipe latte = recipe("Latte", 4, 2, 1, 1, 0);

        assertTrue(coffeeMaker.addRecipe(latte));
        assertFalse(coffeeMaker.addRecipe(recipe("Latte", 5, 1, 1, 1, 1)));
        assertEquals(latte, coffeeMaker.getRecipeForName("Latte"));
    }

    @Test
    void addRecipeAllowsAtMostThreeRecipes() {
        assertTrue(coffeeMaker.addRecipe(recipe("Latte", 4, 1, 1, 1, 0)));
        assertTrue(coffeeMaker.addRecipe(recipe("Mocha", 5, 1, 1, 1, 1)));
        assertTrue(coffeeMaker.addRecipe(recipe("Espresso", 3, 2, 0, 0, 0)));

        assertFalse(coffeeMaker.addRecipe(recipe("Cappuccino", 4, 1, 2, 0, 0)));
    }

    @Test
    void getRecipeForNameReturnsAnEmptyRecipeWhenNameIsMissing() {
        assertNull(coffeeMaker.getRecipeForName("Unknown").getName());
    }

    @Test
    void deleteRecipeRemovesExistingRecipeAndAllowsSlotToBeReused() {
        Recipe latte = recipe("Latte", 4, 1, 1, 1, 0);
        assertTrue(coffeeMaker.addRecipe(latte));

        assertTrue(coffeeMaker.deleteRecipe(recipe("Latte", 0, 0, 0, 0, 0)));
        assertNull(coffeeMaker.getRecipeForName("Latte").getName());
        assertTrue(coffeeMaker.addRecipe(recipe("Mocha", 5, 1, 1, 1, 1)));
    }

    @Test
    void deleteRecipeReturnsFalseForMissingOrNullRecipe() {
        assertFalse(coffeeMaker.deleteRecipe(recipe("Unknown", 0, 0, 0, 0, 0)));
        assertFalse(coffeeMaker.deleteRecipe(null));
    }

    @Test
    void editRecipeUpdatesExistingRecipeWithoutChangingItsName() {
        Recipe latte = recipe("Latte", 4, 1, 1, 1, 0);
        Recipe updatedLatte = recipe("Latte", 5, 2, 1, 1, 0);
        coffeeMaker.addRecipe(latte);

        assertTrue(coffeeMaker.editRecipe(latte, updatedLatte));

        assertEquals("Latte", coffeeMaker.getRecipeForName("Latte").getName());
        assertEquals(5, coffeeMaker.getRecipeForName("Latte").getPrice());
        assertEquals(2, coffeeMaker.getRecipeForName("Latte").getAmtCoffee());
    }

    @Test
    void editRecipeReturnsFalseWhenOldRecipeIsNotPresent() {
        Recipe oldRecipe = recipe("Latte", 4, 1, 1, 1, 0);
        Recipe newRecipe = recipe("Latte", 5, 1, 1, 1, 0);

        assertFalse(coffeeMaker.editRecipe(oldRecipe, newRecipe));
    }

    @Test
    void editRecipeRejectsNullArguments() {
        Recipe latte = recipe("Latte", 4, 1, 1, 1, 0);

        assertFalse(coffeeMaker.editRecipe(null, latte));
        assertFalse(coffeeMaker.editRecipe(latte, null));
    }

    @Test
    void editRecipeFindsRecipeWhenAnotherRecipeOccupiesAnEarlierSlot() {
        Recipe latte = recipe("Latte", 4, 1, 1, 1, 0);
        Recipe mocha = recipe("Mocha", 5, 1, 1, 1, 1);
        Recipe updatedLatte = recipe("Latte", 6, 2, 1, 1, 0);
        coffeeMaker.addRecipe(latte);
        coffeeMaker.addRecipe(mocha);

        assertTrue(coffeeMaker.editRecipe(latte, updatedLatte));

        assertEquals(6, coffeeMaker.getRecipeForName("Latte").getPrice());
        assertEquals(5, coffeeMaker.getRecipeForName("Mocha").getPrice());
    }

    @Test
    void editRecipeDoesNotAllowRecipeNameToChange() {
        Recipe latte = recipe("Latte", 4, 1, 1, 1, 0);
        coffeeMaker.addRecipe(latte);

        assertFalse(coffeeMaker.editRecipe(latte, recipe("Mocha", 5, 1, 1, 1, 1)));

        assertEquals(4, coffeeMaker.getRecipeForName("Latte").getPrice());
        assertNull(coffeeMaker.getRecipeForName("Mocha").getName());
    }

    @Test
    void addInventoryAddsAllNonNegativeIngredientAmounts() {
        assertTrue(coffeeMaker.addInventory(1, 2, 3, 4));

        assertEquals(16, coffeeMaker.checkInventory().getCoffee());
        assertEquals(17, coffeeMaker.checkInventory().getMilk());
        assertEquals(18, coffeeMaker.checkInventory().getSugar());
        assertEquals(19, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void addInventoryAcceptsZeroAmounts() {
        assertTrue(coffeeMaker.addInventory(0, 0, 0, 0));

        assertEquals(15, coffeeMaker.checkInventory().getCoffee());
        assertEquals(15, coffeeMaker.checkInventory().getMilk());
        assertEquals(15, coffeeMaker.checkInventory().getSugar());
        assertEquals(15, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void addInventoryRejectsNegativeAmountsWithoutChangingInventory() {
        assertFalse(coffeeMaker.addInventory(-1, 0, 0, 0));
        assertFalse(coffeeMaker.addInventory(0, -1, 0, 0));
        assertFalse(coffeeMaker.addInventory(0, 0, -1, 0));
        assertFalse(coffeeMaker.addInventory(0, 0, 0, -1));

        assertEquals(15, coffeeMaker.checkInventory().getCoffee());
        assertEquals(15, coffeeMaker.checkInventory().getMilk());
        assertEquals(15, coffeeMaker.checkInventory().getSugar());
        assertEquals(15, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void makeCoffeeReturnsChangeAndUsesRecipeIngredients() {
        Recipe latte = recipe("Latte", 4, 2, 3, 4, 5);
        coffeeMaker.addRecipe(latte);

        assertEquals(3, coffeeMaker.makeCoffee(latte, 7));

        assertEquals(13, coffeeMaker.checkInventory().getCoffee());
        assertEquals(12, coffeeMaker.checkInventory().getMilk());
        assertEquals(11, coffeeMaker.checkInventory().getSugar());
        assertEquals(10, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void makeCoffeeSucceedsWhenPaidAmountExactlyMatchesPrice() {
        Recipe latte = recipe("Latte", 4, 2, 3, 4, 5);
        coffeeMaker.addRecipe(latte);

        assertEquals(0, coffeeMaker.makeCoffee(latte, 4));

        assertEquals(13, coffeeMaker.checkInventory().getCoffee());
        assertEquals(12, coffeeMaker.checkInventory().getMilk());
        assertEquals(11, coffeeMaker.checkInventory().getSugar());
        assertEquals(10, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void makeCoffeeReturnsPaymentWhenPaymentIsInsufficient() {
        Recipe latte = recipe("Latte", 4, 2, 3, 4, 5);
        coffeeMaker.addRecipe(latte);

        assertEquals(3, coffeeMaker.makeCoffee(latte, 3));

        assertEquals(15, coffeeMaker.checkInventory().getCoffee());
        assertEquals(15, coffeeMaker.checkInventory().getMilk());
        assertEquals(15, coffeeMaker.checkInventory().getSugar());
        assertEquals(15, coffeeMaker.checkInventory().getChocolate());
    }

    @Test
    void makeCoffeeReturnsPaymentWhenIngredientsAreInsufficient() {
        Recipe latte = recipe("Latte", 4, 2, 3, 4, 5);
        coffeeMaker.addRecipe(latte);
        coffeeMaker.checkInventory().setCoffee(1);

        assertEquals(7, coffeeMaker.makeCoffee(latte, 7));

        assertEquals(1, coffeeMaker.checkInventory().getCoffee());
        assertEquals(15, coffeeMaker.checkInventory().getMilk());
        assertEquals(15, coffeeMaker.checkInventory().getSugar());
        assertEquals(15, coffeeMaker.checkInventory().getChocolate());
    }

    private static Recipe recipe(String name, int price, int coffee, int milk, int sugar, int chocolate) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPrice(price);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);
        return recipe;
    }
}
