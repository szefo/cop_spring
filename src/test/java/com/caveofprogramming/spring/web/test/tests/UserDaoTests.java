package com.caveofprogramming.spring.web.test.tests;


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
        "file:src/test/java/com/caveofprogramming/spring/web/test/config/datasource.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

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
    public void testUser() {
        User user = new User("marian997","Marian", "qwertyuiop", true, "user", "szefo@wp.pl");
        assertTrue("User creation should return true", usersDao.create(user));

        List<User> users = usersDao.getAllUsers();

        assertEquals("Number of users should be 1", 1, users.size());

        assertTrue("User should exists", usersDao.exists(user.getUsername()));

        assertEquals("Created user should be identical to retrieved user",
                user, users.get(0));
    }
}
