package org.dobex.sound_crafters.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.dobex.sound_crafters.entity.City;
import org.dobex.sound_crafters.entity.Province;
import org.dobex.sound_crafters.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class LocationService {

    private final Gson GSON = new Gson();

    public String getAllCities() {
        JsonObject response = new JsonObject();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<City> cityList = session.createNamedQuery("City.getAllCities", City.class).getResultList();
        response.add("cities", GSON.toJsonTree(cityList));
        session.close();
        return GSON.toJson(response);
    }

    public String getAllProvinces() {
        JsonObject response = new JsonObject();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Province> provinceList = session.createNamedQuery("Province.getAllProvinces", Province.class).getResultList();
        response.add("provinces", GSON.toJsonTree(provinceList));
        return GSON.toJson(response);
    }
}
