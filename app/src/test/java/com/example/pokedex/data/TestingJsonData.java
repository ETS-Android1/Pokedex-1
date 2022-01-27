package com.example.pokedex.data;

import com.example.pokedex.data.model.Ability;
import com.example.pokedex.data.model.Move;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.data.model.PokemonType;
import com.example.pokedex.data.model.Stat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class TestingJsonData {
    private static final Gson gson = new Gson();

    public static JsonElement ofNextApiResouceUrl(String nextApiResourceUrl){
        return convertToJsonElementFrom(ofNamedApiResourcesInString(
                new NamedApiResources(nextApiResourceUrl, null, null)));
    }

    public static JsonElement ofPrevApiResourceUrl(String prevApiResourceUrl){
        return convertToJsonElementFrom(ofNamedApiResourcesInString(
                new NamedApiResources(null, prevApiResourceUrl, null)));
    }

    public static JsonElement ofNamedApiResourceList(List<NamedApiResource> namedApiResourceList){
        return convertToJsonElementFrom(ofNamedApiResourcesInString(
                new NamedApiResources(null, null, namedApiResourceList)));
    }

    public static String ofNamedApiResourcesInString(NamedApiResources namedApiResources){
        String defaultNextResourceUrl = "https://pokeapi.co/api/v2/pokemon/?offset=3&limit=3";
        String defaultPrevResourceUrl = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=3";
        String nextResourceUrl = namedApiResources.getNextApiResourceUrl() == null
                ? defaultNextResourceUrl : namedApiResources.getNextApiResourceUrl();
        String prevResourceUrl = namedApiResources.getPrevApiResourceUrl() == null
                ? defaultPrevResourceUrl : namedApiResources.getPrevApiResourceUrl();

        return "{\"count\": 1118," +
                "\"next\": \"" + nextResourceUrl + "\"," +
                "\"previous\": \"" + prevResourceUrl + "\"," +
                "\"results\": " +
                "[" + getJsonStringFrom(namedApiResources.getList()) + "]}";
    }

    private static String getJsonStringFrom(List<NamedApiResource> namedApiResourceList){
        if (namedApiResourceList == null || namedApiResourceList.isEmpty()){
            return "";
        }

        List<String> namedApiResourceJsonString = new ArrayList<>();
        for(NamedApiResource namedApiResource : namedApiResourceList){
            namedApiResourceJsonString.add("{" +
                    "\"name\": \"" + namedApiResource.getName() + "\"," +
                    "\"url\": \"" + namedApiResource.getUrl() + "\"" +
                    "}");
        }
        return String.join(",", namedApiResourceJsonString);
    }

    public static JsonElement ofPokemonId(int pokemonId){
        return convertToJsonElementFrom(ofPokemonInString(
                new Pokemon(pokemonId, null, null, null, null, null)));
    }

    public static JsonElement ofPokemonName(String pokemonName){
        return convertToJsonElementFrom(ofPokemonInString(
                new Pokemon(0, pokemonName, null, null, null, null)));
    }

    public static JsonElement ofPokemonAbilities(List<Ability> abilityList){
        return convertToJsonElementFrom(ofPokemonInString(
                new Pokemon(0, null, abilityList, null, null, null)));
    }

    public static JsonElement ofPokemonMoves(List<Move> moveList){
        return convertToJsonElementFrom(ofPokemonInString(
                new Pokemon(0, null, null, moveList, null, null)));
    }

    public static JsonElement ofPokemonStats(List<Stat> statList){
        return convertToJsonElementFrom(ofPokemonInString(
                new Pokemon(0, null, null, null, statList, null)));
    }

    public static JsonElement ofPokemonTypes(List<PokemonType> typeList){
        return convertToJsonElementFrom(ofPokemonInString(
                new Pokemon(0, null, null, null, null, typeList)));
    }

    public static String ofPokemonInString(Pokemon pokemon){
        String defaultName = "bulbasaur";
        String pokemonName = pokemon.getName() == null ? defaultName : pokemon.getName();

        return "{\n" +
                "\"abilities\": [\n" +
                    getAbilityJsonStringFrom(pokemon.getAbilities()) +
                "],\n" +
                "\"base_experience\": 39,\n" +
                "\"forms\": [\n" +
                    "{\n" +
                        "\"name\": \"caterpie\",\n" +
                        "\"url\": \"https://pokeapi.co/api/v2/pokemon-form/10/\"\n" +
                    "}\n" +
                "],\n" +
                "\"game_indices\": [],\n" +
                "\"height\": 3,\n" +
                "\"held_items\": [],\n" +
                "\"id\": " + pokemon.getId() + ",\n" +
                "\"is_default\": true,\n" +
                "\"location_area_encounters\": \"https://pokeapi.co/api/v2/pokemon/10/encounters\",\n" +
                "\"moves\": [" +
                    getMoveJsonStringFrom(pokemon.getMoves()) +
                "],\n" +
                "\"name\": \"" + pokemonName + "\",\n" +
                "\"order\": 14,\n" +
                "\"past_types\": [],\n" +
                "\"species\": {\n" +
                "\"name\": \"caterpie\",\n" +
                "\"url\": \"https://pokeapi.co/api/v2/pokemon-species/10/\"\n" +
                "},\n" +
                "\"sprites\": {},\n" +
                "\"stats\": [" +
                    getStatJsonStringFrom(pokemon.getStats()) +
                "],\n" +
                "\"types\": [\n" +
                    getPokemonTypeJsonStringFrom(pokemon.getPokemonTypes()) +
                "],\n" +
                "\"weight\": 29\n" +
                "}";
    }

    private static String getAbilityJsonStringFrom(List<Ability> abilities){
        if (abilities == null || abilities.isEmpty()){
            return "";
        }

        List<String> abilityJsonStrings = new ArrayList<>();
        for(Ability ability : abilities){
            abilityJsonStrings.add(
                    "{\"ability\": {" +
                        "\"name\": \"" + ability.getAbilityInfo().getName() + "\"," +
                        "\"url\": \"" + ability.getAbilityInfo().getUrl() + "\"" +
                    "}}"
            );
        }
        return String.join(",", abilityJsonStrings);
    }

    private static String getMoveJsonStringFrom(List<Move> moveList){
        if (moveList == null || moveList.isEmpty()){
            return "";
        }

        List<String> moveJsonStrings = new ArrayList<>();
        for(Move move : moveList){
            moveJsonStrings.add(
                    "{\"move\": {" +
                        "\"name\": \"" + move.getMoveInfo().getName() + "\"," +
                        "\"url\": \"" + move.getMoveInfo().getUrl() + "\"" +
                    "}}"
            );
        }
        return String.join(",", moveJsonStrings);
    }

    private static String getStatJsonStringFrom(List<Stat> statList){
        if (statList == null || statList.isEmpty()){
            return "";
        }

        List<String> moveJsonStrings = new ArrayList<>();
        for(Stat stat : statList){
            moveJsonStrings.add(
                    "{\"stat\": {" +
                        "\"name\": \"" + stat.getStatInfo().getName() + "\"," +
                        "\"url\": \"" + stat.getStatInfo().getUrl() + "\"" +
                    "}," +
                    "\"base_stat\": " + stat.getValue() + "}");
        }
        return String.join(",", moveJsonStrings);
    }

    private static String getPokemonTypeJsonStringFrom(List<PokemonType> typeList){
        if (typeList == null || typeList.isEmpty()){
            return "";
        }

        List<String> typeJsonStrings = new ArrayList<>();
        for(PokemonType type : typeList){
            typeJsonStrings.add(
                    "{\"type\": {" +
                        "\"name\": \"" + type.getTypeInfo().getName() + "\"," +
                        "\"url\": \"" + type.getTypeInfo().getUrl() + "\"" +
                    "}}");
        }
        return String.join(",", typeJsonStrings);
    }

    private static JsonElement convertToJsonElementFrom(String jsonString){
        return gson.fromJson(jsonString, JsonElement.class);
    }
}
