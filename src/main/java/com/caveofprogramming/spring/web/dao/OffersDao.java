package com.caveofprogramming.spring.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;


@Component("offersDao")
public class OffersDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource jdbc) {
        this.jdbc = new NamedParameterJdbcTemplate(jdbc);
    }

    public List<Offer> getOffers() {

        return jdbc.query("SELECT * FROM offer, users WHERE " +
                        "offer.username = users.username " +
                        "AND users.enabled = true",
                new OfferRowMapper());
    }

    public List<Offer> getOffers(String username) {

        return jdbc.query("SELECT * FROM offer, users WHERE " +
                        "offer.username = users.username " +
                        "AND users.enabled = true " +
                        "AND offer.username =:username",
                new MapSqlParameterSource("username", username),
                new OfferRowMapper());
    }

    public boolean update(Offer offer) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(offer);

        return jdbc.update("update offer set text=:text where id=:id", params) == 1;
    }

    public boolean create(Offer offer) {

        BeanPropertySqlParameterSource params =
                new BeanPropertySqlParameterSource(offer);

        return jdbc.update("insert into offer (username, text) " +
                "values (:username, :text)", params) == 1;
    }

    @Transactional
    public int[] create(List<Offer> offers) {

        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(offers.toArray());

        return jdbc.batchUpdate("insert into offer (username, text) values " +
                "(:username, :text)", params);
    }

    public boolean delete(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbc.update("delete from offer where id=:id", params) == 1;
    }

    public Offer getOffer(int id) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbc.queryForObject("SELECT * FROM offer, users " +
                        "WHERE offer.username = users.username " +
                        "AND users.enabled = true " +
                        "AND offer.id = :id", params,
                new OfferRowMapper());
    }

}
