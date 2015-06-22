package com.caveofprogramming.spring.web.test.tests;

import com.caveofprogramming.spring.web.dao.Offer;
import com.caveofprogramming.spring.web.dao.OffersDao;
import com.caveofprogramming.spring.web.dao.User;
import com.caveofprogramming.spring.web.dao.UsersDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/dao-context.xml",
        "file:src/main/webapp/WEB-INF/security-context.xml",
        "file:src/test/resources/datasource.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OfferDaoTests {

    @Autowired
    private OffersDao offersDao;
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private DataSource dataSource;


    @Before
    public void init() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DELETE FROM offer");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    public void testOffer() {

        User user = new User("marian997", "Marian", "qwertyuiop", true, "user", "szefo@wp.pl");
        assertTrue("User creation should return true", usersDao.create(user));

        Offer offer = new Offer(user, "Pokemon");

        assertTrue("Offer creation should return true", offersDao.create(offer));

        List<Offer> offers = offersDao.getOffers();

        assertEquals("Number of users should be 1", 1, offers.size());

        assertEquals("Created offer should be identical to retrieved offer",
                offer, offers.get(0));

        // get the offer with ID filled in.
        offer = offers.get(0);

        offer.setText("Updated text offer");
        assertTrue("Offer update should return true", offersDao.update(offer));

        Offer offerUpdated = offersDao.getOffer(offer.getId());

        assertEquals("Updated offer should match retrieved updated offer", offer, offerUpdated);

        //Test get by ID ///
        Offer offer2 = new Offer(user, "Thi is test offer");

        assertTrue("Offer creation should return true", offersDao.create(offer2));

        List<Offer> userOffers = offersDao.getOffers(user.getUsername());
        assertEquals("Should be 2 offers for user", 2, userOffers.size());

        List<Offer> offers1 = offersDao.getOffers();

        for (Offer offer_loop : offers1) {
            Offer retrieved = offersDao.getOffer(offer_loop.getId());

            assertEquals("Offer by ID should match offer from list", offer_loop, retrieved);
        }

        // Test deletion
        offersDao.delete(offer.getId());

        List<Offer> empty = offersDao.getOffers();

        assertEquals("Offers lists should be empty", 1, empty.size());
    }


}
