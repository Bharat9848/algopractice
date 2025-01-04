package topological.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * You are given information about nn different recipes. Each recipe is listed in the array recipes, and its corresponding ingredients are provided in the 2D array ingredients. The ithith recipe, recipes[i], can be prepared if all the necessary ingredients listed in ingredients[i] are available. Some ingredients might need to be created from other recipes, meaning ingredients[i] may contain strings that are also in recipes.
 *
 * Additionally, you have a string array supplies that contains all the ingredients you initially have, and you have an infinite supply of each.
 *
 * Return a list of all the recipes you can create. The answer can be returned in any order.
 *
 *     Note: It is possible for two recipes to list each other as ingredients. However, if these are the only two recipes provided, the expected output is an empty list.
 *
 * Constraints:
 *
 *     n == recipes.length == ingredients.length
 *
 *     1≤n ≤100
 *
 *     1≤1≤ ingredients[i].length, supplies.length ≤100≤100
 *
 *     1≤1≤ recipes[i].length, ingredients[i][j].length, supplies[k].length ≤10≤10
 *
 *     recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
 *
 *     All the combined values of recipes and supplies are unique.
 *
 *     Each ingredients[i] doesn’t contain any duplicate values.
 */
public class CreateRecipe {
    public static List<String> findAllRecipes (String[] recipes, List<List<String>> ingredients, String[] supplies) {

        Map<String, Integer> incoming = new HashMap<>();
        Map<String, List<String>> outgoing = new HashMap<>();
        for(String rece: recipes){
            incoming.put(rece, 0);
            outgoing.put(rece, new ArrayList<>());
        }
        for (int i = 0; i < ingredients.size(); i++) {
            for (int j = 0; j < ingredients.get(i).size(); j++) {
                String ingredient = ingredients.get(i).get(j);
                if(incoming.get(ingredient) == null){
                    incoming.put(ingredient, 0);
                    outgoing.put(ingredient, new ArrayList<>());
                }
                outgoing.get(ingredient).add(recipes[i]);
            }
            incoming.put(recipes[i], incoming.get(recipes[i])+ ingredients.get(i).size());
        }

        List<String> queue = new ArrayList<>();
        for(String supply : supplies){
            queue.add(supply);
        }
        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()){
            String item = queue.remove(0);
            if(incoming.containsKey(item)){
                var receipeNodes = outgoing.get(item);
                for (int i = 0; i < receipeNodes.size(); i++) {
                    String recipe = receipeNodes.get(i);
                    incoming.put(recipe, incoming.get(recipe) -1);
                    if(incoming.get(recipe) == 0){
                        result.add(recipe);
                        queue.add(recipe);
                    }
                }
                outgoing.remove(item);
                incoming.remove(item);
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "soup,spaghetti|[meat,yogurt,sauce],[pasta,salt,sauce]]|meat,pasta,yogurt,sauce|soup",
            "soup,spaghetti|[meat,yogurt,sauce],[pasta,salt,sauce]]||",
            "fries,burger,sandwich|[[potatoes,oil],[sauce,onion,fries,pepper,bun,meat],[cheese,fish,bread,sauce]]|sauce,fish,onion,pepper,bun,bread,meat,cheese,potatoes|sandwich",
            "pasta,ice cream|[vegetable juice,egg,flour,flavour],[ice,milk fat,milk protein,sugar]]|vegetable juice,milk protein,egg,ice,flour,flavour|pasta"
    })
    void test(String recipeStr, String ingrStr, String supplyStr, String expectedStr){
        List<String> expected = expectedStr == null ? new ArrayList<>(): Arrays.stream(expectedStr.split(",")).toList();
        String[] supplies = supplyStr == null ? new String[0] : supplyStr.split(",");
        String[] recipes = recipeStr == null ? new String[0] : recipeStr.split(",");
        List<List<String>> ingredients = StringParser.parseStringAsListOfList(ingrStr, "\\[([a-z ]+,)+[a-z ]+\\]", s -> s);
        Assertions.assertEquals(expected, findAllRecipes(recipes, ingredients, supplies));
    }
}
