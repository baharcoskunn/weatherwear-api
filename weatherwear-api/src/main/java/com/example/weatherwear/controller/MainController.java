package com.example.weatherwear.controller;

import com.example.weatherwear.model.WeatherInfo;
import com.example.weatherwear.service.ClothingRecommender;
import com.example.weatherwear.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ClothingRecommender clothingRecommender;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/check")
    public String checkWeather(@RequestParam String city, Model model) {
        if (city == null || city.trim().isEmpty()) {
            model.addAttribute("error", "City name cannot be empty.");
            return "error";
        }

        WeatherInfo weatherInfo = weatherService.getWeather(city);

        if (weatherInfo == null) {
            model.addAttribute("city", city);
            model.addAttribute("error", "City not found or API error occurred.");
            return "error";
        }

        String suggestion = clothingRecommender.getSuggestion(weatherInfo.getMainWeather(), weatherInfo.getFeelsLikeTemp());
        String cssClass;
        switch (weatherInfo.getMainWeather().toLowerCase()) {
            case "clear":
                cssClass = "sunny-page";
                break;
            case "clouds":
                cssClass = "cloudy-page";
                break;
            case "rain":
            case "drizzle":
            case "thunderstorm":
                cssClass = "rainy-page";
                break;
            default:
                cssClass = "default-page";
                break;
        }


        model.addAttribute("city", city);
        model.addAttribute("weather", weatherInfo.getMainWeather());
        model.addAttribute("description", weatherInfo.getDescription());
        model.addAttribute("temp", weatherInfo.getFeelsLikeTemp());
        model.addAttribute("humidity", weatherInfo.getHumidity());
        model.addAttribute("windSpeed", weatherInfo.getWindSpeed());
        model.addAttribute("suggestion", suggestion);
        model.addAttribute("cssClass", cssClass);
        return "result";
    }
}
