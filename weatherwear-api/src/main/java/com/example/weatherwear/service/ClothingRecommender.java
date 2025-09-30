package com.example.weatherwear.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClothingRecommender {

    private final Map<String, String> tempRecommendations = new HashMap<>();

    public ClothingRecommender() {
        tempRecommendations.put("very_cold", "Thick coat, thermal underwear, gloves, scarf, waterproof boots.");
        tempRecommendations.put("cold", "Heavy coat, sweater, thermal layers, scarf.");
        tempRecommendations.put("chilly", "Coat or thick jacket, sweatshirt or sweater.");
        tempRecommendations.put("cool", "Light jacket, hoodie, long-sleeved shirt.");
        tempRecommendations.put("mild", "Cardigan, long sleeve t-shirt.");
        tempRecommendations.put("warm", "T-shirt, light pants or skirt.");
        tempRecommendations.put("hot", "Shorts, t-shirt, dress.");
        tempRecommendations.put("very_hot", "Thin and loose clothes, hat, sunglasses.");
        tempRecommendations.put("extreme", "Sleeveless clothes, light-colored fabrics.");
    }

    public String getSuggestion(String weatherType, double feelsLikeTemp) {
        Queue<String> suggestionQueue = new LinkedList<>();

        // sıcaklık aralığına göre öneri seçimi
        if (feelsLikeTemp < 0) {
            suggestionQueue.add(tempRecommendations.get("very_cold"));
        } else if (feelsLikeTemp <= 5) {
            suggestionQueue.add(tempRecommendations.get("cold"));
        } else if (feelsLikeTemp <= 10) {
            suggestionQueue.add(tempRecommendations.get("chilly"));
        } else if (feelsLikeTemp <= 15) {
            suggestionQueue.add(tempRecommendations.get("cool"));
        } else if (feelsLikeTemp <= 20) {
            suggestionQueue.add(tempRecommendations.get("mild"));
        } else if (feelsLikeTemp <= 25) {
            suggestionQueue.add(tempRecommendations.get("warm"));
        } else if (feelsLikeTemp <= 30) {
            suggestionQueue.add(tempRecommendations.get("hot"));
        } else if (feelsLikeTemp <= 35) {
            suggestionQueue.add(tempRecommendations.get("very_hot"));
        } else {
            suggestionQueue.add(tempRecommendations.get("extreme"));
        }

        // hava durumu koşullarına göre ek öneriler
        if (weatherType.equalsIgnoreCase("Rain") || weatherType.equalsIgnoreCase("Drizzle") ||
                weatherType.equalsIgnoreCase("Thunderstorm")) {
            suggestionQueue.add("Don’t forget umbrella, raincoat, and waterproof shoes.");
        }

        if (weatherType.equalsIgnoreCase("Wind") || weatherType.equalsIgnoreCase("Windy")) {
            suggestionQueue.add("Consider wearing a windbreaker or hooded clothing.");
        }

        StringBuilder suggestions = new StringBuilder();
        while (!suggestionQueue.isEmpty()) {
            suggestions.append(suggestionQueue.poll()).append(" ");
        }

        return suggestions.toString().trim();
    }
}
