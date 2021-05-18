package learn.spooky.data;

import learn.spooky.data.mappers.WishListMapper;
import learn.spooky.models.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishListJdbcTemplateRepository implements WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Find All
    @Override
    public List<WishList> findAll() {
        final String sql = "select wishlist_id, username "
                + "from wishlist;";

        return jdbcTemplate.query(sql, new WishListMapper());
    }


    //Find by Username
    @Override
    public WishList findByUsername(String username) {
        final String sql = "select wishlist_id, username "
                + "from wishlist "
                + "where username = ?;";

        return jdbcTemplate.query(sql, new WishListMapper(), username).stream()
                .findFirst()
                .orElse(null);
    }

    //Add
    @Override
    public WishList add(WishList wishList) {
        final String sql = "insert into wishlist (username)"
                + "values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wishList.getUsername());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        wishList.setWishListId(keyHolder.getKey().intValue());
        return wishList;
    }

    //Update
    @Override
    public boolean update(WishList wishList) {
        final String sql = "update wishlist set "
                + "username = ? "
                + "where wishlist_id = ?;";

        return jdbcTemplate.update(sql,
                wishList.getUsername(),
                wishList.getWishListId()) > 0;
    }

    //Delete
    @Override
    public boolean deleteByUsername(String username) {
        return jdbcTemplate.update(
                "delete from wishlist where username = ?", username) > 0;
    }

}
